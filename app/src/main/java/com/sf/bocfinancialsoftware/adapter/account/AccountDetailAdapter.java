package com.sf.bocfinancialsoftware.adapter.account;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.account.AccountDetailBean;

import java.util.ArrayList;
import java.util.List;

import static com.sf.bocfinancialsoftware.R.id.tvContractNum;

/**
 * 财务助手详情adapter
 * Created by Author: wangyongzhu on 2017/7/14.
 */

public class AccountDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<AccountDetailBean.ContentBean.ContractListBean> mContractListDatas = new ArrayList<>();

    public AccountDetailAdapter(Context context, List<AccountDetailBean.ContentBean.ContractListBean> contractListDatas) {
        mContext = context;
        if (contractListDatas != null) {
            mContractListDatas.addAll(contractListDatas);
        }
    }

    public AccountDetailAdapter() {
    }

    public AccountDetailAdapter(Context context) {
        mContext = context;
    }

    public void setContractListDatas(List<AccountDetailBean.ContentBean.ContractListBean> contractListDatas) {
        if (contractListDatas != null) {
            mContractListDatas.addAll(contractListDatas);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mContractListDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mContractListDatas.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_lv_account_detail, null);
            viewHolder = new ViewHolder();
            viewHolder.tvContractNum = (TextView) convertView.findViewById(tvContractNum);
            viewHolder.tvContractName = (TextView) convertView.findViewById(R.id.tvContractName);
            viewHolder.tvKeys = (TextView) convertView.findViewById(R.id.tvKeys);
            viewHolder.tvValues = (TextView) convertView.findViewById(R.id.tvValues);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvContractNum.setText(mContext.getString(R.string.tv_account) + mContractListDatas.get(position).getContractNum() + mContext.getString(R.string.tv_financial_currency));
        viewHolder.tvContractName.setText(mContractListDatas.get(position).getContractName());
        List<AccountDetailBean.ContentBean.ContractListBean.ContractInfoBean> contractInfoBean = mContractListDatas.get(position).getContractInfo();
        for (int i = 0; i < contractInfoBean.size(); i++) {
            AccountDetailBean.ContentBean.ContractListBean.ContractInfoBean infoBean = contractInfoBean.get(i);
            if (i == 0) {
                viewHolder.tvKeys.setText(infoBean.getKey());
                viewHolder.tvValues.setText(infoBean.getValue());
            } else {
                viewHolder.tvKeys.append("\n" + infoBean.getKey());
                viewHolder.tvValues.append("\n" + infoBean.getValue());
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvContractNum;
        TextView tvContractName;
        TextView tvKeys;
        TextView tvValues;
    }
}
