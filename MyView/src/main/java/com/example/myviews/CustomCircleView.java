package com.example.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * @author niutong
 * @date 2017-09-07
 * Discription:圆形等待框
 */

public class CustomCircleView extends View {

    private Paint mPaint;

    public CustomCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCircleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (mPaint == null){
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.parseColor("#0DC65B"));
            mPaint.setStyle(Paint.Style.FILL);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(50, 50, 50, mPaint);
    }
}
