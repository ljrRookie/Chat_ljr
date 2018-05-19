package com.ljr.factory.data.group;


import com.ljr.factory.model.card.GroupCard;
import com.ljr.factory.model.card.GroupMemberCard;

/**
 * 群中心的接口定义
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public interface GroupCenter {
    // 群卡片的处理
    void dispatch(GroupCard... cards);

    // 群成员的处理
    void dispatch(GroupMemberCard... cards);
}
