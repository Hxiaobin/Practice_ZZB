package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;

import java.util.List;

/**
 * Created by Author: wangyongzhu on 2017/7/4.
 */

public class Test extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public Test(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_lv_search_result, null);
            holder = new Holder();
            holder.textview = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        CharSequence item = (CharSequence) getItem(position);
        holder.textview.setText(item);


        return convertView;
    }

    class Holder {
        TextView textview;
    }
}
