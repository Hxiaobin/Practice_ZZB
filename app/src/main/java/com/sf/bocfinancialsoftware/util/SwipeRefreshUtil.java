package com.sf.bocfinancialsoftware.util;

import android.support.v4.widget.SwipeRefreshLayout;

import com.sf.bocfinancialsoftware.R;

/**
 * 下拉刷新列表样式
 * Created by sn on 2017/6/19.
 */

public class SwipeRefreshUtil {

    public static void setRefreshCircle(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
    }

}
