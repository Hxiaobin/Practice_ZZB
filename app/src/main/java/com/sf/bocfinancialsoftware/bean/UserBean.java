package com.sf.bocfinancialsoftware.bean;

import java.io.Serializable;

/**
 * 用户的bean
 * Created by Author: wangyongzhu on 2017/6/12.
 */

public class UserBean implements Serializable{
    private String userName;      //用户名
    private String loginPassword; //登录密码
    private boolean isLogin;

    public UserBean(String userName, String loginPassword) {
        this.userName = userName;
        this.loginPassword = loginPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
