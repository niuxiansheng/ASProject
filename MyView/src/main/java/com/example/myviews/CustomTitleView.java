package com.example.myviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.myapplication4.R;


/**
 * Created by niutong on 2017-09-27.
 * Discription:
 */

public class CustomTitleView extends View{

    /**text
     *
     */
    private String mTitleText;

    /**
     * color
     */
    private int mTitleTextColor;

    /**
     * size
     */
    private int mTitleTextSize;

    private Bitmap mImage;

    private int mImageScale;


    /**
    * Discription :
    */
    private Rect rect;
    private Rect mBound;
    private Paint mPaint;

    int width;
    int height;

    public CustomTitleView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context){
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        /**get my style
         *
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_mytitleTextColor:
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomTitleView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomTitleView_imageScaleType:
                    mImageScale = a.getInt(attr, 0);
                    break;
            }
        }
        a.recycle();

        /**
         * get width and height
         */

        rect = new Rect();
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
//        mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        width = 0;
        height = 0;

//        if (widthMode == MeasureSpec.EXACTLY) {
//            width = widthSize;
//        }else {
//            mPaint.setTextSize(mTitleTextSize);
//            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
//            float textWidth = mBound.width();
//            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
//            width = desired;
//        }
//
//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = heightSize;
//        }else {
//            mPaint.setTextSize(mTitleTextSize);
//            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
//            float textHeight = mBound.height();
//            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
//            height = desired;
//        }

        //图片决定控件大小
        // width
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY){
            width = specSize;
        }else{
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mBound.width();

            if (specMode == MeasureSpec.AT_MOST) {
                int desire = Math.max(desireByImg, desireByTitle);
                width = Math.min(desire, specSize);
            }
        }

        //height

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        }else {
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mBound.height();
            if (specMode == MeasureSpec.AT_MOST) {
                height = Math.min(desire, specSize);
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
//
//        mPaint.setColor(mTitleTextColor);
//        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);

        //边框
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.right = getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = getPaddingBottom();

        mPaint.setColor(mTitleTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        //设置宽度小于字体所需
        if (mBound.width() > width) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitleText, paint, width - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), height - getPaddingBottom(), mPaint);
        }else {
            canvas.drawText(mTitleText, width/2 - mBound.width() * 1.0f / 2, height - getPaddingBottom(), mPaint);
        }

        //去掉使用掉的块
        rect.bottom -= mBound.height();

        if (mImageScale == 0) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }else {
            //计算居中矩形范围
            rect.left = width / 2 - mImage.getWidth()/2;
            rect.right = width / 2 + mImage.getWidth() / 2;
            rect.top = (height - mBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (height - mBound.height()) / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, rect, mPaint);
        }
    }
}
