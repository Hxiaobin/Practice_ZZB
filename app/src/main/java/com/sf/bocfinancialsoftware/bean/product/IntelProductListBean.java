package com.sf.bocfinancialsoftware.bean.product;

import java.io.Serializable;
import java.util.List;

/**
 * 产品介绍列表bean
 * Created by Author: wangyongzhu on 2017/7/14.
 */

public class IntelProductListBean implements Serializable{

    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : {"hasNext":"0","typeList":[{"typeId":"123546","typeName":"供应链融资","productArray":[{"productId":"45423","productName":"信用证通知"},{"productId":"871425","productName":"信用证开立"}]}]}
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

    public static class ContentBean implements Serializable{
        /**
         * hasNext : 0
         * typeList : [{"typeId":"123546","typeName":"供应链融资","productArray":[{"productId":"45423","productName":"信用证通知"},{"productId":"871425","productName":"信用证开立"}]}]
         */

        private String hasNext;
        private List<TypeListBean> typeList;//分页列表

        public String getHasNext() {
            return hasNext;
        }

        public void setHasNext(String hasNext) {
            this.hasNext = hasNext;
        }

        public List<TypeListBean> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<TypeListBean> typeList) {
            this.typeList = typeList;
        }

        public static class TypeListBean implements Serializable{
            /**
             * typeId : 123546
             * typeName : 供应链融资
             * productArray : [{"productId":"45423","productName":"信用证通知"},{"productId":"871425","productName":"信用证开立"}]
             */

            private String typeId;//类别ID
            private String typeName;//类别名称
            private List<ProductArrayBean> productArray;//国结产品列表

            public String getTypeId() {
                return typeId;
            }

            public void setTypeId(String typeId) {
                this.typeId = typeId;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public List<ProductArrayBean> getProductArray() {
                return productArray;
            }

            public void setProductArray(List<ProductArrayBean> productArray) {
                this.productArray = productArray;
            }

            public static class ProductArrayBean implements Serializable{
                /**
                 * productId : 45423
                 * productName : 信用证通知
                 */

                private String productId;//国结产品ID
                private String productName;//国结产品名称

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
            }
        }
    }
}
