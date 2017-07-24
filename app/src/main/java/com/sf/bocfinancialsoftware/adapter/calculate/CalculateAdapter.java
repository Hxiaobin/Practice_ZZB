package com.sf.bocfinancialsoftware.adapter.calculate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.calculate.ExchangeRateCalculateBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算器 PopupWindow适配器
 * Created by Author: wangyongzhu on 2017/7/19.
 */

public class CalculateAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExchangeRateCalculateBean.ContentBean> mCurrency = new ArrayList<>(); //币种数据

    public CalculateAdapter(Context context) {
        mContext = context;
    }

    public void setCurrency(List<ExchangeRateCalculateBean.ContentBean> currency) {
        if (currency != null) {
            mCurrency.clear();
            mCurrency.addAll(currency);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mCurrency.size();
    }

    @Override
    public Object getItem(int position) {
        return mCurrency.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) View.inflate(mContext, R.layout.spinner_item, null);
        textView.setText(mCurrency.get(position).getCurrencyName());
        return textView;
    }
}
