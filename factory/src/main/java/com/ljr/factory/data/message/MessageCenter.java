package com.ljr.factory.data.message;

import com.ljr.factory.model.card.MessageCard;

/**
 * Created by 林佳荣 on 2018/5/11.
 * Github：https://github.com/ljrRookie
 * Function ：消息中心，进行消息卡片的消费
 */

public interface MessageCenter {
    void dispatch(MessageCard... cards);
}
