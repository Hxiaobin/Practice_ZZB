package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
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
 * 首页中银分析列表适配器
 * Created by sn on 2017/6/8.
 */

public class HomeFragmentBocAnalyseAdapter extends BaseAdapter {

    private Context context;
    private List<BocAnalyseBean.Content.NewsBean> bocAnalyseBeanList;

    public HomeFragmentBocAnalyseAdapter(Context context, List<BocAnalyseBean.Content.NewsBean> bocAnalyseBeanList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_boc_analyse, null);
            viewHolder.tvHomeFragmentBOCAnalyseNewsTitle = (TextView) convertView.findViewById(R.id.tvHomeFragmentBOCAnalyseNewsTitle);
            viewHolder.tvHomeFragmentBOCAnalyseNewsDesc = (TextView) convertView.findViewById(R.id.tvHomeFragmentBOCAnalyseNewsDesc);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BocAnalyseViewHolder) convertView.getTag();
        }
        BocAnalyseBean.Content.NewsBean bean = bocAnalyseBeanList.get(position);
        viewHolder.tvHomeFragmentBOCAnalyseNewsTitle.setText(bean.getNewsTitle());
        viewHolder.tvHomeFragmentBOCAnalyseNewsDesc.setText(bean.getNewsDesc());
        return convertView;
    }

    class BocAnalyseViewHolder {
        private TextView tvHomeFragmentBOCAnalyseNewsTitle;  //新闻名称
        private TextView tvHomeFragmentBOCAnalyseNewsDesc;   //新闻描述
    }

}
