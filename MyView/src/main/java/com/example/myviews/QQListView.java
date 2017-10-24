package com.example.myviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by niutong on 2017-10-16.
 * Discription:perfect
 */

public class QQListView extends ListView {

    private static final String TAG = "QQlistView";

    //slide shortest distance
    private int touchSlop;

    //if response slide
    private boolean canSlideing;

    //x
    private int xDown;
    private int yDown;
    private int xMove;
    private int yMove;

    private LayoutInflater mInflater;

    //view touch now
    private View mCurrentView;
    private int mCurrentViewPos;

    private int mStartX;
    private int mMaxLength;

    private Scroller mScroller;

    public QQListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context, new LinearInterpolator(context, null));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mScroller.abortAnimation();    // 当重新点击屏幕时停止动画，避免对新的view产生影响
                xDown = x;
                yDown = y;
                mStartX = x;
                mCurrentViewPos = pointToPosition(xDown, yDown);
                mCurrentView = getChildAt(mCurrentViewPos - getFirstVisiblePosition());
                mCurrentView.getScrollX();
                if (mCurrentView.getScrollX() == 0) {
                    if (resetView()) {          // 已经操作的条目恢复
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;

                // right to left or left to right
                if (Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop) {
                    canSlideing = true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setMaxLength(int maxLength){
        mMaxLength = maxLength;
    }

    public Boolean resetView(){
        int conunt = getChildCount();
        for (int i = 0; i < conunt; i++) {
            View view1 = getChildAt(i);
            if (view1.getScrollX() != 0) {
                view1.scrollTo( 0, 0);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int scrollX = mCurrentView.getScrollX();
        if (canSlideing) {
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    System.out.println("TTT--->> TOUCHEVENT ACTION MOVE");
                    int newScrollX = mStartX - x;
                    if (newScrollX < 0 && scrollX <= 0){
                        newScrollX = 0;
                    }else if (newScrollX > 0 && scrollX >= mMaxLength){
                        newScrollX = 0;
                    }

                    mCurrentView.scrollBy(newScrollX, 0);
//                    int[] location = new int[2];
                    // get current item x and y
//                    mCurrentView.getLocationOnScreen(location);

                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("TTT--->> TOUCHEVENT ACTION UP");
                    if (scrollX > mMaxLength / 2) {
                        mScroller.startScroll(scrollX, 0, mMaxLength - scrollX, 0);
                    }else {
                        mScroller.startScroll(scrollX, 0, -scrollX, 0);
                    }
                    invalidate();
                    canSlideing = false;
                    break;
            }
        }
        mStartX = x;
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mCurrentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
