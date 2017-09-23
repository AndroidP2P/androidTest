package com.app.frame.ui.ViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AppCustomViewPager extends ViewPager {
    private boolean isCanScroll = true;
    public AppCustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public AppCustomViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if(isCanScroll){
                return super.onTouchEvent(event);
            }else{
                return false;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            if(isCanScroll){
                return super.onInterceptTouchEvent(ev);
            }else{
                return false;
            }        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // this is tell you when user send signal to change viewpager page index, the smoothScroll determine to scroll page.
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, false);
    }

}
