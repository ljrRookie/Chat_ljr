package com.ljr.factory.presenter.message;


import com.ljr.factory.data.helper.GroupHelper;
import com.ljr.factory.data.message.MessageDataSource;
import com.ljr.factory.data.message.MessageGroupRepository;
import com.ljr.factory.model.db.Group;
import com.ljr.factory.model.db.Message;
import com.ljr.factory.model.db.view.MemberUserModel;
import com.ljr.factory.persistence.Account;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/29.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class ChatGroupPresenter extends ChatPresenter<ChatContract.GroupView> implements ChatContract.Presenter {


    public ChatGroupPresenter(ChatContract.GroupView view, String receiverId) {
        super(new MessageGroupRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_GROUP);
    }

    @Override
    public void start() {
        super.start();
        // 拿群的信息
        Group group = GroupHelper.findFromLocal(mReceiverId);
        if (group != null) {
            // 初始化操作
            ChatContract.GroupView view = getView();

            boolean isAdmin = Account.getUserId().equalsIgnoreCase(group.getOwner().getId());
            view.showAdminOption(isAdmin);

            // 基础信息初始化
            view.onInit(group);

            // 成员初始化
            List<MemberUserModel> models = group.getLatelyGroupMembers();
            final long memberCount = group.getGroupMemberCount();
            // 没有显示的成员的数量
            long moreCount = memberCount - models.size();
            view.onInitGroupMembers(models, moreCount);
        }
    }
}
