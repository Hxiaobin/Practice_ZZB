package com.sf.bocfinancialsoftware.adapter.home.advertisement;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.sf.bocfinancialsoftware.R;

import java.util.List;

/**
 * 首页和中银分析详情图片轮播适配器
 * Created by sn on 2017/6/28.
 */

public class ImageAdapter extends StaticPagerAdapter {

    private Context context;  //上下文
    private List<String> imageList; //图片URL集合

    public ImageAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide
                .with(context)
                .load(imageList.get(position))
                .placeholder(R.mipmap.image_loading)             //在网络加载完成之前，预先加载显示一张本地图片
                .error(R.mipmap.image_load_failure)              //加载网络图片失败
                .centerCrop()
                .crossFade()                                 //设置淡入淡出
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)  //缓存图片源文件
                .into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

}
