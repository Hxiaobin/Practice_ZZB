package com.sf.bocfinancialsoftware.adapter.product;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.activity.tool.product.IntelProductDetailActivity;
import com.sf.bocfinancialsoftware.bean.product.IntelProductListBean;
import com.sf.bocfinancialsoftware.constant.ConstantConfig;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

/**
 * 产品介绍列表的适配器
 * Created by Author: wangyongzhu on 2017/6/28.
 */

public class IntelProductListAdapter extends BaseAdapter {
    private Context mContext;
    private List<IntelProductListBean.ContentBean.TypeListBean> mTypeListBeen = new ArrayList<>();

    public IntelProductListAdapter(Context context) {
        mContext = context;
    }

    public List<IntelProductListBean.ContentBean.TypeListBean> getTypeListBeen() {
        return mTypeListBeen;
    }

    public void setTypeListBeen(List<IntelProductListBean.ContentBean.TypeListBean> typeListBeen) {
        if (typeListBeen != null) {
            mTypeListBeen.clear();
            mTypeListBeen.addAll(typeListBeen);
            notifyDataSetChanged();
        }
    }

    public void addPage(List<IntelProductListBean.ContentBean.TypeListBean> typeListBeen) {
        if (typeListBeen != null) {
            mTypeListBeen.addAll(typeListBeen);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mTypeListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mTypeListBeen.get(position);
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
        viewHolder.title.setText(mTypeListBeen.get(position).getTypeName());
        final List<IntelProductListBean.ContentBean.TypeListBean.ProductArrayBean> productArray = mTypeListBeen.get(position).getProductArray();
        final List<String> tags = new ArrayList<>();
        for (int i = 0; i < productArray.size(); i++) {
            tags.add(productArray.get(i).getProductName());
        }
        viewHolder.contents.setTags(tags);

        viewHolder.contents.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                int i = tags.indexOf(tag);
                Intent intent = new Intent(mContext, IntelProductDetailActivity.class);
                intent.putExtra(ConstantConfig.INTEL_PRODUCT_KEY_SER, productArray.get(i));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TagGroup contents;
    }
}
