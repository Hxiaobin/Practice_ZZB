package com.sf.bocfinancialsoftware.bean;

/**
 * 热销理财产品的bean
 * Created by Author: wangyongzhu on 2017/6/28.
 */

public class ProductBean {
    private String title; //标题
    private String concent; //内容

    public ProductBean(String title, String concent) {
        this.title = title;
        this.concent = concent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConcent() {
        return concent;
    }

    public void setConcent(String concent) {
        this.concent = concent;
    }
}
