package com.ljr.factory.presenter;

import com.ljr.common.factory.data.DataSource;
import com.ljr.common.factory.data.DbDataSource;
import com.ljr.common.factory.presenter.BaseContract;
import com.ljr.common.factory.presenter.BaseRecyclerPresenter;

import java.util.List;

/**
 * Created by 林佳荣 on 2018/5/8.
 * Github：https://github.com/ljrRookie
 * Function ：基础的仓库源
 */

public abstract class BaseSourcePresenter<Data,ViewModel,Source extends DbDataSource<Data>,
        View extends BaseContract.RecyclerView> extends BaseRecyclerPresenter<ViewModel,View>
        implements DataSource.SucceedCallback<List<Data>>{
    protected Source mSource;


    public BaseSourcePresenter(Source source,View view) {
        super(view);
        this.mSource = source;
    }

    @Override
    public void start() {
        super.start();
        if(mSource != null){
            mSource.load(this);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        mSource.dispose();
        mSource = null;
    }
}
