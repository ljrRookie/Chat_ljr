package com.ljr.factory.data.helper;

import android.media.midi.MidiOutputPort;
import android.net.Network;
import android.text.TextUtils;
import android.util.Log;

import com.ljr.factory.Factory;
import com.ljr.factory.model.api.RspModel;
import com.ljr.factory.model.api.message.MsgCreateModel;
import com.ljr.factory.model.card.MessageCard;
import com.ljr.factory.model.db.Message;
import com.ljr.factory.model.db.Message_Table;
import com.ljr.factory.net.NetWork;
import com.ljr.factory.net.RemoteService;
import com.ljr.factory.net.UploadHelper;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 林佳荣 on 2018/5/11.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class MessageHelper {
    private static final String TAG = MessageHelper.class.getSimpleName();
    public static Message findFromLocal(String id) {
        return SQLite.select()
                .from(Message.class)
                .where(Message_Table.id.eq(id))
                .querySingle();
    }

    public static Message findLastWithUser(String userId) {
        return SQLite.select()
                .from(Message.class)
                .where(OperatorGroup.clause()
                        .and(Message_Table.sender_id.eq(userId))
                        .and(Message_Table.group_id.isNull()))
                .or(Message_Table.receiver_id.eq(userId))
                .orderBy(Message_Table.createAt, false) // 倒序查询
                .querySingle();
    }

    public static Message findLastWithGroup(String groupId) {
        return SQLite.select()
                .from(Message.class)
                .where(Message_Table.group_id.eq(groupId))
                .orderBy(Message_Table.createAt, false) // 倒序查询
                .querySingle();
    }

    //发送是异步进行的
    public static void push(final MsgCreateModel model) {
        Log.e(TAG, "MsgCreateModel:" + model.toString());
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                //成功状态：如果是一个已经发送过的消息，则不能重新发送
                //正在发送状态：如果是一个消息正在发送，则不能重新发送
                Message message = findFromLocal(model.getId());
                if (message != null && message.getStatus() != Message.STATUS_FAILED)
                    return;

                //我们在发送的时候需要通知界面更新状态，Card
                final MessageCard card = model.buildCard();
                Factory.getMessageCenter().dispatch(card);

                // 发送文件消息分两部：上传到云服务器，消息Push到我们自己的服务器
                // 如果是文件类型的（语音，图片，文件），需要先上传后才发送
                if (card.getType() != Message.TYPE_STR) {
                    // 不是文字类型
                    if (!card.getContent().startsWith(UploadHelper.ENDPOINT)) {
                        // 没有上传到云服务器的，还是本地手机文件
                        String content = null;

                        switch (card.getType()) {
                            case Message.TYPE_PIC:
                                //  content = uploadPicture(card.getContent());
                                break;
                            case Message.TYPE_AUDIO:
                                //  content = uploadAudio(card.getContent());
                                break;
                            default:
                                content = "";
                                break;
                        }

                        if (TextUtils.isEmpty(content)) {
                            // 失败
                            card.setStatus(Message.STATUS_FAILED);
                            Factory.getMessageCenter().dispatch(card);
                            return;
                        }


                        // 成功则把网络路径进行替换
                        card.setContent(content);
                        Factory.getMessageCenter().dispatch(card);
                        // 因为卡片的内容改变了，而我们上传到服务器是使用的model，
                        // 所以model也需要跟着更改
                        model.refreshByCard();
                    }
                }


                // 直接发送, 进行网络调度
                RemoteService service = NetWork.remote();
                service.msgPush(model).enqueue(new Callback<RspModel<MessageCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<MessageCard>> call, Response<RspModel<MessageCard>> response) {
                        RspModel<MessageCard> rspModel = response.body();
                        if (rspModel != null && rspModel.success()) {
                            MessageCard rspCard = rspModel.getResult();
                            if (rspCard != null) {
                                // 成功的调度
                                Factory.getMessageCenter().dispatch(rspCard);
                            }
                        } else {
                            // 检查是否是账户异常
                            Factory.decodeRspCode(rspModel, null);
                            // 走失败流程
                            onFailure(call, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<MessageCard>> call, Throwable t) {
                        // 通知失败
                        card.setStatus(Message.STATUS_FAILED);
                        Factory.getMessageCenter().dispatch(card);
                    }
                });
            }
        });
    }
}