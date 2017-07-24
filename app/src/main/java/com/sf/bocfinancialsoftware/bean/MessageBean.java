package com.sf.bocfinancialsoftware.bean;

import java.util.List;

/**
 * 通知提醒的消息Bean类
 * Created by sn on 2017/6/12.
 */

public class MessageBean {

    private String rtnMsg;       //返回信息
    private String rtnCode;      //返回码
    private Content content;     //内容

    public class Content {
        private String hasNext; //是否含有下一页
        private List<MessageObject> msgArray; //消息对象集合

        public class MessageObject {
            private String typeId; //消息类型
            private String msgId; //消息id
            private String msgTitle; //消息标题
            private String msgDate; //消息时间
            private String msgContent; //消息内容
            private String msgIsRead; //消息是否已读（0：未读；1：已读）

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
        }

        public String getHasNext() {
            return hasNext;
        }

        public void setHasNext(String hasNext) {
            this.hasNext = hasNext;
        }

        public List<MessageObject> getMsgArray() {
            return msgArray;
        }

        public void setMsgArray(List<MessageObject> msgArray) {
            this.msgArray = msgArray;
        }
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

}
