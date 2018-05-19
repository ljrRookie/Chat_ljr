package com.ljr.factory.presenter.user;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;

import com.ljr.common.factory.data.DataSource;
import com.ljr.common.factory.presenter.BasePresenter;
import com.ljr.factory.Factory;
import com.ljr.factory.R;
import com.ljr.factory.data.helper.UserHelper;
import com.ljr.factory.model.api.user.UserUpdateModel;
import com.ljr.factory.model.card.UserCard;
import com.ljr.factory.model.db.User;
import com.ljr.factory.net.UploadHelper;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * Created by 林佳荣 on 2018/5/4.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View>
        implements UpdateInfoContract.Presenter ,DataSource.Callback<UserCard>{
    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void update(final String photoFilePath, final String desc, final boolean isMan) {
        start();
        final UpdateInfoContract.View view = getView();
        if (TextUtils.isEmpty(photoFilePath) || TextUtils.isEmpty(desc)) {
            view.showError(R.string.data_account_update_invalid_parameter);
        } else {
            // 上传头像
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    String url = UploadHelper.uploadPortrait(photoFilePath);
                    if (TextUtils.isEmpty(url)) {
                        // 上传失败
                        view.showError(R.string.data_upload_error);
                    } else {
                        // 构建Model
                        UserUpdateModel model = new UserUpdateModel("", url, desc,
                                isMan ? User.SEX_MAN : User.SEX_WOMAN);
                        // 进行网络请求，上传
                        UserHelper.update(model, UpdateInfoPresenter.this);
                    }
                }
            });
        }
        }

    @Override
    public void onDataLoaded(UserCard userCard) {
        Log.e("调试","onDataLoaded");
        final UpdateInfoContract.View view = getView();
        if (view == null) {
            Log.e("调试", "view == null");
            return;
        }
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Log.e("调试","view.updateSucceed();");
                view.updateSucceed();
            }
        });
    }

    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {
        final UpdateInfoContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Log.e("调试","view.showError();");
                view.showError(strRes);
            }
        });
    }
    }

