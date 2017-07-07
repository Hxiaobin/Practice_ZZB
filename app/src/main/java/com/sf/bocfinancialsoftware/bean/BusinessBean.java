package com.sf.bocfinancialsoftware.bean;

/**
 * 业务Bean类
 * Created by sn on 2017/6/14.
 */

public class BusinessBean {

    private String businessId; //业务id
    private String typeName; //业务类别
    private String businessName; //业务名称
    private String dateName; //日期名称（可为空，为空APP不显示相应栏位）
    private String idName; //编号名称（可为空，为空APP不显示相应栏位）

    public BusinessBean(String businessName) {
        this.businessName = businessName;
    }

    public BusinessBean(String typeName, String businessName) {
        this.typeName = typeName;
        this.businessName = businessName;
    }

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

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
