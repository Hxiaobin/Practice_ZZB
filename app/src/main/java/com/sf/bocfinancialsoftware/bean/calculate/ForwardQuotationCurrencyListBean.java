package com.sf.bocfinancialsoftware.bean.calculate;

import java.util.List;

/**
 * Created by Author: wangyongzhu on 2017/7/18.
 */

public class ForwardQuotationCurrencyListBean {

    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : [{"currency":"USD","currencyName":"美元"},{"currency":"Euro","currencyName":"欧元"},{"currency":"Japan","currencyName":"日元"}]
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
         * currency : USD
         * currencyName : 美元
         */

        private String currency;//币种
        private String currencyName;//币种名

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }
    }
}
