package com.sf.bocfinancialsoftware.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 重写SwipeRefreshLayout
 * 说明：处理轮播图左右滑动与列表下拉刷新的冲突
 * Created by sn on 2017/7/5.
 */
public class SwipeRefreshLayoutHome extends SwipeRefreshLayout {

    private float startX; //初始X坐标
    private float startY; //初始Y坐标
    private boolean isHorizontalSliding;  //是否存在左右滑动
    private int scaledTouchSlop;  //导致触发事件的最小距离

    public SwipeRefreshLayoutHome(Context context) {
        super(context);
    }

    public SwipeRefreshLayoutHome(Context context, AttributeSet attrs) {
        super(context, attrs);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 对于父控件的触摸监听，是否交给其他控件来处理事件
     * 说明：当返回true，拦截事件，由自己处理事件；当返回false，不拦截事件，交给其他控件处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:  //手指按下
                startX = ev.getX();
                startY = ev.getY();
                isHorizontalSliding = false;  //设置左右滑动事件为false
                break;
            case MotionEvent.ACTION_MOVE:  //手指移动
                if (isHorizontalSliding) {  // 如果存在左右滑动事件
                    return false;  //将该事件交给其他控件来处理
                }
                float endX = ev.getX();  //当前在X轴的位置
                float endY = ev.getY();  //当前在Y轴的位置
                float distanceX = Math.abs(endX - startX);  //X轴上的绝对位移
                float distanceY = Math.abs(endY - startY);  //Y轴上的绝对位移
                if (distanceX > scaledTouchSlop && distanceX > distanceY) {  //X轴上的绝对位移大于最小触发距离，并且大于Y轴上的绝对距离
                    isHorizontalSliding = true;  //属于左右滑动事件
                    return false;  //将该事件交给其他控件来处理
                }
                break;
            case MotionEvent.ACTION_UP:   //手指离开屏幕
                break;
            case MotionEvent.ACTION_CANCEL:  //手指移动到外层控件时
                isHorizontalSliding = false;  //左右滑动事件设为false
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
