package com.ljr.factory.presenter.contact;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.ljr.common.factory.data.DataSource;
import com.ljr.common.factory.presenter.BasePresenter;
import com.ljr.common.recycler.RecyclerAdapter;
import com.ljr.factory.R;
import com.ljr.factory.data.helper.DbHelper;
import com.ljr.factory.data.helper.UserHelper;
import com.ljr.factory.data.user.ContactDataSource;
import com.ljr.factory.data.user.ContactRepository;
import com.ljr.factory.model.db.User;
import com.ljr.factory.model.db.User_Table;
import com.ljr.factory.persistence.Account;
import com.ljr.factory.presenter.BaseSourcePresenter;
import com.ljr.factory.utils.DiffUiDataCallback;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/8.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class ContactPresenter extends BaseSourcePresenter<User, User, ContactDataSource, ContactContract.View>
        implements ContactContract.Presenter, DataSource.SucceedCallback<List<User>> {


    public ContactPresenter(ContactContract.View view) {
        //初始化数据仓库
        super(new ContactRepository(), view);
    }

    @Override
    public void start() {
        super.start();
        // 加载网络数据
        UserHelper.refreshContacts();

    }

    //运行到这里的时候是子线程
    @Override
    public void onDataLoaded(List<User> users) {
        // 无论怎么操作，数据变更，最终都会通知到这里来
        final ContactContract.View view = getView();
        if (view == null)
            return;

        RecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> old = adapter.getItems();

        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 调用基类方法进行界面刷新
        refreshData(result, users);
    }
}