package com.example.day11;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MyLinearlayout extends LinearLayout {
    public MyLinearlayout(Context context) {
        super(context);
    }

    public MyLinearlayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final String TAG = "MyLinearlayout";

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View child = null;
        int childCount = getChildCount();

        int left=0,top=0,right=0,bottom=0;
        for (int i = 0; i < childCount; i++) {
              child = getChildAt(i);

              left = (getWidth()-child.getMeasuredWidth())/2;
              top = (getHeight()-child.getMeasuredHeight())/2;
              //子元素 右边缘 距离父布局左边的距离
              right = left+child.getMeasuredWidth();
              bottom = top+child.getMeasuredHeight();

        }
        child.layout(left,top,right,bottom);
    }


    //    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        Log.d(TAG, "dispatchTouchEvent: "+ev.getAction());
//        return super.dispatchTouchEvent(ev);
//
//
//    }
//
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.d(TAG, "onInterceptTouchEvent: "+ev.getAction());
//
//
//        return super.onInterceptTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//        Log.d(TAG, "onTouchEvent: "+ev.getAction());
//        return super.onTouchEvent(ev);
//    }
}
