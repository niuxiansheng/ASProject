package com.example.myviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication4.R;

/**
 *
 * @author niutong
 * @date 2017-10-13
 * Discription:声音控制器控件
 */

public class CustomVolumControlBar extends View {

    private int mFirstColor;

    private int mSecondColor;

    private int mCircleWidth;

    private Paint mPaint;

    // 当前长度
    private int mCurrentCount = 3;

    private Bitmap mImage;

    private int mSplitSize;

    private int mCount;

    private Rect mRect;

    public CustomVolumControlBar(Context context) {
        this(context, null);
    }

    public CustomVolumControlBar(Context context, AttributeSet attributeSet){
        this(context, attributeSet, 0);
    }

    public CustomVolumControlBar(Context context, AttributeSet attributeSet, int defStyle){
        super(context, attributeSet, defStyle);
        initDefaultValue();
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomVolumControlBar, defStyle, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.CustomVolumControlBar_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomVolumControlBar_secondColor:
                    a.getColor(attr, Color.CYAN);
                    break;
                case R.styleable.CustomVolumControlBar_bg:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomVolumControlBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomVolumControlBar_dotCount:
                    mCount = a.getInt(attr, 20);
                    break;
                case R.styleable.CustomVolumControlBar_splitSize:
                    mSplitSize = a.getInt(attr, 20);
                    break;
            }
        }

        a.recycle();
        mPaint = new Paint();
        mRect = new Rect();
    }

    private void initDefaultValue(){
        mFirstColor = Color.GREEN;
        mSecondColor = Color.CYAN;
        mCircleWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics());
        mCount = 15;
        mSplitSize = 10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);  //消除锯齿
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND); //断线处为圆头
        mPaint.setStyle(Paint.Style.STROKE); //空心

        int centre = getWidth() / 2;
        int radius = centre - mCircleWidth / 2;

        //块块
        drawOval(canvas, centre, radius);

        //图片
        int relRadius = radius - mCircleWidth / 2;  //内圆半径

        mRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.bottom = (int) (mRect.top + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);

        if (mImage.getWidth() < Math.sqrt(2) * relRadius) {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getHeight() * 1.0f / 2);
            mRect.right = (int) (mRect.left + mImage.getWidth());
            mRect.bottom = (int) (mRect.top + mImage.getHeight());
        }

        canvas.drawBitmap(mImage, null, mRect, mPaint);
    }

    private void drawOval(Canvas canvas, int centre, int radius){

        //计算每个块块占得比例
        float itemSize = (270 * 1.0f - (mCount - 1) * mSplitSize) / mCount;
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

        mPaint.setColor(mFirstColor);
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(oval, i * (itemSize + mSplitSize) + 135, itemSize, false, mPaint);
        }

        mPaint.setColor(mSecondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval, i * (itemSize + mSplitSize) + 135, itemSize, false, mPaint);
        }
    }

    public void up(){
        if (mCurrentCount < mCount)
        {
            mCurrentCount++;
            postInvalidate();
        }
    }

    public void down(){
        if (mCurrentCount > 0){
            mCurrentCount--;
            postInvalidate();
        }
    }

    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xUp > xDown) {
                    down();
                }else {
                    up();
                }
                break;
        }
        return true;
    }
}
