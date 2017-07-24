package com.sf.bocfinancialsoftware.bean.calculate;

import java.util.List;

/**
 * 汇率计算器
 * Created by Author: wangyongzhu on 2017/7/12.
 */

public class ExchangeRateCalculateBean {

    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : [{"currency":"USD","currencyName":"美元","exchangeSurrendering":"0.12","exchangeSettlement":"6.12"},{"currency":"USD","currencyName":"美元","exchangeSurrendering":"0.12","exchangeSettlement":"6.12"}]
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
         * exchangeSurrendering : 0.12
         * exchangeSettlement : 6.12
         */

        private String currency;//币种
        private String currencyName;//币种名
        private String exchangeSurrendering;//售汇牌价
        private String exchangeSettlement;//结汇牌价

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

        public String getExchangeSurrendering() {
            return exchangeSurrendering;
        }

        public void setExchangeSurrendering(String exchangeSurrendering) {
            this.exchangeSurrendering = exchangeSurrendering;
        }

        public String getExchangeSettlement() {
            return exchangeSettlement;
        }

        public void setExchangeSettlement(String exchangeSettlement) {
            this.exchangeSettlement = exchangeSettlement;
        }
    }
}
