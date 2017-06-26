package com.sf.bocfinancialsoftware.bean;

/**
 * Created by Author: wangyongzhu on 2017/6/14.
 */

public class Picture {
    private int iconId;
    private String title;

    public Picture(int iconId, String title) {
        this.iconId = iconId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
