package com.ljr.factory.presenter.message;

import android.support.v7.util.DiffUtil;

import com.ljr.factory.data.message.SessionDataSource;
import com.ljr.factory.data.message.SessionRepository;
import com.ljr.factory.model.db.Session;
import com.ljr.factory.presenter.BaseSourcePresenter;
import com.ljr.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/30.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class SessionPresenter extends BaseSourcePresenter<Session, Session,
        SessionDataSource, SessionContract.View> implements SessionContract.Presenter {

    public SessionPresenter(SessionContract.View view) {
        super(new SessionRepository(), view);
    }

    @Override
    public void onDataLoaded(List<Session> sessions) {
        SessionContract.View view = getView();
        if (view == null)
            return;

        // 差异对比
        List<Session> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Session> callback = new DiffUiDataCallback<>(old, sessions);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 刷新界面
        refreshData(result, sessions);
    }
}
