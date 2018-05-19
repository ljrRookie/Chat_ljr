package com.ljr.factory.presenter.account;

import com.ljr.common.factory.presenter.BaseContract;

/**
 * Created by 林佳荣 on 2018/4/24.
 * Github：https://github.com/ljrRookie
 * Function ：注册业务
 */

public interface RegisterContract {
    interface Presenter extends BaseContract.Presenter{
        //发起一个注册
        void register(String phone,String name,String password);

        //检查手机号是否正确
        boolean checkMobile(String phone);
    }
    interface View extends BaseContract.View<Presenter>{
        //注册成功
        void registerSuccess();
    }
}
