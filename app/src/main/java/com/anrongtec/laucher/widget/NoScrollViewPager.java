package com.anrongtec.laucher.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by huiliu on 2018/4/18.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isScroll;
    public NoScrollViewPager(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public NoScrollViewPager(Context context) {
        super(context);
    }



    /**
     * 如果不需要嵌套之类的处理，不用处理这个情况
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }

    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll){
            return super.onTouchEvent(ev);
        }else {
            return true;// 消费,拦截事件，就不会让
        }
    }


    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
