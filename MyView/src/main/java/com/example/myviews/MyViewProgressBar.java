package com.example.myviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.myapplication4.R;

/**
 *
 * @author niutong
 * @date 2017-10-12
 * Discription:进度条
 */

public class MyViewProgressBar extends View{

    private int mFirstColor;
    private int mSecondColor;
    private int mCircleWidth;
    private Paint mPaint;
    private int mProgress;
    private int mSpeed;

    private boolean startNext = false;

    public MyViewProgressBar(Context context) {
        this(context, null);
    }
    public MyViewProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MyViewProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultValue();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = a.getInt(attr, 100);
                    break;
            }
        }
        a.recycle();
        mPaint = new Paint();
        System.out.println("mSpeed = " + mSpeed);
        new Thread(){
            @Override
            public void run() {
                while (true)
                {
                    System.out.println("mProgress = " + mProgress);
                    mProgress++;
                    if (mProgress == 360){
                        mProgress = 0;
                        if (!startNext) {
                            startNext = true;
                        }else {
                            startNext = false;
                        }
                    }

                    postInvalidate();;

                    try {
                        Thread.sleep(mSpeed);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    private void initDefaultValue(){
        mFirstColor = Color.GREEN;
        mSecondColor = Color.RED;
        mCircleWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics());
        mSpeed = 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("Draw");
        int centre = getWidth() / 2;
        int radius = centre - mCircleWidth / 2;  //边框宽度是以圆边为中心的
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

        if (!startNext) {
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(centre, centre, radius, mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(centre, centre, radius, mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }
    }
}
