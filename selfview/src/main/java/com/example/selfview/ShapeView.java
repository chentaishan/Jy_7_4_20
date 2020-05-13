package com.example.selfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ShapeView extends View {

    private Paint paint;

    public ShapeView(Context context) {
        super(context);

//        initPaint();
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        initPaint();
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initPaint();
    }

    private void initPaint() {
        //创建画笔
        paint = new Paint();
        // 画笔为填充模式
        paint.setStyle(Paint.Style.FILL);
        //画笔颜色
        paint.setColor(Color.RED);
        // 画笔宽度
        paint.setStrokeWidth(11f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initPaint();

//        canvas.drawPoint(300,300, paint);
        canvas.drawLine(300,300,500,600,paint);
    }
}
