package com.sf.bocfinancialsoftware.bean.message;

/**
 * 未读消息bean类
 * Created by sn on 2017/7/20.
 */

public class UnReadMsgBean {

    private String rtnMsg;  //返回信息
    private String rtnCode; //返回码
    private Content content; //内容

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

    public class Content {
        private String sum;    //总未读数量
        private String type1;  //进口通知未读数量
        private String type2;  //出口通知未读数量
        private String type3;  //保函通知未读数量
        private String type4;  //保理通知未读数量
        private String type5;  //远期通知未读数量

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getType1() {
            return type1;
        }

        public void setType1(String type1) {
            this.type1 = type1;
        }

        public String getType2() {
            return type2;
        }

        public void setType2(String type2) {
            this.type2 = type2;
        }

        public String getType3() {
            return type3;
        }

        public void setType3(String type3) {
            this.type3 = type3;
        }

        public String getType4() {
            return type4;
        }

        public void setType4(String type4) {
            this.type4 = type4;
        }

        public String getType5() {
            return type5;
        }

        public void setType5(String type5) {
            this.type5 = type5;
        }
    }


}
