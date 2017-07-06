package com.sf.bocfinancialsoftware.bean;

/**
 * 通知提醒的消息Bean类
 * Created by sn on 2017/6/12.
 */

public class PleasantMessageBean {

    private String msgId; //消息id
    private String msgTitle; //消息标题
    private String msgContent; //消息内容
    private String msgIsRead; //消息是否已读（0：未读；1：已读）
    private String msgDateAndTime; //消息日期和时间

    public PleasantMessageBean() {
    }

    public PleasantMessageBean(String msgTitle, String msgContent, String msgIsRead, String msgDateAndTime) {
        this.msgTitle = msgTitle;
        this.msgContent = msgContent;
        this.msgIsRead = msgIsRead;
        this.msgDateAndTime = msgDateAndTime;
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

    public String getMsgDateAndTime() {
        return msgDateAndTime;
    }

    public void setMsgDateAndTime(String msgDateAndTime) {
        this.msgDateAndTime = msgDateAndTime;
    }

}
