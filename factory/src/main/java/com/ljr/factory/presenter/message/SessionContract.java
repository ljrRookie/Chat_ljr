package com.ljr.factory.presenter.message;

import com.ljr.common.factory.presenter.BaseContract;

/**
 * Created by 林佳荣 on 2018/5/4.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class SessionContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

 /*   // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, Session> {

    }*/
}
