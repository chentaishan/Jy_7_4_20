package com.example.luckymoney;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mClick;
    private TextView mResult;


    String[] names = {
            "王凯轩",
            "王东升",
            "张天一",
            "郜明杰",
            "包国梁",
            "赵立新",
            "逯瑞斌",
            "相春梅",
            "王晶媛",
            "康正瑜",
            "杨钰",
            "高宇亨",
            "陈龙",
            "熊国杰",
            "周肖鹏",
            "王天祥",
            "闫古月",
            "那日苏",
            "孙佳乐",
            "罗浩毅",
            "李忽瑞",
            "杜龙飞",
            "朱青林",
            "刘涛",
            "周浩",
            "袁包根",
            "韩瑞",
            "王思凡",
            "胡大江",
            "娄淼灿",
            "杨明亮",
            "芦凯涛",
            "张旭"
    };

    int index;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (isLoading) {
                index = index % names.length;
                Log.d(TAG, "handleMessage: " + index);
                mResult.setText(names[index]);
                index++;
                handler.sendMessageDelayed(Message.obtain(), 50);
            }
        }
    };
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
        mResult = (TextView) findViewById(R.id.result);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click:
                // TODO 20/04/24

                if (!isLoading) {
                    isLoading = true;
                    mClick.setText("停止");
                } else {
                    isLoading = false;
                    mClick.setText("开始");
                }

                handler.sendMessage(Message.obtain());

                break;
            default:
                break;
        }
    }

    private static final String TAG = "MainActivity";


    boolean isAnimFinish = true;

    private void anim() {

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mResult, "scaleX", 1, 3);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mResult, "scaleY", 1, 3);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimFinish = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimFinish = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


}
