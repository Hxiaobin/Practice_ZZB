package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.ContractBean;
import com.sf.bocfinancialsoftware.util.ChildrenListViewUtil;

import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.CREDIT_BALANCE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.OPENING_AMOUNT;

/**
 * 业务查询列表适配器
 * Created by sn on 2017/6/12.
 */

public class BusinessAdapter extends BaseAdapter {

    private Context context;
    private List<ContractBean.Content.Contract> contractBeenList; //合同集合

    public BusinessAdapter(Context context, List<ContractBean.Content.Contract> contractBeenList) {
        this.context = context;
        this.contractBeenList = contractBeenList;
    }

    @Override
    public int getCount() {
        return contractBeenList != null ? contractBeenList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return contractBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContractViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ContractViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_business_query_result, null);
            viewHolder.tvContractId = (TextView) convertView.findViewById(R.id.tvContractId);
            viewHolder.lvContractContent = (ListView) convertView.findViewById(R.id.lvContractContent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ContractViewHolder) convertView.getTag();
        }
        ContractBean.Content.Contract bean = contractBeenList.get(position);
        viewHolder.tvContractId.setText(bean.getContractId());
        viewHolder.lvContractContent.setAdapter(new ContractContentAdapter(context, bean.getObject()));
        ChildrenListViewUtil.setListViewHeightBasedOnChildren(viewHolder.lvContractContent);
        return convertView;
    }

    class ContractViewHolder {
        private TextView tvContractId; //业务id
        private ListView lvContractContent; //合同内容列表
    }

}
