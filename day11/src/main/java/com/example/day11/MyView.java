package com.example.day11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private static final String TAG = "MyView";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(11f);

//        canvas.drawPoints(new float[]{10,10,20,20,30,30,40,40},paint);
//        canvas.drawLine(10,10,100,100,paint);

//        canvas.drawRect(10,10,100,100,paint);

//        canvas.drawCircle(50,50,10f,paint);

//        RectF rectF = new RectF(50,50,100,100);
//        canvas.drawRoundRect(rectF,5,5,paint);
//        0-255
        canvas.drawARGB(233,0,100,233);
    }
}
