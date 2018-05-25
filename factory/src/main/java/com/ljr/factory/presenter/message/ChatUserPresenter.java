package com.ljr.factory.presenter.message;

import com.ljr.factory.data.helper.UserHelper;
import com.ljr.factory.data.message.MessageDataSource;
import com.ljr.factory.data.message.MessageRepository;
import com.ljr.factory.model.db.Message;
import com.ljr.factory.model.db.User;

/**
 * Created by 林佳荣 on 2018/5/23.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView>implements ChatContract.Presenter {

    public ChatUserPresenter( ChatContract.UserView view, String receiverId) {
        super(new MessageRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_NONE);
    }
    @Override
    public void start() {
        super.start();

        // 从本地拿这个人的信息
        User receiver = UserHelper.findFromLocal(mReceiverId);
        getView().onInit(receiver);
    }
}
