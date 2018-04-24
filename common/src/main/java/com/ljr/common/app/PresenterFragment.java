package com.ljr.common.app;

import android.content.Context;
import android.support.annotation.StringRes;

import com.ljr.common.R;
import com.ljr.common.factory.BaseContract;

/**
 * Created by 林佳荣 on 2018/4/23.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends BaseFragment
        implements BaseContract.View<Presenter> {
    protected Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 在界面onAttach之后就触发初始化Presenter
        initPresenter();
    }

    /**
     * 初始化Presenter
     */
    protected abstract Presenter initPresenter();


    @Override
    public void showError(@StringRes int str) {
        //显示错误，优先使用占位布局
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerError(str);
        } else {
            BaseApplication.showToast(str);
        }
    }

    @Override
    public void showLoading() {
        if (mPlaceHolderView != null) {
            mPlaceHolderView.triggerLoading();
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        //View中赋值Presenter
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.destroy();
        }
    }
}
