package com.sf.bocfinancialsoftware.bean.finance;

import java.util.List;

/**
 * 热销理财产品详情bean
 * Created by Author: wangyongzhu on 2017/7/12.
 */

public class FinanceDetailBean {

    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : {"htmlContent":"http://192.168.2.123/zzb/tool/finance/financialdetail.html","imageList":[{"imageUrl":"http://192.168.2.123/zzb/tool/banner1.jpg"},{"imageUrl":"http://192.168.2.123/zzb/tool/banner2.jpg"},{"imageUrl":"http://192.168.2.123/zzb/tool/banner3.jpg"}]}
     */

    private String rtnMsg;
    private String rtnCode;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * htmlContent : http://192.168.2.123/zzb/tool/finance/financialdetail.html
         * imageList : [{"imageUrl":"http://192.168.2.123/zzb/tool/banner1.jpg"},{"imageUrl":"http://192.168.2.123/zzb/tool/banner2.jpg"},{"imageUrl":"http://192.168.2.123/zzb/tool/banner3.jpg"}]
         */

        private String htmlContent;//正文（HTML文本）
        private List<ImageListBean> imageList;//图片URL数组

        public String getHtmlContent() {
            return htmlContent;
        }

        public void setHtmlContent(String htmlContent) {
            this.htmlContent = htmlContent;
        }

        public List<ImageListBean> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageListBean> imageList) {
            this.imageList = imageList;
        }

        public static class ImageListBean {
            /**
             * imageUrl : http://192.168.2.123/zzb/tool/banner1.jpg
             */

            private String imageUrl;//图片URL

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }
        }
    }
}
