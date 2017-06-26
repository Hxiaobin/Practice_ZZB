package com.sf.bocfinancialsoftware.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * 广告图片轮播适配器
 * Created by sn on 2017/6/8.
 */

public class ImageCarouselAdapter extends PagerAdapter {

    private List<ImageView> imageViewList; //广告图片源

    public ImageCarouselAdapter(List<ImageView> imageViewList) {
        this.imageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= imageViewList.size();
        if (position < 0) {
            position = imageViewList.size() + position;
        }
        ImageView imageView = imageViewList.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException
        ViewParent viewParent = imageView.getParent();
        if (viewParent != null) {
            ViewGroup viewGroup = (ViewGroup) viewParent;
            viewGroup.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

}
