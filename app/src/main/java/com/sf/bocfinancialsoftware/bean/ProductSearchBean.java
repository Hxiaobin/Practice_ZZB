package com.sf.bocfinancialsoftware.bean;

/**
 * Created by Author: wangyongzhu on 2017/7/3.
 */

public class ProductSearchBean {
    private String tvResults;//产品介绍查询后显示的数据

    public ProductSearchBean(String tvResults) {
        this.tvResults = tvResults;
    }

    public String getTvResults() {
        return tvResults;
    }

    public void setTvResults(String tvResults) {
        this.tvResults = tvResults;
    }
}
