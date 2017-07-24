package com.sf.bocfinancialsoftware.bean;

import android.graphics.Bitmap;

import java.util.List;

/**
 * 中银分析列表Bean类
 * Created by sn on 2017/6/8.
 */

public class BocAnalyseBean {

    private String rtnMsg;       //返回信息
    private String rtnCode;      //返回码
    private Content content;     //内容

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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public class Content {

        private String hasNext;  //是否有下一页
        private List<NewsBean> newsArray;  //新闻列表
        private String htmlContent; //正文内容
        private List<NewsImageBean> imageList;  //轮播图片对象

        public String getHtmlContent() {
            return htmlContent;
        }

        public void setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
        }

        public List<NewsImageBean> getImageList() {
            return imageList;
        }

        public void setImageList(List<NewsImageBean> imageList) {
            this.imageList = imageList;
        }

        public String getHasNext() {
            return hasNext;
        }

        public void setHasNext(String hasNext) {
            this.hasNext = hasNext;
        }

        public List<NewsBean> getNewsArray() {
            return newsArray;
        }

        public void setNewsArray(List<NewsBean> newsArray) {
            this.newsArray = newsArray;
        }

        public class NewsBean {

            private String newsId;       //新闻id
            private String newsTitle;    //新闻名称
            private String newsDesc;     //新闻描述
            private String newsDate;     //新闻时间
            private String newsImageUrl; //新闻图片URL
            private String htmlContent;  //正文（HTML文本）
            private List<Bitmap> imageList; //详情图片URL集合
            private String carouseImageUrl; //详情轮播图片url

            public NewsBean(String newsId, String newsTitle, String newsDesc, String newsDate, String newsImageUrl) {
                this.newsId = newsId;
                this.newsTitle = newsTitle;
                this.newsDesc = newsDesc;
                this.newsDate = newsDate;
                this.newsImageUrl = newsImageUrl;
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

            public String getNewsDate() {
                return newsDate;
            }

            public void setNewsDate(String newsDate) {
                this.newsDate = newsDate;
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

            public List<Bitmap> getImageList() {
                return imageList;
            }

            public void setImageList(List<Bitmap> imageList) {
                this.imageList = imageList;
            }

            public String getCarouseImageUrl() {
                return carouseImageUrl;
            }

            public void setCarouseImageUrl(String carouseImageUrl) {
                this.carouseImageUrl = carouseImageUrl;
            }
        }

        public class NewsImageBean {
            private String imageUrl; //轮播图片的url

            public NewsImageBean(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }
        }
    }

}
