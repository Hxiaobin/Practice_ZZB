package com.sf.bocfinancialsoftware.bean;

import java.util.List;

/**
 * 热销理财产品的bean
 * Created by Author: wangyongzhu on 2017/6/28.
 */

public class ProductBean {
    private String title; //标题
    private List<String> content; //内容

    public ProductBean(String title, List<String> content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
