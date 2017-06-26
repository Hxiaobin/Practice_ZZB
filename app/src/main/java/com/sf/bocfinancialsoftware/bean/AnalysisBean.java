package com.sf.bocfinancialsoftware.bean;

import java.io.Serializable;

/**
 * Created by Author: wangyongzhu on 2017/6/14.
 */

public class AnalysisBean  implements Serializable{
    private String title;
    private String content;
    private String time;
    private int image;

    public AnalysisBean(String title, String content, String time, int image) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
