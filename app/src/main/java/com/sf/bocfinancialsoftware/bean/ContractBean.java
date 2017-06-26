package com.sf.bocfinancialsoftware.bean;

import java.util.HashMap;

/**
 * 合同bean类
 * Created by sn on 2017/6/14.
 */

public class ContractBean {

    private String autoId;//合同id
    private String contractId; //合同编号
    private String businessName; //业务id
    private String startDate; //开始时间
    private String endDate; //结束时间
    private String signDate; //签订时间
    private HashMap<Object, Object> mapObject; //返回对象

    public ContractBean() {
    }

    public ContractBean(String contractId, String businessName, String signDate, HashMap<Object, Object> mapObject) {
        this.contractId = contractId;
        this.businessName = businessName;
        this.signDate = signDate;
        this.mapObject = mapObject;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public HashMap<Object, Object> getMapObject() {
        return mapObject;
    }

    public void setMapObject(HashMap<Object, Object> mapObject) {
        this.mapObject = mapObject;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }
}
