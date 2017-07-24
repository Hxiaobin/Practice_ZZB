package com.sf.bocfinancialsoftware.adapter.guide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sf.bocfinancialsoftware.activity.GuideActivity;

import java.util.List;

/**
 * 引导页滑动适配器
 * Created by sn on 2017/6/28.
 */

public class GuideAdapter extends PagerAdapter {

    private Context context;
    private List<View> views;

    public GuideAdapter(Context context, List<View> views) {
        this.context = context;
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }
}
