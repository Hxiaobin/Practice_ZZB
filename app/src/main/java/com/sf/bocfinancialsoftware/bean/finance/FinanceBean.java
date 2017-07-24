package com.sf.bocfinancialsoftware.bean.finance;

import java.io.Serializable;
import java.util.List;

/**
 * 热销理财产品列表的bean
 * Created by Author: wangyongzhu on 2017/6/14.
 */

public class FinanceBean implements Serializable{
    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : {"hasNext":"0","productList":[{"productId":"1","productName":"中银QD02(中银稳健增长)","productDate":"2017-05-01"},{"productId":"2","productName":"中银QD02(中银新兴市场)","productDate":"2017-05-15"},{"productId":"3","productName":"中银QD02(中银债市通理财计划)","productDate":"2017-06-08"},{"productId":"4","productName":"债市通(中银债市通理财计划)","productDate":"2017-06-09"},{"productId":"5","productName":"中银FQF1(中银精选基金理财计划)","productDate":"2017-06-15"},{"productId":"6","productName":"中银QD02(中银精选基金理财计划)","productDate":"2017-06-16"},{"productId":"7","productName":"融货达","productDate":"2017-07-06"}]}
     */

    private String rtnMsg;//返回描述信息
    private String rtnCode;//返回码
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

    public static class ContentBean implements Serializable{
        /**
         * hasNext : 0
         * productList : [{"productId":"1","productName":"中银QD02(中银稳健增长)","productDate":"2017-05-01"},{"productId":"2","productName":"中银QD02(中银新兴市场)","productDate":"2017-05-15"},{"productId":"3","productName":"中银QD02(中银债市通理财计划)","productDate":"2017-06-08"},{"productId":"4","productName":"债市通(中银债市通理财计划)","productDate":"2017-06-09"},{"productId":"5","productName":"中银FQF1(中银精选基金理财计划)","productDate":"2017-06-15"},{"productId":"6","productName":"中银QD02(中银精选基金理财计划)","productDate":"2017-06-16"},{"productId":"7","productName":"融货达","productDate":"2017-07-06"}]
         */

        private String hasNext;//是否有下一页（0：否；1：是）
        private List<ProductListBean> productList;//产品列表

        public String getHasNext() {
            return hasNext;
        }

        public void setHasNext(String hasNext) {
            this.hasNext = hasNext;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class ProductListBean implements Serializable{
            /**
             * productId : 1
             * productName : 中银QD02(中银稳健增长)
             * productDate : 2017-05-01
             */

            private String productId;//理财产品ID
            private String productName;//理财产品名称
            private String productDate;//理财产品时间

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
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
    }
}
