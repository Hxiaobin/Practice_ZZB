package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.tool.product.IntelProductDetailActivity;
import com.sf.bocfinancialsoftware.bean.ProductBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;

import java.util.List;

import me.gujun.android.taggroup.TagGroup;

/**
 * 热销理财产品的适配器
 * Created by Author: wangyongzhu on 2017/6/28.
 */

public class ProductAdapter extends BaseAdapter {
    private Context mContext;
    private List<ProductBean> mBeanList;

    public ProductAdapter(Context context, List<ProductBean> beanList) {
        mContext = context;
        mBeanList = beanList;
    }

    public void setProductBeen(List<ProductBean> Been) {
        if (Been.size()>0){
            mBeanList.removeAll(mBeanList);
            mBeanList.addAll(Been);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_product, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvProductTitle);
            viewHolder.contents = (TagGroup) convertView.findViewById(R.id.tclToolProductName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(mBeanList.get(position).getTitle());
        viewHolder.contents.setTags(mBeanList.get(position).getContent());

        viewHolder.contents.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                Intent intent = new Intent(mContext, IntelProductDetailActivity.class);
                intent.putExtra(ConstantConfig.TITLE, tag);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView title;
        TagGroup contents;
    }
}
