package com.ljr.factory.data.helper;

import com.ljr.factory.model.db.Session;
import com.ljr.factory.model.db.Session_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by 林佳荣 on 2018/5/11.
 * Github：https://github.com/ljrRookie
 * Function ：会话辅助工具类
 */

public class SessionHelper {
    // 从本地查询Session
    public static Session findFromLocal(String id) {
        return SQLite.select()
                .from(Session.class)
                .where(Session_Table.id.eq(id))
                .querySingle();
    }
}
