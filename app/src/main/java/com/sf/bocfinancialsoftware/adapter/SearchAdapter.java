package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.ProductSearchBean;

import java.util.List;

/**
 * Created by Author: wangyongzhu on 2017/7/3.
 */

public class SearchAdapter extends BaseAdapter {
    private Context mContext;
    private List<ProductSearchBean> mDatas;

    public SearchAdapter(Context context, List<ProductSearchBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_product_search, null);
            viewHolder = new ViewHolder();
            viewHolder.tvResult = (TextView) convertView.findViewById(R.id.tvResult);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvResult.setText(mDatas.get(position).getTvResults());
        return convertView;
    }

    class ViewHolder {
        TextView tvResult;
    }
}
