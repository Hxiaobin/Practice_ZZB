package com.sf.bocfinancialsoftware.bean.account;

import java.io.Serializable;
import java.util.List;

/**
 * 财务助手账号列表的实体类
 * Created by Author: wangyongzhu on 2017/6/22.
 */

public class AccountBean implements Serializable {

    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : {"hasNext":"1","accountList":[{"accountId":"100123","accountName":"2968****7845","balance":"123","payable":"123"},{"accountId":"145123","accountName":"2978****7655","balance":"173","payable":"120"},{"accountId":"105123","accountName":"2978****7656","balance":"1655","payable":"1400"}]}
     */

    private String rtnMsg;
    private String rtnCode;
    private ContentBean content;

    @Override
    public String toString() {
        return "AccountBean{" +
                "rtnMsg='" + rtnMsg + '\'' +
                ", rtnCode='" + rtnCode + '\'' +
                ", content=" + content +
                '}';
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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable{
        /**
         * hasNext : 1
         * accountList : [{"accountId":"100123","accountName":"2968****7845","balance":"123","payable":"123"},{"accountId":"145123","accountName":"2978****7655","balance":"173","payable":"120"},{"accountId":"105123","accountName":"2978****7656","balance":"1655","payable":"1400"}]
         */

        private String hasNext;
        private List<AccountListBean> accountList;//账号列表

        @Override
        public String toString() {
            return "ContentBean{" +
                    "hasNext='" + hasNext + '\'' +
                    ", accountList=" + accountList +
                    '}';
        }

        public String getHasNext() {
            return hasNext;
        }

        public void setHasNext(String hasNext) {
            this.hasNext = hasNext;
        }

        public List<AccountListBean> getAccountList() {
            return accountList;
        }

        public void setAccountList(List<AccountListBean> accountList) {
            this.accountList = accountList;
        }

        public static class AccountListBean implements Serializable{
            /**
             * accountId : 100123
             * accountName : 2968****7845
             * balance : 123
             * payable : 123
             */

            private String accountId;//账号ID
            private String accountName;//账号名
            private String balance;//余额
            private String payable;//30天内应付账款

            @Override
            public String toString() {
                return "AccountListBean{" +
                        "accountId='" + accountId + '\'' +
                        ", accountName='" + accountName + '\'' +
                        ", balance='" + balance + '\'' +
                        ", payable='" + payable + '\'' +
                        '}';
            }

            public String getAccountId() {
                return accountId;
            }

            public void setAccountId(String accountId) {
                this.accountId = accountId;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getPayable() {
                return payable;
            }

            public void setPayable(String payable) {
                this.payable = payable;
            }
        }
    }
}

