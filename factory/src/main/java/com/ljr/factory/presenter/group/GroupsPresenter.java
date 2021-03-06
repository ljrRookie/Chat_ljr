package com.ljr.factory.presenter.group;

import android.support.v7.util.DiffUtil;

import com.ljr.factory.data.group.GroupsDataSource;
import com.ljr.factory.data.group.GroupsRepository;
import com.ljr.factory.data.helper.GroupHelper;
import com.ljr.factory.model.db.Group;
import com.ljr.factory.presenter.BaseSourcePresenter;
import com.ljr.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/21.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class GroupsPresenter extends BaseSourcePresenter<Group, Group,
        GroupsDataSource, GroupsContract.View> implements GroupsContract.Presenter{

    public GroupsPresenter(GroupsContract.View view) {
        super(new GroupsRepository(),view);
    }

    @Override
    public void start() {
        super.start();
        // 加载网络数据, 以后可以优化到下拉刷新中
        // 只有用户下拉进行网络请求刷新
        GroupHelper.refreshGroups();
    }

    @Override
    public void onDataLoaded(List<Group> groups) {
        final GroupsContract.View view = getView();
        if (view == null)
            return;

        // 对比差异
        List<Group> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Group> callback = new DiffUiDataCallback<>(old, groups);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 界面刷新
        refreshData(result, groups);
    }
}
