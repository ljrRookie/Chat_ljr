package com.ljr.chat_ljr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ljr.common.app.BaseActivity;

public class MainActivity extends BaseActivity {

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
}
