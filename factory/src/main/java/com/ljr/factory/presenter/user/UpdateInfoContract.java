package com.ljr.factory.presenter.user;

import com.ljr.common.factory.presenter.BaseContract;

/**
 * Created by 林佳荣 on 2018/5/4.
 * Github：https://github.com/ljrRookie
 * Function ：更新用户信息的基本契约
 */

public interface UpdateInfoContract {
    interface Presenter extends BaseContract.Presenter{
        //更新
        void update(String photoFilePath,String desc,boolean isMan);
    }
    interface View extends BaseContract.View<Presenter>{
        // 回调成功
        void updateSucceed();
    }
}
