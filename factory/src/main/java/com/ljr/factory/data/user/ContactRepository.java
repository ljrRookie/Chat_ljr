package com.ljr.factory.data.user;

import com.ljr.common.factory.data.DataSource;
import com.ljr.factory.data.BaseDbRepository;
import com.ljr.factory.model.db.User;
import com.ljr.factory.model.db.User_Table;
import com.ljr.factory.persistence.Account;
import com.ljr.factory.presenter.contact.ContactContract;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/11.
 * Github：https://github.com/ljrRookie
 * Function ：联系人仓库
 */

public class ContactRepository extends BaseDbRepository<User> implements ContactDataSource{
    @Override
    public void load(SucceedCallback<List<User>> callback) {
        super.load(callback);
        // 加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(User user) {
        return user.isFollow() && !user.getId().equals(Account.getUserId());
    }
}
