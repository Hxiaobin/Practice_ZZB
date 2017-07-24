package com.sf.bocfinancialsoftware.adapter.account;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.account.AccountBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 财务助手账户列表的Adapter
 * Created by Author: wangyongzhu on 2017/6/22.
 */

public class AccountListAdapter extends BaseAdapter {
    private Context mContext;
    private List<AccountBean.ContentBean.AccountListBean> mDates = new ArrayList<>();

    public AccountListAdapter(Context context) {
        mContext = context;
    }

    public List<AccountBean.ContentBean.AccountListBean> getDates() {
        return mDates;
    }

    public void setDates(List<AccountBean.ContentBean.AccountListBean> dates) {
        if (dates != null) {
            mDates.clear();
            mDates.addAll(dates);
            notifyDataSetChanged();
        }
    }
    public void addPage(List<AccountBean.ContentBean.AccountListBean> dates) {
        if (dates != null) {
            mDates.addAll(dates);
            notifyDataSetChanged();
        }
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
        Log.e("AccountListAdapter", "getView: "+getItem(position));
        viewHolder.tvAccount.setText(mDates.get(position).getAccountName());
        viewHolder.tvBalance.setText(mDates.get(position).getBalance());
        viewHolder.tvPay.setText(mDates.get(position).getPayable());
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
