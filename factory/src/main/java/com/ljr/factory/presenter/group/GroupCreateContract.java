package com.ljr.factory.presenter.group;

import com.ljr.common.factory.presenter.BaseContract;
import com.ljr.common.factory.model.Author;

/**
 * Created by 林佳荣 on 2018/5/5.
 * Github：https://github.com/ljrRookie
 * Function ：群组创建契约
 */

public interface GroupCreateContract {
    interface Presenter extends BaseContract.Presenter{
        //创建
        void create(String name ,String desc,String picture);
        //更改一个Model的选中状态
        void changeSelect(ViewModel model,boolean isSelected);
    }
    interface View extends BaseContract.RecyclerView<Presenter,ViewModel>{
        //创建成功
        void onCreateSucceed();
    }
    class ViewModel{
        //用户信息
        public Author mAuthor;
        //是否选中
        public boolean isSelected;
    }
}
