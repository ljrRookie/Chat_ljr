package com.ljr.factory.presenter.message;

import com.ljr.common.factory.presenter.BaseContract;
import com.ljr.factory.model.db.Group;
import com.ljr.factory.model.db.Message;
import com.ljr.factory.model.db.User;
import com.ljr.factory.model.db.view.MemberUserModel;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/22.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public interface ChatContract {
    interface Presenter extends BaseContract.Presenter{
        //发送文字
        void pushText(String content);
        // 发送语音
        void pushAudio(String path);

        // 发送图片
        void pushImages(String[] paths);

        // 重新发送一个消息，返回是否调度成功
        boolean rePush(Message message);
    }
    // 界面的基类
    interface View<InitModel> extends BaseContract.RecyclerView<Presenter, Message> {
        // 初始化的Model
        void onInit(InitModel model);
    }
    // 人聊天的界面
    interface UserView extends View<User> {

    }

    // 群聊天的界面
    interface GroupView extends View<Group> {
        // 显示管理员菜单
        void showAdminOption(boolean isAdmin);

        // 初始化成员信息
        void onInitGroupMembers(List<MemberUserModel> members, long moreCount);
    }
}
