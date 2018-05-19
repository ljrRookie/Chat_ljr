package com.ljr.chat_ljr.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ljr.chat_ljr.R;
import com.ljr.common.app.BaseApplication;
import com.ljr.common.app.PresenterToolbarActivity;
import com.ljr.common.widget.PortraitView;
import com.ljr.factory.model.db.User;
import com.ljr.factory.presenter.contact.PersonalContract;
import com.ljr.factory.presenter.contact.PersonalPresenter;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 林佳荣 on 2018/5/4.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class PersonalActivity extends PresenterToolbarActivity<PersonalContract.Presenter> implements PersonalContract.View {
    private static final String BOUND_KEY_ID = "BOUND_KEY_ID";
    @BindView(R.id.im_header)
    ImageView mImHeader;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.im_portrait)
    PortraitView mImPortrait;
    @BindView(R.id.txt_follows)
    TextView mTxtFollows;
    @BindView(R.id.txt_following)
    TextView mTxtFollowing;
    @BindView(R.id.linearLayout2)
    LinearLayout mLinearLayout2;
    @BindView(R.id.txt_desc)
    TextView mTxtDesc;
    @BindView(R.id.btn_say_hello)
    Button mBtnSayHello;
    @BindView(R.id.guideline1)
    Guideline mGuideline1;
    private String userId;
    // 关注
    private MenuItem mFollowItem;
    private boolean mIsFollowUser = false;
    public static void show(Context context, String userId) {
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra(BOUND_KEY_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        userId = bundle.getString(BOUND_KEY_ID);
        return !TextUtils.isEmpty(userId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.personal, menu);
        mFollowItem = menu.findItem(R.id.action_follow);
        changeFollowItemStatus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_follow) {
            // TODO 进行关注操作
            BaseApplication.showToast("点击");
            Drawable drawable = getResources()
                    .getDrawable(R.drawable.ic_favorite) ;
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, Resource.Color.WHITE);
            mFollowItem.setIcon(drawable);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 更改关注菜单状态
     */
    private void changeFollowItemStatus() {
        if (mFollowItem == null)
            return;
        // 根据状态设置颜色
        Drawable drawable = mIsFollowUser ? getResources()
                .getDrawable(R.drawable.ic_favorite) :
                getResources().getDrawable(R.drawable.ic_favorite_border);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Resource.Color.WHITE);
        mFollowItem.setIcon(drawable);
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void onLoadDone(User user) {
        mImPortrait.setup(Glide.with(this), user);
        mTxtName.setText(user.getName());
        mTxtDesc.setText(user.getDesc());
        mTxtFollows.setText(String.format(getString(R.string.label_follows), user.getFollows()));
        mTxtFollowing.setText(String.format(getString(R.string.label_following), user.getFollowing()));
        hideLoading();
    }

    @Override
    public void allowSayHello(boolean isAllow) {
        mBtnSayHello.setVisibility(isAllow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setFollowStatus(boolean isFollow) {
        mIsFollowUser = isFollow;
        changeFollowItemStatus();
    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return new PersonalPresenter(this);
    }



    @OnClick(R.id.btn_say_hello)
    public void onViewClicked() {

    }
}
