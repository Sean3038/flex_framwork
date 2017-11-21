package com.example.ffes.flex_framwork.noteview.widget;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by user on 2017/11/14.
 */

public class AccountViewPager extends ViewPager {

    private boolean isCanScroll = false;//默認不可以滑動

    public AccountViewPager(Context context) {

        super(context);

    }

    public AccountViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);

    }


    public void setCanScroll(boolean canScroll) {

        isCanScroll = canScroll;

    }

    @Override

    public boolean onTouchEvent(MotionEvent ev) {

        if (isCanScroll) {

            return super.onTouchEvent(ev);

        } else {

            return false;

        }

    }

    @Override

    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (isCanScroll) {

            return super.onInterceptTouchEvent(ev);

        } else {

            return false;

        }

    }

}
