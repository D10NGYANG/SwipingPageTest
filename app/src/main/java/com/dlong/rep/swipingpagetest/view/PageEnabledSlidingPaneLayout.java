package com.dlong.rep.swipingpagetest.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 页面划动Layout
 * 继承SlidingPaneLayout
 * 能够兼容别的控件的划动事件
 * 避免冲突
 * @author  dlong
 * created at 2019/3/12 11:29 AM
 */
public class PageEnabledSlidingPaneLayout extends SlidingPaneLayout {
    private float mInitialMotionX;
    private float mInitialMotionY;
    /** 最小触发事件的划动距离 */
    private float mEdgeSlop;//手滑动的距离

    public PageEnabledSlidingPaneLayout(Context context) {
        this(context, null);
    }

    public PageEnabledSlidingPaneLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageEnabledSlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //--------  ViewConfiguration 滑动参数设置类 --------//
        ViewConfiguration config = ViewConfiguration.get(context);
        //--------  它获得的是触发移动事件的最短距离，如果小于这个距离就不触发移动控件  --------//
        mEdgeSlop = config.getScaledEdgeSlop();
    }

    /**
     * 拦截手势事件
     * 避免划动冲突
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN: {
                // 屏幕检测到第一个触点按下
                mInitialMotionX = ev.getX();
                mInitialMotionY = ev.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // 触点在屏幕上移动
                final float x = ev.getX();
                final float y = ev.getY();
                // The user should always be able to "close" the pane, so we only check
                // for child scrollability if the pane is currently closed.
                // 避免与其他划动控件冲突
                if (mInitialMotionX > mEdgeSlop && !isOpen() && canScroll(this, false,
                        Math.round(x - mInitialMotionX), Math.round(x), Math.round(y))) {
                    // How do we set super.mIsUnableToDrag = true?
                    // send the parent a cancel event
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                    return super.onInterceptTouchEvent(cancelEvent);
                }
            }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
