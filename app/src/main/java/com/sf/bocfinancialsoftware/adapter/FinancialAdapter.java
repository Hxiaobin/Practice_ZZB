package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.FinanceBean;

import java.util.List;

/**
 * Created by Author: wangyongzhu on 2017/6/15.
 */

public class FinancialAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<FinanceBean> mFinancialBeen;


    public void setFinancialBeen(List<FinanceBean> Been) {
        if (Been.size()>0){
            mFinancialBeen.removeAll(mFinancialBeen);
            mFinancialBeen.addAll(Been);
            notifyDataSetChanged();
        }
    }

    public FinancialAdapter(Context context, List<FinanceBean> financialBeen) {
        mInflater = LayoutInflater.from(context);
        mFinancialBeen = financialBeen;
    }

    @Override
    public int getCount() {
        return mFinancialBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mFinancialBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_financial, null);
            viewHolder = new ViewHolder();
            viewHolder.tvFinancialTitle = (TextView) convertView.findViewById(R.id.tvFinancialTitle);
            viewHolder.tvFinancialTime = (TextView) convertView.findViewById(R.id.tvFinancialTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvFinancialTitle.setText(mFinancialBeen.get(position).getProductName());
        viewHolder.tvFinancialTime.setText(mFinancialBeen.get(position).getProductDate());
        return convertView;
    }

    class ViewHolder {
        TextView tvFinancialTitle;
        TextView tvFinancialTime;
    }
}
