package com.demo.mvp1.model;

import android.util.Log;

import com.demo.mvp1.model.interfaces.IModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jinxiong on 2017/5/20.
 */

public class ModelImp implements IModel {

    private static final String TAG = "ModelImp";

    /**
     *
     * @param loadDataListener presenter 实现这个接口，用于加载数据完成后
     *                         presenter能够知道
     */
    @Override
    public void loadData(final LoadDataListener loadDataListener) {


        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    URL url = new URL("http://tj.nineton.cn/Heart/index/future24h/" +
                            "?city=CHSH000000&language=zh-chs&" +
                            "key=36bdd59658111bc23ff2bf9aaf6e345c");

                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();


                    byte[] bytes = new byte[1024];
                    inputStream.read(bytes);

                    String s = new String(bytes);

                    Log.d(TAG, "run: " + s);



                    loadDataListener.complete(s);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }
}
