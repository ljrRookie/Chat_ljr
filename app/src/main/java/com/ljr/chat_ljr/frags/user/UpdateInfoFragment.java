package com.ljr.chat_ljr.frags.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ljr.chat_ljr.MainActivity;
import com.ljr.chat_ljr.R;
import com.ljr.chat_ljr.activities.UserActivity;
import com.ljr.chat_ljr.frags.media.GalleryFragment;
import com.ljr.common.app.BaseApplication;
import com.ljr.common.app.PresenterFragment;
import com.ljr.common.widget.PortraitView;
import com.ljr.factory.presenter.user.UpdateInfoContract;
import com.ljr.factory.presenter.user.UpdateInfoPresenter;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.EditText;
import net.qiujuer.genius.ui.widget.Loading;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 林佳荣 on 2018/5/4.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class UpdateInfoFragment extends PresenterFragment<UpdateInfoContract.Presenter>
        implements UpdateInfoContract.View {
    @BindView(R.id.im_portrait)
    PortraitView mImPortrait;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.im_sex)
    ImageView mImSex;
    @BindView(R.id.edit_desc)
    EditText mEditDesc;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.loading)
    Loading mLoading;
    Unbinder unbinder;
    // 头像的本地路径
    private String mPortraitPath;
    private boolean isMan = true;
    private UserActivity mActivity;

    @Override
    protected UpdateInfoContract.Presenter initPresenter() {
        return new UpdateInfoPresenter(this);
    }

    public UpdateInfoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @Override
    protected void initData() {
        super.initData();
        mActivity = (UserActivity) getActivity();
    }

    @Override
    public void updateSucceed() {
        // 更新成功跳转到主界面
        Log.e("调试","更新成功跳转到主界面");
            MainActivity.show(getContext());
           getActivity().finish();


    }

    @Override
    public void showError(int str) {
        super.showError(str);
        // 当需要显示错误的时候触发，一定是结束了
        // 停止Loading
        mLoading.stop();
        // 让控件可以输入
        mEditDesc.setEnabled(true);
        mImPortrait.setEnabled(true);
        mImSex.setEnabled(true);
        // 提交按钮可以继续点击
        mBtnSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();

        // 正在进行时，正在进行注册，界面不可操作
        // 开始Loading
        mLoading.start();
        // 让控件不可以输入
        mEditDesc.setEnabled(false);
        mImPortrait.setEnabled(false);
        mImSex.setEnabled(false);
        // 提交按钮不可以继续点击
        mBtnSubmit.setEnabled(false);
    }

    @OnClick({R.id.im_portrait, R.id.im_sex, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_portrait:
                new GalleryFragment().setListener(new GalleryFragment.OnSelectedListener() {
                    @Override
                    public void onSelectedImage(String path) {
                        UCrop.Options options = new UCrop.Options();
                        // 设置图片处理的格式JPEG
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        // 设置压缩后的图片精度
                        options.setCompressionQuality(96);
                        // 得到头像的缓存地址
                        File dPath = BaseApplication.getPortraitTmpFile();
                        // 发起剪切
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                                .withAspectRatio(1, 1) // 1比1比例
                                .withMaxResultSize(520, 520) // 返回最大的尺寸
                                .withOptions(options) // 相关参数
                                .start(getActivity());
                    }
                }) // show 的时候建议使用getChildFragmentManager，
                        // tag GalleryFragment class 名
                        .show(getChildFragmentManager(), GalleryFragment.class.getName());
                break;
            case R.id.im_sex:
                // 性别图片点击的时候触发
                isMan = !isMan; // 反向性别

                Drawable drawable = getResources().getDrawable(isMan ?
                        R.drawable.ic_sex_man : R.drawable.ic_sex_woman);
                mImSex.setImageDrawable(drawable);
                // 设置背景的层级，切换颜色
                mImSex.getBackground().setLevel(isMan ? 0 : 1);
                break;
            case R.id.btn_submit:
                String desc = mEditDesc.getText().toString();
                // 调用P层进行注册
                mPresenter.update(mPortraitPath, desc, isMan);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            BaseApplication.showToast(R.string.data_rsp_error_unknown);
        }
    }
    /**
     * 加载Uri到当前的头像中
     * @param uri Uri
     */
    private void loadPortrait(Uri uri) {
        // 得到头像地址
        mPortraitPath = uri.getPath();
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mImPortrait);
    }
}
