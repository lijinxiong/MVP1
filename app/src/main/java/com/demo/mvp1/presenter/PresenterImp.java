package com.demo.mvp1.presenter;

import android.app.Activity;

import com.demo.mvp1.model.ModelImp;
import com.demo.mvp1.model.interfaces.IModel;
import com.demo.mvp1.presenter.interfaces.IPresenter;
import com.demo.mvp1.view.interfaces.IView;

import java.lang.ref.WeakReference;

/**
 * Created by jinxiong on 2017/5/20.
 */
public class PresenterImp implements IPresenter {


    //拥有一个model 的实例，我们可以直接new 一个
    IModel mIModel = new ModelImp();

    //那么拥有一个view的实例怎么写？直接把activity传过来？
    //显然是不合适的，我们拿到这个view的实例只是为了提示下载时候的一些信息，是不是，下载的时候告诉用户开始下载了
    //所以我们就只需要一个Iview的接口的实例就可以了，并且view（activity）已经实现了这个接口的

    WeakReference<IView> mIViewWeakReference;


    public PresenterImp() {
    }


    public PresenterImp attach(IView iView) {
        mIViewWeakReference = new WeakReference<>(iView);
        return this;
    }

    public void detach(){
        if (null != mIViewWeakReference) {
            mIViewWeakReference.clear();
        }
    }

    @Override
    public void getData() {


        IView iView = mIViewWeakReference.get();
        if (null != iView) {
            //提示用户下载开始了
            iView.showDowning();
        }

        //开始下载
        mIModel.loadData(new IModel.LoadDataListener() {


            /**
             * model层下载完成回调此函数，将数据传过来,这里注意的是这个方法是在model层被调用了，
             * 而我们是在子线程中下载数据，这个方法也是在子线程中被回调，所以更新ui需要注意
             * @param data
             */
            @Override
            public void complete(final String data) {

                IView iView = mIViewWeakReference.get();

                if (null != iView) {

                    ((Activity) iView).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            IView iView = mIViewWeakReference.get();
                            if (null != iView) {
                                iView.showData(data);
                            }
                        }
                    });
                }


            }


        });


    }


}
