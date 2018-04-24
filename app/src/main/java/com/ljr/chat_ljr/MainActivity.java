package com.ljr.chat_ljr;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ljr.common.app.BaseActivity;
import com.ljr.common.widget.PortraitView;
import com.ljr.factory.persistence.Account;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.appbar)
    View mLayAppbar;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @BindView(R.id.btn_action)
    FloatActionButton mAction;

  //  private NavHelper<Integer> mNavHelper;
    /**
     * MainActivity 显示的入口
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected boolean initArgs(Bundle bundle) {
        if (Account.isComplete()) {
            // 判断用户信息是否完全，完全则走正常流程
            return super.initArgs(bundle);
        } else {
           // UserActivity.show(this);
            return false;
        }
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //初始化底部辅助工具类

    }

    @Override
    protected void initData() {
        super.initData();
    }
}
