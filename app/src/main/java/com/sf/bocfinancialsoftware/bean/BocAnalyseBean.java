package com.sf.bocfinancialsoftware.bean;

import java.util.List;

/**
 * 中银分析列表Bean类
 * Created by sn on 2017/6/8.
 */

public class BocAnalyseBean {

    private String newsId;       //新闻id
    private String newsTitle;    //新闻名称
    private String newsDesc;     //新闻描述
    private String newsData;     //新闻时间
    private String newsImageUrl; //新闻图片URL
    private String htmlContent;  //正文（HTML文本）
    private List<String> imageList; //详情图片URL集合
    private String carouseImageUrl; //详情轮播图片url

    public BocAnalyseBean() {
    }

    public BocAnalyseBean(String newsTitle, String newsDesc, String newsData, String newsImageUrl, String htmlContent, List<String> imageList) {
        this.newsTitle = newsTitle;
        this.newsDesc = newsDesc;
        this.newsData = newsData;
        this.newsImageUrl = newsImageUrl;
        this.htmlContent = htmlContent;
        this.imageList = imageList;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }

    public String getNewsData() {
        return newsData;
    }

    public void setNewsData(String newsData) {
        this.newsData = newsData;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getCarouseImageUrl() {
        return carouseImageUrl;
    }

    public void setCarouseImageUrl(String carouseImageUrl) {
        this.carouseImageUrl = carouseImageUrl;
    }
}
