package com.sf.bocfinancialsoftware.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 计算子项ListView高度
 * 说明：ListView中嵌套ListView，子ListView数据显示不全，根据子ListView的子项目重新计算ListView的高度，然后把高度再作为LayoutParams设置给父ListView
 * Created by sn on 2017/7/19.
 */

public class ChildrenListViewUtil {

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); // 获取ListView对应的Adapter
        if (listAdapter == null) {
            return;
        }
        int totalItemHeight = 0;  //所有子项的总高度
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalItemHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));//params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
