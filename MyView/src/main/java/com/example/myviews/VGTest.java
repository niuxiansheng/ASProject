package com.example.myviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by niutong on 2017-10-14.
 * Discription:
 */

public class VGTest extends ViewGroup {

    public VGTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VGTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VGTest(Context context) {
        super(context);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // get ViewGroup`s suggest width and height

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        // get all childrens width and height
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        int lHeight = 0;    // left childview height
        int rHeight = 0;    // right childview height

        int tWidth = 0;     // top childview width
        int bWidth = 0;     // bottom childview width

        //get viewgroup width and height when warp_content

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            //get width
            if (i == 0 || i== 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if (i == 2 || i == 3) {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if (i == 0 || i == 2) {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }
            if (i == 1 || i == 3) {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }
        }

        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin -cParams.rightMargin;
                    ct = cParams.topMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
            }

            cr = cl + cWidth;
            cb = cHeight + ct;
            childView.layout(cl, ct, cr, cb);
        }
    }
}
