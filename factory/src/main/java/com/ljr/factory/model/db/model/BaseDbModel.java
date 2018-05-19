package com.ljr.factory.model.db.model;

import com.ljr.factory.utils.DiffUiDataCallback;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by 林佳荣 on 2018/4/23.
 * Github：https://github.com/ljrRookie
 * Function ：基类BaseDbModel，数据库框架DbFlow中的基类，同时定义类我们需要的方法
 */

public abstract class BaseDbModel<Model> extends BaseModel implements DiffUiDataCallback.UiDataDiffer<Model>{
}
