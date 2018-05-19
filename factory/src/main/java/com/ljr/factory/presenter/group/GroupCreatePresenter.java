package com.ljr.factory.presenter.group;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.ljr.common.factory.data.DataSource;
import com.ljr.common.factory.presenter.BaseRecyclerPresenter;
import com.ljr.factory.R;
import com.ljr.factory.model.card.GroupCard;
import com.ljr.factory.model.db.view.UserSampleModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 林佳荣 on 2018/5/5.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class GroupCreatePresenter extends BaseRecyclerPresenter<GroupCreateContract.ViewModel, GroupCreateContract.View>
        implements GroupCreateContract.Presenter, DataSource.Callback<GroupCard> {
    private Set<String> users = new HashSet<>();

    public GroupCreatePresenter(GroupCreateContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        loadData();
    }

    private void loadData() {
        List<GroupCreateContract.ViewModel> models = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GroupCreateContract.ViewModel viewModel = new GroupCreateContract.ViewModel();
            viewModel.mAuthor = new UserSampleModel(i + "", "测试好友" + i, "");
            models.add(viewModel);
        }
        refreshData(models);
    }

    @Override
    public void create(String name, String desc, String picture) {
        GroupCreateContract.View view = getView();
        view.showLoading();

        // 判断参数
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(desc) ||
                TextUtils.isEmpty(picture) || users.size() == 0) {
            view.showError(R.string.label_group_create_invalid);
            return;
        } else {
            view.onCreateSucceed();
        }

    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {
        if (isSelected)
            users.add(model.mAuthor.getId());
        else
            users.remove(model.mAuthor.getId());

    }

    @Override
    public void onDataLoaded(GroupCard groupCard) {

    }

    @Override
    public void onDataNotAvailable(@StringRes int strRes) {

    }
}
