package com.ljr.chat_ljr.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ljr.chat_ljr.R;
import com.ljr.common.app.BaseApplication;
import com.ljr.common.app.PresenterToolbarActivity;
import com.ljr.common.recycler.RecyclerAdapter;
import com.ljr.common.widget.PortraitView;
import com.ljr.factory.presenter.group.GroupCreateContract;
import com.ljr.factory.presenter.group.GroupCreatePresenter;

import net.qiujuer.genius.ui.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by 林佳荣 on 2018/5/5.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class GroupCreateActivity extends PresenterToolbarActivity<GroupCreateContract.Presenter>
        implements GroupCreateContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.im_portrait)
    PortraitView mImPortrait;
    @BindView(R.id.edit_name)
    EditText mEditName;
    @BindView(R.id.edit_desc)
    EditText mEditDesc;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private Adapter mAdapter;

    public static void show(Context context) {
        context.startActivity(new Intent(context, GroupCreateActivity.class));
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_group_create;
    }

    @Override
    public void onCreateSucceed() {
        hideLoading();
        BaseApplication.showToast(R.string.label_group_create_succeed);
        finish();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new Adapter());
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create) {
            //  进行创建
            onCreateClick();
        }
        return super.onOptionsItemSelected(item);
    }
    // 进行创建操作
    private void onCreateClick() {
        hideSoftKeyboard();
        String name = mEditName.getText().toString().trim();
        String desc = mEditDesc.getText().toString().trim();
        mPresenter.create(name, desc, "");
    }
    @Override
    public RecyclerAdapter<GroupCreateContract.ViewModel> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        hideLoading();
    }

    @Override
    protected GroupCreateContract.Presenter initPresenter() {
        return new GroupCreatePresenter(this);
    }

    // 隐藏软件盘
    private void hideSoftKeyboard() {
        // 当前焦点的View
        View view = getCurrentFocus();
        if (view == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private class Adapter extends RecyclerAdapter<GroupCreateContract.ViewModel> {

        @Override
        protected int getItemViewType(int position, GroupCreateContract.ViewModel viewModel) {
            return R.layout.cell_group_create_contact;
        }

        @Override
        protected ViewHolder<GroupCreateContract.ViewModel> onCreateViewHolder(View root, int viewType) {
            return new GroupCreateActivity.ViewHolder(root);
        }
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<GroupCreateContract.ViewModel> {
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;
        @BindView(R.id.txt_name)
        TextView mName;
        @BindView(R.id.cb_select)
        CheckBox mSelect;


        ViewHolder(View itemView) {
            super(itemView);
        }

        @OnCheckedChanged(R.id.cb_select)
        void onCheckedChanged(boolean checked) {
            // 进行状态更改
            mPresenter.changeSelect(mData, checked);
        }

        @Override
        protected void onBind(GroupCreateContract.ViewModel viewModel) {
           // mPortrait.setup(Glide.with(GroupCreateActivity.this), viewModel.mAuthor);
            mName.setText(viewModel.mAuthor.getName());
            mSelect.setChecked(viewModel.isSelected);
        }
    }
}
