package com.sf.bocfinancialsoftware.bean.business;

import java.util.List;

/**
 * 业务列表Bean类
 * Created by sn on 2017/7/18.
 */

public class BusinessBean {

    private String rtnMsg;       //返回信息
    private String rtnCode;      //返回码
    private List<BusinessTypeBean> content;     //内容

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

    public List<BusinessTypeBean> getContent() {
        return content;
    }

    public void setContent(List<BusinessTypeBean> content) {
        this.content = content;
    }

    public class BusinessTypeBean {
        private String typeId; //业务类别id
        private String typeName; //业务lei别名称
        private List<Business> businessArray; //业务集合

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<Business> getBusinessArray() {
            return businessArray;
        }

        public void setBusinessArray(List<Business> businessArray) {
            this.businessArray = businessArray;
        }

        public class Business {
            private String businessId;   //业务ID
            private String businessName; //业务名称
            private String dateName;     //业务时间
            private String contractId;    //合同ID

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
            }

            public String getDateName() {
                return dateName;
            }

            public void setDateName(String dateName) {
                this.dateName = dateName;
            }

            public String getContractId() {
                return contractId;
            }

            public void setContractId(String contractId) {
                this.contractId = contractId;
            }
        }
    }

}
