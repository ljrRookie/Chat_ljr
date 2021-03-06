package com.ljr.chat_ljr.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.ljr.factory.Factory;
import com.ljr.factory.data.helper.AccountHelper;
import com.ljr.factory.persistence.Account;

/**
 * Created by 林佳荣 on 2018/4/24.
 * Github：https://github.com/ljrRookie
 * Function ：个推消息接收器
 */

public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        //判断当前消息的意图
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID: {
               // Log.i(TAG, "GET_CLIENTID:" + bundle.toString());
                Log.e(TAG, "当Id初始化的时候，获取设备Id" );
                // 当Id初始化的时候
                // 获取设备Id
                onClientInit(bundle.getString("clientid"));
                break;
            }
            case PushConsts.GET_MSG_DATA: {

                // 常规消息送达
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String message = new String(payload);
                  //  Log.e(TAG, "GET_MSG_DATA:" + message);
                    Log.e(TAG, "常规消息送达 :"+message );
                    onMessageArrived(message);
                }
                break;
            }
            default:
                Log.i(TAG, "OTHER:" + bundle.toString());
                break;
        }
    }
    /**
     * 消息达到时
     * @param message 新消息
     */
    private void onMessageArrived(String message) {
        // 交给Factory处理
        Factory.dispatchPush(message);
    }

    /**
     * 个推初始化Id
     * @param clientid  设备Id
     */
    private void onClientInit(String clientid) {
    //设置设备Id
        Account.setPushId(clientid);
        if(Account.isLogin()){
            //账户登录状态，进行一次PushId绑定
            //没有登录是不能绑定PushId的
            AccountHelper.bindPush(null);
        }

    }

}
