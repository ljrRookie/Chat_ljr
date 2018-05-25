package com.ljr.factory.data.message;

import com.ljr.common.factory.data.DbDataSource;
import com.ljr.factory.model.db.Message;

/**
 * Created by 林佳荣 on 2018/5/23.
 * Github：https://github.com/ljrRookie
 * Function ：消息的数据源定义，他的实现是：MessageRepository, MessageGroupRepository
 *              关注的对象是Message表
 */

public interface MessageDataSource extends DbDataSource<Message> {
}
