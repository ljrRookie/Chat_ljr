package com.ljr.factory.presenter.contact;

import com.ljr.common.factory.presenter.BaseContract;
import com.ljr.factory.model.db.User;

/**
 * Created by 林佳荣 on 2018/5/8.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public interface ContactContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, User> {

    }
}
