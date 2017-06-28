package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.BocAnalyseBean;

import java.util.List;

/**
 * 中银分析适配器
 * Created by sn on 2017/6/8.
 */
public class BocAnalyseAdapter extends BaseAdapter {

    private Context context;
    private List<BocAnalyseBean> bocAnalyseBeanList;

    public BocAnalyseAdapter(Context context, List<BocAnalyseBean> bocAnalyseBeanList) {
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
        BocAnalyseBean bocAnalyseBean = bocAnalyseBeanList.get(position);
        viewHolder.ivBocAnalyseNewsImageUrl.setImageResource(Integer.parseInt(bocAnalyseBean.getNewsImageUrl()));
        viewHolder.tvBocAnalyseNewsTitle.setText(bocAnalyseBean.getNewsTitle());
        viewHolder.tvBocAnalyseNewsDesc.setText(bocAnalyseBean.getNewsDesc());
        viewHolder.tvBocAnalyseNewsDate.setText(bocAnalyseBean.getNewsData());
        return convertView;
    }

    class BocAnalyseViewHolder {
        private ImageView ivBocAnalyseNewsImageUrl;
        private TextView tvBocAnalyseNewsTitle; //新闻名称
        private TextView tvBocAnalyseNewsDesc;  //新闻描述
        private TextView tvBocAnalyseNewsDate;  //新闻时间
    }

}
