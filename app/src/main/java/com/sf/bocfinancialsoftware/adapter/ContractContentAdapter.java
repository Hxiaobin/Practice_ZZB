package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.ContractBean;

import java.util.List;

/**
 * 合同的内容适配器
 * Created by sn on 2017/7/18.
 */

public class ContractContentAdapter extends BaseAdapter {

    private Context context;
    private List<ContractBean.Content.Contract.ContractContent> contentList;  //合同内容

    public ContractContentAdapter(Context context, List<ContractBean.Content.Contract.ContractContent> contentList) {
        this.context = context;
        this.contentList = contentList;
    }

    @Override
    public int getCount() {
        return contentList != null ? contentList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return contentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContractContentViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contract_content, null);
            viewHolder = new ContractContentViewHolder();
            viewHolder.tvContractContentKey = (TextView) convertView.findViewById(R.id.tvContractContentKey);
            viewHolder.tvContractContentValue = (TextView) convertView.findViewById(R.id.tvContractContentValue);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ContractContentViewHolder) convertView.getTag();
        }
        ContractBean.Content.Contract.ContractContent bean = contentList.get(position);
        viewHolder.tvContractContentKey.setText(bean.getKey());
        viewHolder.tvContractContentValue.setText(bean.getValue());
        return convertView;
    }

    public class ContractContentViewHolder {
        private TextView tvContractContentKey; //key
        private TextView tvContractContentValue; //value
    }
}
