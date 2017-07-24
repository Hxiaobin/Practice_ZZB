package com.sf.bocfinancialsoftware.bean.rate;

import java.util.List;

/**
 * 汇率的bean
 * Created by Author: wangyongzhu on 2017/6/21.
 */

public class ExchangeRateBean {

    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : [{"moneyType":"412356","moneyName":"美元","timeLimit":"期限","settlePrice":"6.2172","salePrice":"6.2418"},{"moneyType":"412356","moneyName":"美元","timeLimit":"1年","settlePrice":"6.2813","salePrice":"6.3147"},{"moneyType":"412356","moneyName":"美元","timeLimit":"6个月","settlePrice":"6.3184","salePrice":"6.3522"},{"moneyType":"412356","moneyName":"美元","timeLimit":"1年","settlePrice":"6.3795","salePrice":"6.4164"},{"moneyType":"412357","moneyName":"欧元","timeLimit":"期限","settlePrice":"7.2172","salePrice":"7.2418"},{"moneyType":"412357","moneyName":"欧元","timeLimit":"3个月","settlePrice":"7.2813","salePrice":"7.3147"},{"moneyType":"412357","moneyName":"欧元","timeLimit":"6个月","settlePrice":"7.3184","salePrice":"7.3522"},{"moneyType":"412357","moneyName":"美元","timeLimit":"1年","settlePrice":"7.3795","salePrice":"7.4164"},{"moneyType":"412358","moneyName":"日元","timeLimit":"3个月","settlePrice":"6.3172","salePrice":"6.3418"},{"moneyType":"412358","moneyName":"日元","timeLimit":"6个月","settlePrice":"6.3813","salePrice":"6.4147"},{"moneyType":"412358","moneyName":"日元","timeLimit":"1年","settlePrice":"6.3284","salePrice":"6.3422"},{"moneyType":"412358","moneyName":"日元","timeLimit":"1年","settlePrice":"6.3795","salePrice":"6.3164"}]
     */

    private String rtnMsg;
    private String rtnCode;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * moneyType : 412356
         * moneyName : 美元
         * timeLimit : 期限
         * settlePrice : 6.2172
         * salePrice : 6.2418
         */

        private String moneyType;//币种类别
        private String moneyName;//币种名称
        private String timeLimit;//期限
        private String settlePrice;//结汇价
        private String salePrice;//售汇价

        public String getMoneyType() {
            return moneyType;
        }

        public void setMoneyType(String moneyType) {
            this.moneyType = moneyType;
        }

        public String getMoneyName() {
            return moneyName;
        }

        public void setMoneyName(String moneyName) {
            this.moneyName = moneyName;
        }

        public String getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(String timeLimit) {
            this.timeLimit = timeLimit;
        }

        public String getSettlePrice() {
            return settlePrice;
        }

        public void setSettlePrice(String settlePrice) {
            this.settlePrice = settlePrice;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }
    }
}
