package com.ljr.factory.net;

import android.text.TextUtils;

import com.ljr.common.Common;
import com.ljr.factory.Factory;
import com.ljr.factory.persistence.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 林佳荣 on 2018/4/24.
 * Github：https://github.com/ljrRookie
 * Function ：网络请求
 */

public class NetWork {
    private static NetWork instance;
    private Retrofit retrofit;

    static {
        instance = new NetWork();
    }

    private NetWork() {

    }

    /**
     * 创建一个Retrofit
     *
     * @return
     */
    public static Retrofit getRetrofit() {
        if (instance.retrofit != null)
            return instance.retrofit;
        //得到一个Ok Client
        OkHttpClient client = new OkHttpClient.Builder()
                //给所有请求添加一个拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // 拿到我们的请求
                        Request original = chain.request();
                        // 重新进行build
                        Request.Builder builder = original.newBuilder();
                        if (!TextUtils.isEmpty(Account.getToken())) {
                            // 注入一个token
                            builder.addHeader("token", Account.getToken());
                        }
                        builder.addHeader("Content-Type", "application/json");
                        Request newRequest = builder.build();
                        // 返回
                        return chain.proceed(newRequest);
                    }
                }).build();
        Retrofit.Builder builder = new Retrofit.Builder();
        // 设置电脑链接
        instance.retrofit = builder.baseUrl(Common.Constance.API_URL)
                // 设置client
                .client(client)
                // 设置Json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
        return instance.retrofit;
    }
        /**
         * 返回一个请求代理
         */
        public static RemoteService remote(){
            return NetWork.getRetrofit().create(RemoteService.class);

    }
}
