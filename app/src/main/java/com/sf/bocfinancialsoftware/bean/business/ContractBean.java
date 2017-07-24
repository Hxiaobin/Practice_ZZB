package com.sf.bocfinancialsoftware.bean.business;

import java.util.List;

/**
 * 合同bean类
 * Created by sn on 2017/6/14.
 */

public class ContractBean {

    private String rtnMsg;       //返回信息
    private String rtnCode;      //返回码
    private Content content;     //内容

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
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

    public class Content {
        private String hasNext; //是否还有下一页
        private List<Contract> contractArray; //合同集合contractArray

        public String getHasNext() {
            return hasNext;
        }

        public void setHasNext(String hasNext) {
            this.hasNext = hasNext;
        }

        public List<Contract> getContractArray() {
            return contractArray;
        }

        public void setContractArray(List<Contract> contractArray) {
            this.contractArray = contractArray;
        }

        public class Contract {
            private String contractId; //合同Id
            private List<ContractContent> object; //合同内容集合

            public String getContractId() {
                return contractId;
            }

            public void setContractId(String contractId) {
                this.contractId = contractId;
            }

            public List<ContractContent> getObject() {
                return object;
            }

            public void setObject(List<ContractContent> object) {
                this.object = object;
            }

            public class ContractContent {
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
