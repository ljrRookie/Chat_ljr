package com.ljr.common.recycler;

/**
 * Created by 林佳荣 on 2018/4/21.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public interface AdapterCallback<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder<Data> holder);
}
