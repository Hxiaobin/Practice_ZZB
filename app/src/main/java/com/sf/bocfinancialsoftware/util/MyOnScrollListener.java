package com.sf.bocfinancialsoftware.util;

import android.view.View;
import android.widget.AbsListView;

import java.util.List;
import java.util.Objects;

/**
 * Created by Author: wangyongzhu on 2017/7/17.
 */

public class MyOnScrollListener implements AbsListView.OnScrollListener {

    private int totalItemCount;//ListView总共显示多少条
    private int lastItem;//ListView最后的item项
    private boolean isLoading;//用于判断当前是否在加载
    private View footer;//底部加载更多布局
    private OnLoadDataListener listener;//接口回调的实例
    private List<Objects> mObjects;//数据

    public MyOnScrollListener(View footer, List<Objects> objects) {
        this.footer = footer;
        mObjects = objects;
    }

    //设置接口回调的实例
    public void setLoadDataListener(OnLoadDataListener listener) {
        this.listener = listener;
    }

    private void loadMoreData() {
        isLoading = true;
    }

    /**
     * 滑动状态变化
     *
     * @param view
     * @param scrollState 1 SCROLL_STATE_TOUCH_SCROLL是拖动  2 SCROLL_STATE_FLING是惯性滑动 3 SCROLL_STATE_IDLE是停止 , 只有当在不同状态间切换的时候才会执行
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //如果数据没有加载，并且滑动状态是停止的，而且到达了最后一个item项
        if (!isLoading && lastItem == totalItemCount && scrollState == SCROLL_STATE_IDLE) {
            //显示加载更多
            footer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 监听可见界面的情况
     *
     * @param view             ListView
     * @param firstVisibleItem 第一个可见的item的索引
     * @param visibleItemCount 可显示的item的条数
     * @param totalItemCount   总共有多少个item
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //当可见界面的第一个item + 当前界面有可见的界面个数=最后一个item项
        lastItem = firstVisibleItem + visibleItemCount;
        //总listView的item个数
        this.totalItemCount = totalItemCount;
    }

    private class OnLoadDataListener {
    }
}
