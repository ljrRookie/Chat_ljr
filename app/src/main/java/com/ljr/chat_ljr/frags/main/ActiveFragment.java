package com.ljr.chat_ljr.frags.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljr.chat_ljr.R;
import com.ljr.common.app.BaseFragment;
import com.ljr.common.widget.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 林佳荣 on 2018/5/4.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class ActiveFragment extends BaseFragment {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.empty)
    EmptyView mEmpty;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        // 初始化占位布局
        mEmpty.bind(mRecycler);
        setPlaceHolderView(mEmpty);
        mPlaceHolderView.triggerOkOrEmpty(false);
    }
}
