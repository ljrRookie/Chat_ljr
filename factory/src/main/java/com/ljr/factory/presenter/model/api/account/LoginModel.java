package com.ljr.factory.presenter.model.api.account;

/**
 * Created by 林佳荣 on 2018/4/23.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class LoginModel {
    private String account;
    private String password;
    private String pushId;

    public LoginModel(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public LoginModel(String account, String password, String pushId) {
        this.account = account;
        this.password = password;
        this.pushId = pushId;
    }

    public String getAccount() {
        return account == null ? "" : account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushId() {
        return pushId == null ? "" : pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
