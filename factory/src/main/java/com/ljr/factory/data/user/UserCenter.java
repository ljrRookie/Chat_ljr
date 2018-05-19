package com.ljr.factory.data.user;

import com.ljr.factory.model.card.UserCard;

/**
 * Created by 林佳荣 on 2018/5/9.
 * Github：https://github.com/ljrRookie
 * Function ：用户中心的基本定义
 */

public interface  UserCenter {
    // 分发处理一堆用户卡片的信息，并更新到数据库
    void dispatch(UserCard... cards);
}
