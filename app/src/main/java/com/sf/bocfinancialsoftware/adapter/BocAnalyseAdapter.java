package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;

import java.util.List;

/**
 * 中银分析适配器
 * Created by sn on 2017/6/8.
 */

public class BocAnalyseAdapter extends BaseAdapter {

    private Context context;
    private List<BocAnalyseBean.Content.NewsBean> bocAnalyseBeanList;

    public BocAnalyseAdapter(Context context, List<BocAnalyseBean.Content.NewsBean> bocAnalyseBeanList) {
        this.context = context;
        this.bocAnalyseBeanList = bocAnalyseBeanList;
    }

    @Override
    public int getCount() {
        return bocAnalyseBeanList != null ? bocAnalyseBeanList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return bocAnalyseBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BocAnalyseViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new BocAnalyseViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_boc_analyse, null);
            viewHolder.ivBocAnalyseNewsImageUrl = (ImageView) convertView.findViewById(R.id.ivBocAnalyseNewsImageUrl);
            viewHolder.tvBocAnalyseNewsTitle = (TextView) convertView.findViewById(R.id.tvBocAnalyseNewsTitle);
            viewHolder.tvBocAnalyseNewsDesc = (TextView) convertView.findViewById(R.id.tvBocAnalyseNewsDesc);
            viewHolder.tvBocAnalyseNewsDate = (TextView) convertView.findViewById(R.id.tvBocAnalyseNewsDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BocAnalyseViewHolder) convertView.getTag();
        }
        BocAnalyseBean.Content.NewsBean bean = bocAnalyseBeanList.get(position);
        viewHolder.tvBocAnalyseNewsTitle.setText(bean.getNewsTitle());
        viewHolder.tvBocAnalyseNewsDesc.setText(bean.getNewsDesc());
        viewHolder.tvBocAnalyseNewsDate.setText(bean.getNewsDate());
        Glide
                .with(context)
                .load(bean.getNewsImageUrl())
                .placeholder(R.mipmap.ic_loading)             //在网络加载完成之前，预先加载显示一张本地图片
                .error(R.mipmap.ic_load_failure)              //加载网络图片失败
                .centerCrop()
                .crossFade()                                  //设置淡入淡出
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)  //缓存图片源文件
                .into(viewHolder.ivBocAnalyseNewsImageUrl);
        return convertView;
    }

    class BocAnalyseViewHolder {
        private ImageView ivBocAnalyseNewsImageUrl;  //新闻图片
        private TextView tvBocAnalyseNewsTitle; //新闻名称
        private TextView tvBocAnalyseNewsDesc;  //新闻描述
        private TextView tvBocAnalyseNewsDate;  //新闻时间
    }

}
