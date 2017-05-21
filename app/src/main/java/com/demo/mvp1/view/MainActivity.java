package com.demo.mvp1.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.mvp1.R;
import com.demo.mvp1.presenter.PresenterImp;
import com.demo.mvp1.presenter.interfaces.IPresenter;
import com.demo.mvp1.view.interfaces.IView;

public class MainActivity extends AppCompatActivity implements IView {

    private TextView mTextView;
    private Button mButton;
    private IPresenter mIPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        mTextView = (TextView) this.findViewById(R.id.content_view);
        mButton = (Button) this.findViewById(R.id.download_data_btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拥有一个presenter的实例，将一个Iview的实例传递过去
                //
                mIPresenter = new PresenterImp().attach(MainActivity.this);
                mIPresenter.getData();
            }
        });
    }


    /**
     * 提示用户加载开始了
     */
    @Override
    public void showDowning() {
        Toast.makeText(this, "下载中....", Toast.LENGTH_SHORT).show();
    }

    /**
     * 展示加载的数据
     *
     * @param data
     */
    @Override
    public void showData(String data) {
        mTextView.setText(data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((PresenterImp) mIPresenter).detach();
    }
}

