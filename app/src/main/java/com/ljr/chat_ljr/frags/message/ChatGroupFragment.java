package com.ljr.chat_ljr.frags.message;

import android.support.design.widget.AppBarLayout;
import android.view.MenuItem;
import android.view.View;

import com.ljr.chat_ljr.R;
import com.ljr.common.factory.presenter.BaseContract;
import com.ljr.common.recycler.RecyclerAdapter;
import com.ljr.common.widget.PortraitView;
import com.ljr.factory.model.db.Group;
import com.ljr.factory.model.db.User;
import com.ljr.factory.model.db.view.MemberUserModel;
import com.ljr.factory.presenter.message.ChatContract;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 林佳荣 on 2018/5/22.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class ChatGroupFragment extends ChatFragment<Group> implements ChatContract.GroupView{


    @Override
    protected ChatContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public void onInit(Group group) {

    }

    @Override
    public void showAdminOption(boolean isAdmin) {

    }

    @Override
    public void onInitGroupMembers(List<MemberUserModel> members, long moreCount) {

    }

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }
}
