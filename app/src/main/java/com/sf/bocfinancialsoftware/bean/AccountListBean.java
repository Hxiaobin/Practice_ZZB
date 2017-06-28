package com.sf.bocfinancialsoftware.bean;

/**
 * 财务助手的bean
 * Created by Author: wangyongzhu on 2017/6/22.
 */

public class AccountListBean {
    private String tvAccount; //账户
    private String tvBalance; //余额
    private String tvPay;     //账款

    public AccountListBean(String tvAccount, String tvBalance, String tvPay) {
        this.tvAccount = tvAccount;
        this.tvBalance = tvBalance;
        this.tvPay = tvPay;
    }

    public String getTvAccount() {
        return tvAccount;
    }

    public void setTvAccount(String tvAccount) {
        this.tvAccount = tvAccount;
    }

    public String getTvBalance() {
        return tvBalance;
    }

    public void setTvBalance(String tvBalance) {
        this.tvBalance = tvBalance;
    }

    public String getTvPay() {
        return tvPay;
    }

    public void setTvPay(String tvPay) {
        this.tvPay = tvPay;
    }
}
