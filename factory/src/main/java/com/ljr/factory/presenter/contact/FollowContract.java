package com.ljr.factory.presenter.contact;

import com.ljr.common.factory.presenter.BaseContract;
import com.ljr.factory.model.card.UserCard;

/**
 * Created by 林佳荣 on 2018/5/7.
 * Github：https://github.com/ljrRookie
 * Function ：关注功能的接口定义
 */

public interface FollowContract {
    // 任务调度者
    interface Presenter extends BaseContract.Presenter {
        // 关注一个人
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter> {
        // 成功的情况下返回一个用户的信息
        void onFollowSucceed(UserCard userCard);
    }
}
