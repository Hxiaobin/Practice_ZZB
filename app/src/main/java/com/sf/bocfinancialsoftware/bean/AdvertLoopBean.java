package com.sf.bocfinancialsoftware.bean;

import java.util.List;

/**
 * 广告轮播图bean
 * Created by Author: wangyongzhu on 2017/7/17.
 */

public class AdvertLoopBean {

    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : [{"imageUrl":"http://192.168.2.123/zzb/banner1.jpg","type":"1","desc":"广告描述","attribute":"理财产品id","carouselTitle":"广告标题"},{"imageUrl":"http://192.168.2.123/zzb/banner2.jpg","type":"1","desc":"广告描述","attribute":"理财产品id","carouselTitle":"广告标题"},{"imageUrl":"http://192.168.2.123/zzb/banner3.jpg","type":"1","desc":"广告描述","attribute":"理财产品id","carouselTitle":"广告标题"}]
     */

    private String rtnMsg;
    private String rtnCode;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * imageUrl : http://192.168.2.123/zzb/banner1.jpg
         * type : 1
         * desc : 广告描述
         * attribute : 理财产品id
         * carouselTitle : 广告标题
         */

        private String imageUrl;//广告图片url
        private String type;//链接类型 1：网页；2 理财产品。默认为无
        private String desc;//广告简述
        private String attribute;//根据type字段确定为网页URL或理财产品id
        private String carouselTitle;//广告标题

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getCarouselTitle() {
            return carouselTitle;
        }

        public void setCarouselTitle(String carouselTitle) {
            this.carouselTitle = carouselTitle;
        }
    }
}
