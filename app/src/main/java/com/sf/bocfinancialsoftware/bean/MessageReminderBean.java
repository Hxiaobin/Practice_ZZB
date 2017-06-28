package com.sf.bocfinancialsoftware.bean;

import java.util.List;

/**
 * 通知提醒的消息Bean类
 * Created by sn on 2017/6/12.
 */

public class MessageReminderBean {

    private String typeId; //消息类型
    private String msgId; //消息id
    private String msgTitle; //消息标题
    private String msgDate; //消息时间
    private String msgContent; //消息内容
    private String msgIsRead; //消息是否已读（0：未读；1：已读）
    private String unReadSum; //未读数量

    public MessageReminderBean() {
    }

    public MessageReminderBean(String typeId, String msgId, String msgTitle, String msgDate, String msgContent, String msgIsRead, String unReadSum) {
        this.typeId = typeId;
        this.msgId = msgId;
        this.msgTitle = msgTitle;
        this.msgDate = msgDate;
        this.msgContent = msgContent;
        this.msgIsRead = msgIsRead;
        this.unReadSum = unReadSum;
    }

    public MessageReminderBean(String typeId, String msgTitle, String msgDate, String msgContent, String msgIsRead) {
        this.typeId = typeId;
        this.msgTitle = msgTitle;
        this.msgDate = msgDate;
        this.msgContent = msgContent;
        this.msgIsRead = msgIsRead;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgIsRead() {
        return msgIsRead;
    }

    public void setMsgIsRead(String msgIsRead) {
        this.msgIsRead = msgIsRead;
    }

    public String getUnReadSum() {
        return unReadSum;
    }

    public void setUnReadSum(String unReadSum) {
        this.unReadSum = unReadSum;
    }

}
