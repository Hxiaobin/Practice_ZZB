package com.sf.bocfinancialsoftware.bean.user;

/**
 * Created by Author: wangyongzhu on 2017/7/19.
 */

public class CheckVersion {

    /**
     * rtnMsg : Operate Success
     * rtnCode : 10000
     * content : {"needUpdate":"Y","downloadUrl":"xxx","version":"1.0.0","newContent":"xxx"}
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
         * needUpdate : Y
         * downloadUrl : xxx
         * version : 1.0.0
         * newContent : xxx
         */

        private String needUpdate;//是否强制更新：Y是；N否
        private String downloadUrl;//APP下载地址
        private String version;//APP最新版本号
        private String newContent;//更新内容

        public String getNeedUpdate() {
            return needUpdate;
        }

        public void setNeedUpdate(String needUpdate) {
            this.needUpdate = needUpdate;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getNewContent() {
            return newContent;
        }

        public void setNewContent(String newContent) {
            this.newContent = newContent;
        }
    }
}
