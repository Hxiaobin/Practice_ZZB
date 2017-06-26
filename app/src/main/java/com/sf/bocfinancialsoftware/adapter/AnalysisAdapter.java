package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.AnalysisBean;

import java.util.List;

/**
 * Created by Author: wangyongzhu on 2017/6/14.
 */

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.ViewHolder> implements View.OnClickListener {
    private List<AnalysisBean> mDates;
    private LayoutInflater mInflater;

    public AnalysisAdapter(List<AnalysisBean> dates, Context context) {
        mDates = dates;
        mInflater = LayoutInflater.from(context);
    }

    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_analysis, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    //将数据与界面进行绑定
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AnalysisBean bean = mDates.get(position);
        holder.tvAnalysisTitle.setText(bean.getTitle());
        holder.tvAnalysisContent.setText(bean.getContent());
        holder.tvAnalysisTime.setText(bean.getTime());
        if (position >= 4){
            Log.e("TAG", "image->"+ bean.getImage());
        }
        holder.ivAnalysisIv.setImageResource(bean.getImage());
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDates.size();
    }

    //自定义的ViewHolder,持有每个Item的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAnalysisTitle;
        public TextView tvAnalysisContent;
        public TextView tvAnalysisTime;
        public ImageView ivAnalysisIv;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAnalysisTitle = (TextView) itemView.findViewById(R.id.tvAnalysisTitle);
            tvAnalysisContent = (TextView) itemView.findViewById(R.id.tvAnalysisContent);
            tvAnalysisTime = (TextView) itemView.findViewById(R.id.tvAnalysisTime);
            ivAnalysisIv = (ImageView) itemView.findViewById(R.id.ivAnalysisIv);
        }
    }
}
