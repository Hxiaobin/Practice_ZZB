package com.sf.bocfinancialsoftware.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.sf.bocfinancialsoftware.R;

/**
 * Created by Author: wangyongzhu on 2017/6/16.
 */

public class RollViewPagerAdapter extends StaticPagerAdapter {
    private int[] images = {R.mipmap.banner1, R.mipmap.banner2, R.mipmap.banner3};

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setImageResource(images[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getCount() {
        return images.length;
    }
}
