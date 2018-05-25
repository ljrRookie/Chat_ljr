package com.ljr.factory.presenter.message;

import android.support.v7.util.DiffUtil;

import com.ljr.factory.data.helper.MessageHelper;
import com.ljr.factory.data.message.MessageDataSource;
import com.ljr.factory.data.message.MessageRepository;
import com.ljr.factory.model.api.message.MsgCreateModel;
import com.ljr.factory.model.db.Message;
import com.ljr.factory.presenter.BaseSourcePresenter;
import com.ljr.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/23.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class ChatPresenter<View extends ChatContract.View>
        extends BaseSourcePresenter<Message, Message, MessageDataSource, View>
        implements ChatContract.Presenter {
    // 接收者Id，可能是群，或者人的ID
    protected String mReceiverId;
    // 区分是人还是群Id
    protected int mReceiverType;

    public ChatPresenter(MessageDataSource source, View view, String receiverId, int receiverType) {
        super(source, view);
        this.mReceiverId = receiverId;
        this.mReceiverType = receiverType;
    }

    @Override
    public void onDataLoaded(List<Message> messages) {
        ChatContract.View view = getView();
        if (view == null)
            return;

        // 拿到老数据
        @SuppressWarnings("unchecked")
        List<Message> old = view.getRecyclerAdapter().getItems();

        // 差异计算
        DiffUiDataCallback<Message> callback = new DiffUiDataCallback<>(old, messages);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 进行界面刷新
        refreshData(result, messages);
    }

    @Override
    public void pushText(String content) {
//构建一个新的消息
        MsgCreateModel model = new MsgCreateModel.Builder().receiver(mReceiverId, mReceiverType)
                .content(content, Message.TYPE_STR)
                .build();
        //进行网络发送
        MessageHelper.push(model);
    }

    @Override
    public void pushAudio(String path) {

    }

    @Override
    public void pushImages(String[] paths) {

    }

    @Override
    public boolean rePush(Message message) {
        return false;
    }
}
