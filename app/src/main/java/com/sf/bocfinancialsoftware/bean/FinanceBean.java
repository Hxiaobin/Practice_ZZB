package com.sf.bocfinancialsoftware.bean;

import java.io.Serializable;

/**
 * 热销理财产品的bean
 * Created by Author: wangyongzhu on 2017/6/14.
 */

public class FinanceBean implements Serializable{
    private String productName; //产品名
    private String productDate; //日期

    public FinanceBean(String productName, String productDate) {
        this.productName = productName;
        this.productDate = productDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }
}
