package com.ljr.factory.net;

import com.ljr.factory.model.api.RspModel;
import com.ljr.factory.model.api.account.AccountRspModel;
import com.ljr.factory.model.api.account.LoginModel;
import com.ljr.factory.model.api.account.RegisterModel;
import com.ljr.factory.model.api.group.GroupCreateModel;
import com.ljr.factory.model.api.message.MsgCreateModel;
import com.ljr.factory.model.api.user.UserUpdateModel;
import com.ljr.factory.model.card.GroupCard;
import com.ljr.factory.model.card.GroupMemberCard;
import com.ljr.factory.model.card.MessageCard;
import com.ljr.factory.model.card.UserCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    // 用户搜索的接口
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> userSearch(@Path("name") String name);

    // 用户关注接口
    @PUT("user/follow/{userId}")
    Call<RspModel<UserCard>> userFollow(@Path("userId") String userId);
    // 获取联系人列表
    @GET("user/contact")
    Call<RspModel<List<UserCard>>> userContacts();
    // 用户更新的接口
    @PUT("user")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

    // 查询某人的信息
    @GET("user/{userId}")
    Call<RspModel<UserCard>> userFind(@Path("userId") String userId);

    // 创建群
    @POST("group")
    Call<RspModel<GroupCard>> groupCreate(@Body GroupCreateModel model);

    // 群搜索的接口
    @GET("group/search/{name}")
    Call<RspModel<List<GroupCard>>> groupSearch(@Path(value = "name", encoded = true) String name);

    // 我的群的成员列表
    @GET("group/{groupId}/member")
    Call<RspModel<List<GroupMemberCard>>> groupMembers(@Path("groupId") String groupId);

    // 我的群列表
    @GET("group/list/{date}")
    Call<RspModel<List<GroupCard>>> groups(@Path(value = "date", encoded = true) String date);

    // 发送消息的接口
    @POST("msg")
    Call<RspModel<MessageCard>> msgPush(@Body MsgCreateModel model);
}
