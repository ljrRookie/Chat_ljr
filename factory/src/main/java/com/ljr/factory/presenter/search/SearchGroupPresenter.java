package com.ljr.factory.presenter.search;

import android.provider.ContactsContract;
import android.support.annotation.StringRes;

import com.ljr.common.factory.data.DataSource;
import com.ljr.common.factory.presenter.BasePresenter;
import com.ljr.factory.data.helper.GroupHelper;
import com.ljr.factory.model.card.GroupCard;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/7.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class SearchGroupPresenter extends BasePresenter<SearchContract.GroupView>
        implements SearchContract.Presenter,DataSource.Callback<List<GroupCard>> {


    public SearchGroupPresenter(SearchContract.GroupView view) {
        super(view);
    }

    @Override
    public void search(String content) {
        start();
        GroupHelper.search(content,this);
    }



    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {
        // 搜索失败
        final SearchContract.GroupView view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(strRes);
                }
            });
        }
    }

    @Override
    public void onDataLoaded(final List<GroupCard> groupCards) {
        final SearchContract.GroupView view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onSearchDone(groupCards);
                }
            });
        }
    }
}
