package com.sf.bocfinancialsoftware.bean.account;

import java.util.List;

/**
 * 财务助手账号详情的实体类
 * Created by Author: wangyongzhu on 2017/6/22.
 */

public class AccountDetailBean {
    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : {"hasNext":" ","contractList":[{"contractId":"100123","contractNum":"2968****7845","contractName":"融货达","contractInfo":[{"key":"合同日期","value":"2013-11-01"},{"key":"到期日","value":"2015-11-01"},{"key":"合同金额","value":"11,000,000.00"},{"key":"贷款利率","value":"4.171"},{"key":"币种","value":"人民币"},{"key":"融资金额","value":"28,000,000.00元"}]}]}
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
         * hasNext :
         * contractList : [{"contractId":"100123","contractNum":"2968****7845","contractName":"融货达","contractInfo":[{"key":"合同日期","value":"2013-11-01"},{"key":"到期日","value":"2015-11-01"},{"key":"合同金额","value":"11,000,000.00"},{"key":"贷款利率","value":"4.171"},{"key":"币种","value":"人民币"},{"key":"融资金额","value":"28,000,000.00元"}]}]
         */

        private int hasNext;
        private List<ContractListBean> contractList;

        public int getHasNext() {
            return hasNext;
        }

        public void setHasNext(int hasNext) {
            this.hasNext = hasNext;
        }

        public List<ContractListBean> getContractList() {
            return contractList;
        }

        public void setContractList(List<ContractListBean> contractList) {
            this.contractList = contractList;
        }

        public static class ContractListBean {
            /**
             * contractId : 100123
             * contractNum : 2968****7845
             * contractName : 融货达
             * contractInfo : [{"key":"合同日期","value":"2013-11-01"},{"key":"到期日","value":"2015-11-01"},{"key":"合同金额","value":"11,000,000.00"},{"key":"贷款利率","value":"4.171"},{"key":"币种","value":"人民币"},{"key":"融资金额","value":"28,000,000.00元"}]
             */

            private String contractId;
            private String contractNum;
            private String contractName;
            private List<ContractInfoBean> contractInfo;

            public String getContractId() {
                return contractId;
            }

            public void setContractId(String contractId) {
                this.contractId = contractId;
            }

            public String getContractNum() {
                return contractNum;
            }

            public void setContractNum(String contractNum) {
                this.contractNum = contractNum;
            }

            public String getContractName() {
                return contractName;
            }

            public void setContractName(String contractName) {
                this.contractName = contractName;
            }

            public List<ContractInfoBean> getContractInfo() {
                return contractInfo;
            }

            public void setContractInfo(List<ContractInfoBean> contractInfo) {
                this.contractInfo = contractInfo;
            }

            public static class ContractInfoBean {
                /**
                 * key : 合同日期
                 * value : 2013-11-01
                 */

                private String key;
                private String value;

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}

