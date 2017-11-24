package com.example.myviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.example.myapplication4.R;

import java.lang.ref.WeakReference;

/**
 * Modify by niutong at 2017-11-24
 *
 * @author niutong
 * @date 2017-11-24
 * Discription:
 */

public class OvalCornerImageView extends ImageView{

    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    // 圆角大小默认值
    private static final int BODER_RADIUS_DEFAULT = 10;

    private int mBorderRadius;

    private Paint mPaint;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;

    private WeakReference<Bitmap> mWeakBitmap;

    public OvalCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public OvalCornerImageView(Context context) {
        this(context, null);
    }

    private void init(Context context, AttributeSet attributeSet){

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.Custom_oval_picture);
        type = a.getInt(R.styleable.Custom_oval_picture_type, TYPE_CIRCLE);
        mBorderRadius = a.getDimensionPixelOffset(R.styleable.Custom_oval_picture_borderRadius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BODER_RADIUS_DEFAULT, getResources().getDisplayMetrics()));
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (TYPE_CIRCLE != type) {
            int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(width, width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();

        if (bitmap == null || bitmap.isRecycled()) {
            Drawable drawable = getDrawable();

            int dWidth = drawable.getIntrinsicWidth();
            int dHeight = drawable.getIntrinsicHeight();

            if (drawable != null) {
                // new a bitmap
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                float scale = 1.0f;

                //new a canvas
                Canvas drawCanvas = new Canvas(bitmap);

                // set scale to keep the photo real
                if (type == TYPE_ROUND) {
                    // if the picture width and height != View's width and height
                    scale = Math.max(getWidth() * 1.0f / dWidth, getHeight() * 1.0f / dHeight);
                }else {
                    scale = getWidth() * 1.0f / Math.min(dWidth, dHeight);
                }

                // set bounds of drawable
                drawable.setBounds(0, 0, (int)(scale * dWidth), (int) (scale * dHeight));
                drawable.draw(drawCanvas);

                if (mMaskBitmap == null || mMaskBitmap.isRecycled()){
                    mMaskBitmap = getBitmap();
                }

                // Draw bitmap
                mPaint.reset();
                mPaint.setFilterBitmap(false);
                mPaint.setXfermode(mXfermode);
                //绘制形状

                drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
                mPaint.setXfermode(null);
                canvas.drawBitmap(bitmap, 0, 0, null);
                // bitmap
                mWeakBitmap = new WeakReference<Bitmap>(bitmap);
            }
        }

        if (bitmap != null)
        {
            mPaint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
        }
    }

    @Override
    public void invalidate() {
        mWeakBitmap = null;
        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
        super.invalidate();
    }

    public Bitmap getBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        if (type == TYPE_ROUND)
        {
            canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                    mBorderRadius, mBorderRadius, paint);
        } else
        {
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2,
                    paint);
        }

        return bitmap;
    }
}
