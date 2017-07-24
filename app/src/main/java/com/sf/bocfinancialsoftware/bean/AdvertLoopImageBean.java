package com.sf.bocfinancialsoftware.bean;

import android.graphics.Bitmap;

import java.util.List;

/**
 * 轮播图实体类
 * Created by sn on 2017/7/13.
 */

public class AdvertLoopImageBean {
    private String rtnMsg;       //返回信息
    private String rtnCode;      //返回码
    private List<AdvertImage> content;     //内容


    public class AdvertImage {  //图片对象
        private String imgUrl;//图片Url

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

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

    public List<AdvertImage> getContent() {
        return content;
    }

    public void setContent(List<AdvertImage> content) {
        this.content = content;
    }
}
