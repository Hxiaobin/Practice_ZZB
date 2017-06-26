package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.AccountListBean;

import java.util.List;

/**
 * Created by Author: wangyongzhu on 2017/6/22.
 */

public class AccountListAdapter extends BaseAdapter {
    private Context mContext;
    private List<AccountListBean> mDates;

    public AccountListAdapter(Context context, List<AccountListBean> dates) {
        mContext = context;
        mDates = dates;
    }

    @Override
    public int getCount() {
        return mDates.size();
    }

    @Override
    public Object getItem(int position) {
        return mDates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_financial_assistal, null);
            viewHolder = new ViewHolder();
            viewHolder.tvAccount = (TextView) convertView.findViewById(R.id.tvAccount);
            viewHolder.tvAccount1 = (TextView) convertView.findViewById(R.id.tvAccount1);
            viewHolder.tvAccount2 = (TextView) convertView.findViewById(R.id.tvAccount2);
            viewHolder.tvBalance1 = (TextView) convertView.findViewById(R.id.tvBalance1);
            viewHolder.tvBalance = (TextView) convertView.findViewById(R.id.tvBalance);
            viewHolder.tvBalance2 = (TextView) convertView.findViewById(R.id.tvBalance2);
            viewHolder.tvPay1 = (TextView) convertView.findViewById(R.id.tvPay1);
            viewHolder.tvPay2 = (TextView) convertView.findViewById(R.id.tvPay2);
            viewHolder.tvPay = (TextView) convertView.findViewById(R.id.tvPay);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAccount.setText(mDates.get(position).getTvAccount());
        viewHolder.tvBalance.setText(mDates.get(position).getTvBalance());
        viewHolder.tvPay.setText(mDates.get(position).getTvPay());
        return convertView;
    }

    class ViewHolder {
        TextView tvAccount1;
        TextView tvAccount;
        TextView tvAccount2;
        TextView tvBalance1;
        TextView tvBalance;
        TextView tvBalance2;
        TextView tvPay1;
        TextView tvPay;
        TextView tvPay2;
    }
}
