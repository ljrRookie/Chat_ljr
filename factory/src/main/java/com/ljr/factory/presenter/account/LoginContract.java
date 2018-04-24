package com.ljr.factory.presenter.account;

import com.ljr.common.factory.BaseContract;

/**
 * Created by 林佳荣 on 2018/4/23.
 * Github：https://github.com/ljrRookie
 * Function ：登录业务
 */

public interface LoginContract {
    interface View extends BaseContract.View<Presenter> {
        //登录成功
        void loginSuccess();
    }
    interface Presenter extends BaseContract.Presenter{
        //发起一个登录
        void login(String phone, String password);
    }
}
