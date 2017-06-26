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
    private List<BocAnalyseBean> bocAnalyseBeanList;

    public HomeFragmentBocAnalyseAdapter(Context context, List<BocAnalyseBean> bocAnalyseBeanList) {
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
            viewHolder.ivHomeFragmentExpandReading = (ImageView) convertView.findViewById(R.id.ivHomeFragmentExpandReading);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BocAnalyseViewHolder) convertView.getTag();
        }
        BocAnalyseBean bocAnalyseBean = bocAnalyseBeanList.get(position);
        viewHolder.tvHomeFragmentBOCAnalyseNewsTitle.setText(bocAnalyseBean.getNewsTitle());
        viewHolder.tvHomeFragmentBOCAnalyseNewsDesc.setText(bocAnalyseBean.getNewsDesc());
        viewHolder.ivHomeFragmentExpandReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    class BocAnalyseViewHolder {
        private TextView tvHomeFragmentBOCAnalyseNewsTitle;  //新闻名称
        private TextView tvHomeFragmentBOCAnalyseNewsDesc;   //新闻描述
        private ImageView ivHomeFragmentExpandReading;       //展开阅读
    }
}
