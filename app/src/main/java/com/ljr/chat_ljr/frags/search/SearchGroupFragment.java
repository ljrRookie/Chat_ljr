package com.ljr.chat_ljr.frags.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ljr.chat_ljr.R;
import com.ljr.chat_ljr.activities.PersonalActivity;
import com.ljr.chat_ljr.activities.SearchActitivy;
import com.ljr.common.app.PresenterFragment;
import com.ljr.common.recycler.RecyclerAdapter;
import com.ljr.common.widget.EmptyView;
import com.ljr.common.widget.PortraitView;
import com.ljr.factory.model.card.GroupCard;
import com.ljr.factory.presenter.search.SearchContract;
import com.ljr.factory.presenter.search.SearchGroupPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 林佳荣 on 2018/5/7.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class SearchGroupFragment extends PresenterFragment<SearchContract.Presenter> implements SearchContract.GroupView, SearchActitivy.SearchFragment {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.empty)
    EmptyView mEmpty;
    private RecyclerAdapter<GroupCard> mAdapter;
    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchGroupPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        // 初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<GroupCard>() {
            @Override
            protected int getItemViewType(int position, GroupCard userCard) {
                // 返回cell的布局id
                return R.layout.cell_search_group_list;
            }

            @Override
            protected ViewHolder<GroupCard> onCreateViewHolder(View root, int viewType) {
                return new SearchGroupFragment.ViewHolder(root);
            }
        });
        // 初始化占位布局
        mEmpty.bind(mRecycler);
        setPlaceHolderView(mEmpty);
    }

    @Override
    protected void initData() {
        super.initData();
        // 发起首次搜索
        search("");
    }
    @Override
    public void onSearchDone(List<GroupCard> groupCards) {
        // 数据成功的情况下返回数据
        mAdapter.replace(groupCards);
        // 如果有数据，则是OK，没有数据就显示空布局
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    @Override
    public void search(String content) {
        mPresenter.search(content);
    }


     class ViewHolder extends RecyclerAdapter.ViewHolder<GroupCard> {
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.im_join)
        ImageView mJoin;
        public ViewHolder(View root) {
            super(root);
        }

        @Override
        protected void onBind(GroupCard groupCard) {
            mPortraitView.setup(Glide.with(SearchGroupFragment.this), groupCard.getPicture());
            mName.setText(groupCard.getName());
            // 加入时间判断是否加入群
            mJoin.setEnabled(groupCard.getJoinAt() == null);
        }
        @OnClick(R.id.im_join)
        void onJoinClick() {
            // 进入创建者的个人界面
            PersonalActivity.show(getContext(), mData.getOwnerId());
        }
    }
}
