package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.ContractBean;
import com.sf.bocfinancialsoftware.bean.MessageReminderBean;

import java.util.List;

import static com.sf.bocfinancialsoftware.constant.ConstantConfig.CREDIT_BALANCE;
import static com.sf.bocfinancialsoftware.constant.ConstantConfig.OPENING_AMOUNT;

/**
 * 业务查询列表适配器
 * Created by sn on 2017/6/12.
 */
public class BusinessAdapter extends BaseAdapter {

    private Context context;
    private List<ContractBean> contractBeenList; //业务集合

    public BusinessAdapter(Context context, List<ContractBean> contractBeenList) {
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
        MessageViewHolder messageViewHolder = null;
        if (convertView == null) {
            messageViewHolder = new MessageViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_business_query_result, null);
            messageViewHolder.tvContractId = (TextView) convertView.findViewById(R.id.tvContractId);
            messageViewHolder.tvOpeningAmount = (TextView) convertView.findViewById(R.id.tvOpeningAmount);
            messageViewHolder.tvCreditBalance = (TextView) convertView.findViewById(R.id.tvCreditBalance);
            convertView.setTag(messageViewHolder);
        } else {
            messageViewHolder = (MessageViewHolder) convertView.getTag();
        }
        ContractBean contractBean = contractBeenList.get(position);
        messageViewHolder.tvContractId.setText(contractBean.getContractId());
        messageViewHolder.tvOpeningAmount.setText((String) contractBean.getMapObject().get(OPENING_AMOUNT));
        messageViewHolder.tvCreditBalance.setText((String)contractBean.getMapObject().get(CREDIT_BALANCE));
        return convertView;
    }

    class MessageViewHolder {
        private TextView tvContractId; //业务id
        private TextView tvOpeningAmount; //开证金额
        private TextView tvCreditBalance; //信用证余额
    }

}
