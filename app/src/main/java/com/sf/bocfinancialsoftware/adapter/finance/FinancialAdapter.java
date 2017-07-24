package com.sf.bocfinancialsoftware.adapter.finance;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.finance.FinanceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 热销理财产品列表Adapter
 * Created by Author: wangyongzhu on 2017/6/15.
 */

public class FinancialAdapter extends BaseAdapter {
    private Context mContext;
    private List<FinanceBean.ContentBean.ProductListBean> mProductListBeen = new ArrayList<>();

    public List<FinanceBean.ContentBean.ProductListBean> getProductListBeen() {
        return mProductListBeen;
    }

    public void setProductListBeen(List<FinanceBean.ContentBean.ProductListBean> productListBeen) {
        if (productListBeen != null) {
            mProductListBeen.clear();
            mProductListBeen.addAll(productListBeen);
            notifyDataSetChanged();
        }
    }

    public void addPage(List<FinanceBean.ContentBean.ProductListBean> productListBeen) {
        if (productListBeen != null) {
            mProductListBeen.addAll(productListBeen);
            notifyDataSetChanged();
        }
    }

    public FinancialAdapter(Context context, List<FinanceBean.ContentBean.ProductListBean> productListBeen) {
        mContext = context;
        mProductListBeen.addAll(productListBeen);
    }

    @Override
    public int getCount() {
        return mProductListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_financial, null);
            viewHolder = new ViewHolder();
            viewHolder.tvFinancialTitle = (TextView) convertView.findViewById(R.id.tvFinancialTitle);
            viewHolder.tvFinancialTime = (TextView) convertView.findViewById(R.id.tvFinancialTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.e("FinancialAdapter", "getView: " + getItem(position));
        viewHolder.tvFinancialTitle.setText(mProductListBeen.get(position).getProductName());
        viewHolder.tvFinancialTime.setText(mProductListBeen.get(position).getProductDate());
        return convertView;
    }

    class ViewHolder {
        TextView tvFinancialTitle;
        TextView tvFinancialTime;
    }

}
