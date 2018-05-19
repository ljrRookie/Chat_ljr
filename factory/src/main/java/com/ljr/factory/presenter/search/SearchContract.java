package com.ljr.factory.presenter.search;

import com.ljr.common.factory.presenter.BaseContract;
import com.ljr.factory.model.card.GroupCard;
import com.ljr.factory.model.card.UserCard;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/7.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public interface SearchContract {
    interface Presenter extends BaseContract.Presenter {
        // 搜索内容
        void search(String content);
    }

    // 搜索人的界面
    interface UserView extends BaseContract.View<Presenter> {
        void onSearchDone(List<UserCard> userCards);
    }

    // 搜索群的界面
    interface GroupView extends BaseContract.View<Presenter> {
        void onSearchDone(List<GroupCard> groupCards);
    }
}
