package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.RateBean;

import java.util.List;

/**
 * Created by Author: wangyongzhu on 2017/6/22.
 */

public class RateAdapter extends BaseAdapter {
    private List<RateBean> mDatas;
    private Context mContext;

    public RateAdapter(List<RateBean> rateBeen, Context context) {
        mDatas = rateBeen;
        mContext = context;
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
        RateAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_rate, null);
            viewHolder = new ViewHolder();
            viewHolder.tvRateCurrency = (TextView) convertView.findViewById(R.id.tvRateCurrency);
            viewHolder.tvRateTime = (TextView) convertView.findViewById(R.id.tvRateTime);
            viewHolder.tvKnotRate = (TextView) convertView.findViewById(R.id.tvKnotRate);
            viewHolder.tvSellingRate = (TextView) convertView.findViewById(R.id.tvSellingRate);
            viewHolder.tvRateCurrency1 = (TextView) convertView.findViewById(R.id.tvRateCurrency1);
            viewHolder.tvRateTime1 = (TextView) convertView.findViewById(R.id.tvRateTime1);
            viewHolder.tvKnotRate1 = (TextView) convertView.findViewById(R.id.tvKnotRate1);
            viewHolder.tvSellingRate1 = (TextView) convertView.findViewById(R.id.tvSellingRate1);
            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvRateCurrency.setText(mDatas.get(position).getRateCurrency());
        viewHolder.tvRateTime.setText(mDatas.get(position).getRateTime());
        viewHolder.tvKnotRate.setText(mDatas.get(position).getKnotRate());
        viewHolder.tvSellingRate.setText(mDatas.get(position).getSellingRate());
        if (position == 0) {
            viewHolder.ll.setVisibility(View.GONE);
        }else if (position % 4 == 0) {
            viewHolder.ll.setVisibility(View.VISIBLE);
            viewHolder.tvRateCurrency1.setBackgroundColor(mContext.getResources().getColor(R.color.tagcloud_color));
            viewHolder.tvRateTime1.setBackgroundColor(mContext.getResources().getColor(R.color.tagcloud_color));
            viewHolder.tvKnotRate1.setBackgroundColor(mContext.getResources().getColor(R.color.tagcloud_color));
            viewHolder.tvSellingRate1.setBackgroundColor(mContext.getResources().getColor(R.color.tagcloud_color));
        } else {
            viewHolder.ll.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        LinearLayout ll;
        TextView tvRateCurrency1;
        TextView tvRateTime1;
        TextView tvKnotRate1;
        TextView tvSellingRate1;
        TextView tvRateCurrency;
        TextView tvRateTime;
        TextView tvKnotRate;
        TextView tvSellingRate;
    }
}
