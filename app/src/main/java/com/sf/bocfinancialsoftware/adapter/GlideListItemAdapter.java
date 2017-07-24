package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sf.bocfinancialsoftware.R;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * GridView图片加载适配器
 * Created by sn on 2017/7/17.
 */

public class GlideListItemAdapter extends BaseAdapter {

    private Context context; //上下文
    private List<String> urls; //图片地址集合

    public GlideListItemAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls == null ? 0 : urls.size();
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageListViewHolder holder = null;
        if (convertView == null) {
            holder = new ImageListViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_item_picture, null);
            holder.ivPicture = (ImageView) convertView.findViewById(R.id.ivPicture);
            convertView.setTag(holder);
        } else {
            holder = (ImageListViewHolder) convertView.getTag();
        }
        Glide
                .with(context)
                .load(urls.get(position))
                .placeholder(R.mipmap.ic_loading)             //在网络加载完成之前，预先加载显示一张本地图片
                .error(R.mipmap.ic_load_failure)              //加载网络图片失败
                .centerCrop()
                .crossFade()                                  //设置淡入淡出
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)  //缓存图片源文件
                .into(holder.ivPicture);
        return convertView;
    }

    class ImageListViewHolder {
        ImageView ivPicture;
    }
}
