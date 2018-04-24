package com.ljr.factory.presenter.model.db.model;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by 林佳荣 on 2018/4/23.
 * Github：https://github.com/ljrRookie
 * Function ：数据库基本信息
 */
@Database(name = AppDatabase.NAME, version =  AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "AppDatabase";
    public static final int VERSION = 1;
}
