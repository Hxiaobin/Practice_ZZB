package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图adapter
 * Created by Author: wangyongzhu on 2017/6/16.
 */

public class RollViewPagerAdapter extends StaticPagerAdapter {
    private List<String> mLoop = new ArrayList<>();//图片轮播数据
    private Context mContext;

    public RollViewPagerAdapter(Context context) {
        mContext = context;
    }

    public void setLoop(List<String> loop) {
        if (loop != null) {
            mLoop.clear();
            mLoop.addAll(loop);
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(ViewGroup container, int position) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        ImageView view = new ImageView(container.getContext());
        String url = mLoop.get(position);
        Glide.with(mContext).load(url).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(view);
        return linearLayout;
    }

    @Override
    public int getCount() {
        return mLoop.size();
    }

}
