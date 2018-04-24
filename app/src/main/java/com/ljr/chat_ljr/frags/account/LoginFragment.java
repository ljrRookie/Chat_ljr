package com.ljr.chat_ljr.frags.account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ljr.chat_ljr.MainActivity;
import com.ljr.chat_ljr.R;
import com.ljr.common.app.PresenterFragment;
import com.ljr.factory.presenter.account.LoginContract;
import com.ljr.factory.presenter.account.LoginPresenter;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 林佳荣 on 2018/4/23.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class LoginFragment extends PresenterFragment<LoginContract.Presenter> implements LoginContract.View {
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.txt_go_register)
    TextView mTxtGoRegister;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.loading)
    Loading mLoading;

    private AccountTrigger mAccountTrigger;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 拿到我们的Activity的引用
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void loginSuccess() {
         MainActivity.show(getContext());
        getActivity().finish();
    }

    @Override
    public void showError(@StringRes int str) {
        super.showError(str);
        // 当需要显示错误的时候触发，一定是结束了

        // 停止Loading
        mLoading.stop();
        // 让控件可以输入
        mEditPhone.setEnabled(true);
        mEditPassword.setEnabled(true);
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
        mEditPhone.setEnabled(false);
        mEditPassword.setEnabled(false);
        // 提交按钮不可以继续点击
        mBtnSubmit.setEnabled(false);
    }

    @OnClick({R.id.txt_go_register, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_go_register:
                mAccountTrigger.triggerView();
                break;
            case R.id.btn_submit:

                String phone = mEditPhone.getText().toString();
                String password = mEditPassword.getText().toString();
                // 调用P层进行注册
                mPresenter.login(phone, password);
                break;
        }
    }
}
