package com.example.handlerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==1){

                int result = (int) msg.obj;

                mResult.setText(result+"");
            }

        }
    };
    private TextView mResult;
    private Button mClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        mResult = (TextView) findViewById(R.id.result);
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click:
                // TODO 20/05/06
                sendHandler();
                break;
            default:
                break;
        }
    }

    private void sendHandler() {

        new Thread() {

            public void run() {

                Message message = new Message();
                message.what=1;
                message.obj = 99;
                handler.sendMessage(message);
            }
        }.start();
    }
}
