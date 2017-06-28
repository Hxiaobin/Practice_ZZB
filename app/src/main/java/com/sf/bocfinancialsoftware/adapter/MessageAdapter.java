package com.sf.bocfinancialsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sf.bocfinancialsoftware.R;
import com.sf.bocfinancialsoftware.bean.MessageReminderBean;

import java.util.List;

/**
 * 通知提醒消息列表适配器
 * Created by sn on 2017/6/12.
 */
public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<MessageReminderBean> messageReminderBeanList; //消息集合

    public MessageAdapter(Context context, List<MessageReminderBean> messageReminderBeanList) {
        this.context = context;
        this.messageReminderBeanList = messageReminderBeanList;
    }

    @Override
    public int getCount() {
        return messageReminderBeanList != null ? messageReminderBeanList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return messageReminderBeanList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_messge_reminder, null);
            messageViewHolder.tvMsgTitle = (TextView) convertView.findViewById(R.id.tvMsgTitle);
            messageViewHolder.tvMsgDate = (TextView) convertView.findViewById(R.id.tvMsgDate);
            messageViewHolder.tvMsgContent = (TextView) convertView.findViewById(R.id.tvMsgContent);
            convertView.setTag(messageViewHolder);
        } else {
            messageViewHolder = (MessageViewHolder) convertView.getTag();
        }
        MessageReminderBean messageReminderBean = messageReminderBeanList.get(position);
        messageViewHolder.tvMsgTitle.setText(messageReminderBean.getMsgTitle());
        messageViewHolder.tvMsgDate.setText("        " + messageReminderBean.getMsgDate());
        messageViewHolder.tvMsgContent.setText(messageReminderBean.getMsgContent());
        return convertView;
    }

    class MessageViewHolder {
        private TextView tvMsgTitle; //消息标题
        private TextView tvMsgDate; //消息时间
        private TextView tvMsgContent; //消息内容
    }

}
