package com.ljr.common.factory.data;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/8.
 * Github：https://github.com/ljrRookie
 * Function ：基础的数据库数据源接口定义
 */

public interface DbDataSource<Data> extends DataSource{
    /**
     * 有一个基本的数据源加载方法
     * @param callback 传递一个callback回调，一般回调到Presenter
     */
    void load(SucceedCallback<List<Data>> callback);
}
