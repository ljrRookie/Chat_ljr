package com.ljr.factory.net;

import com.ljr.factory.presenter.model.api.RspModel;
import com.ljr.factory.presenter.model.api.account.AccountRspModel;
import com.ljr.factory.presenter.model.api.account.LoginModel;
import com.ljr.factory.presenter.model.api.account.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 林佳荣 on 2018/4/24.
 * Github：https://github.com/ljrRookie
 * Function ：网络请求的所有的接口
 */

public interface RemoteService {
    /**
     * 注册接口
     * @param model 传入RegisterModel
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);
    /**
     * 登录接口
     *
     * @param model LoginModel
     * @return RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);
    /**
     * 绑定设备Id
     *
     * @param pushId 设备Id
     * @return RspModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);
}
