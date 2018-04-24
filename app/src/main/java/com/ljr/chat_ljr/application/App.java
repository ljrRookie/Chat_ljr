package com.ljr.chat_ljr.application;

import android.app.Application;

import com.igexin.sdk.PushManager;
import com.ljr.common.app.BaseApplication;
import com.ljr.factory.Factory;

/**
 * Created by 林佳荣 on 2018/4/24.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        // 调用Factory进行初始化
        Factory.setup();
        // 推送进行初始化
        PushManager.getInstance().initialize(this,null);
    }
}
