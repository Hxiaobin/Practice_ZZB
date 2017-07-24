package com.sf.bocfinancialsoftware.bean.calculate;

/**
 * Created by Author: wangyongzhu on 2017/7/12.
 */

public class ForwardQuotaionCalculateBean {

    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : {"leftExchange":"6.2","rightExchange":"6.2","leftDateLen":"60","rightDateLen":"60","chooseDateLen":"40"}
     */

    private String rtnMsg;
    private String rtnCode;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * leftExchange : 6.2
         * rightExchange : 6.2
         * leftDateLen : 60
         * rightDateLen : 60
         * chooseDateLen : 40
         */

        private String leftExchange;//左区间
        private String rightExchange;//右区间
        private String leftDateLen;//左区间时长
        private String rightDateLen;//右区间时长
        private String chooseDateLen;//选择时长

        public String getLeftExchange() {
            return leftExchange;
        }

        public void setLeftExchange(String leftExchange) {
            this.leftExchange = leftExchange;
        }

        public String getRightExchange() {
            return rightExchange;
        }

        public void setRightExchange(String rightExchange) {
            this.rightExchange = rightExchange;
        }

        public String getLeftDateLen() {
            return leftDateLen;
        }

        public void setLeftDateLen(String leftDateLen) {
            this.leftDateLen = leftDateLen;
        }

        public String getRightDateLen() {
            return rightDateLen;
        }

        public void setRightDateLen(String rightDateLen) {
            this.rightDateLen = rightDateLen;
        }

        public String getChooseDateLen() {
            return chooseDateLen;
        }

        public void setChooseDateLen(String chooseDateLen) {
            this.chooseDateLen = chooseDateLen;
        }
    }
}
