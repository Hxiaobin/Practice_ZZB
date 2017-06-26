package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.Picture;

import java.util.List;

/**
 * Created by Author: wangyongzhu on 2017/6/14.
 */

public class PictureAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Picture> mPictures;

    public PictureAdapter(Context context, List<Picture> pictures) {
        mInflater = LayoutInflater.from(context);
        mPictures = pictures;
    }

    @Override
    public int getCount() {
        return mPictures.size();
    }

    @Override
    public Object getItem(int position) {
        return mPictures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_gridview, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.tvText = (TextView) convertView.findViewById(R.id.tvText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.icon.setImageResource(mPictures.get(position).getIconId());
        viewHolder.tvText.setText(mPictures.get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        private ImageView icon;
        private TextView tvText;
    }
}
