package com.sf.bocfinancialsoftware.bean;

import java.util.List;

/**
 * 业务类别Bean类
 * Created by sn on 2017/6/14.
 */

public class BusinessTypeBean {

    private String typeId; //业务类别id
    private String typeName; //业务lei别名称
    private List<BusinessBean> businessArray; //业务集合

    public BusinessTypeBean() {
    }

    public BusinessTypeBean(String typeName) {
        this.typeName = typeName;
    }

    public BusinessTypeBean(String typeId, String typeName, List<BusinessBean> businessArray) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.businessArray = businessArray;
    }

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

    public List<BusinessBean> getBusinessArray() {
        return businessArray;
    }

    public void setBusinessArray(List<BusinessBean> businessArray) {
        this.businessArray = businessArray;
    }

}
