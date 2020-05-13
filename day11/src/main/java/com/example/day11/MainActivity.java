package com.example.day11;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MyView mClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mClick = (MyView) findViewById(R.id.click);
        mClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+v.getId());
            }
        });

        mClick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick: ");
                return true;
            }
        });
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        Log.d(TAG, "dispatchTouchEvent: " + ev.getAction());
////        return super.dispatchTouchEvent(ev);
//        return true;
//    }
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//        Log.d(TAG, "onTouchEvent: " + ev.getAction());
//        return super.onTouchEvent(ev);
//    }
}
