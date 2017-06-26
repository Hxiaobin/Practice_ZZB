package com.sf.bocfinancialsoftware.bean;

/**
 * Created by Author: wangyongzhu on 2017/6/21.
 */

public class RateBean {
    private String rateCurrency; //币种
    private String rateTime; //期限
    private String knotRate; //结汇价
    private String sellingRate; //售汇价

    public RateBean(String rateCurrency, String rateTime, String knotRate, String sellingRate) {
        this.rateCurrency = rateCurrency;
        this.rateTime = rateTime;
        this.knotRate = knotRate;
        this.sellingRate = sellingRate;
    }

    public String getRateCurrency() {
        return rateCurrency;
    }

    public void setRateCurrency(String rateCurrency) {
        this.rateCurrency = rateCurrency;
    }

    public String getRateTime() {
        return rateTime;
    }

    public void setRateTime(String rateTime) {
        this.rateTime = rateTime;
    }

    public String getKnotRate() {
        return knotRate;
    }

    public void setKnotRate(String knotRate) {
        this.knotRate = knotRate;
    }

    public String getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(String sellingRate) {
        this.sellingRate = sellingRate;
    }
}
