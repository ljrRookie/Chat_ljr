package com.ljr.factory.net;

import android.text.format.DateFormat;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.ljr.common.tools.HashUtil;
import com.ljr.factory.Factory;

import java.io.File;
import java.util.Date;

/**
 * Created by 林佳荣 on 2018/5/4.
 * Github：https://github.com/ljrRookie
 * Function ：上传工具类，用于上传任意文件到阿里oss存储
 */

public class UploadHelper {
    private static final String TAG = UploadHelper.class.getSimpleName();
    // 与你们的存储区域有关系
    public static final String ENDPOINT = "https://oss-cn-shenzhen.aliyuncs.com";
    // 上传的仓库名
    private static final String BUCKET_NAME = "chat-ljr";
    /**
     * 上传头像
     * @param photoFilePath 本地地址
     * @return 服务器地址
     */
    public static String uploadPortrait(String photoFilePath) {
        String key = getPortraitObjKey(photoFilePath);
        return upload(key, photoFilePath);
    }
    /**
     * 上传的最终方法，成功返回则一个路径
     * @param key 上传上去后，在服务器上的独立的KEY
     * @param photoFilePath   需要上传的文件的路径
     * @return 存储的地址
     */
    private static String upload(String key, String photoFilePath) {
        //构造一个上传请求
        // 构造一个上传请求
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME,
                key, photoFilePath);
        try {
            // 初始化上传的Client
            OSS client = getClient();
            // 开始同步上传
            try {
                PutObjectResult result = client.putObject(request);
            } catch (ClientException e) {
                e.printStackTrace();
                Log.e(TAG, "ClientException");
                Log.e("网络异常", "网络异常");
                return null;
            }

            // 得到一个外网可访问的地址
            String url = client.presignPublicObjectURL(BUCKET_NAME, key);
            // 格式打印输出
            Log.d(TAG, String.format("PublicObjectURL:%s", url));
            return url;
        } catch (ServiceException e) {
            e.printStackTrace();
            // 如果有异常则返回空
            Log.e(TAG, "ServiceException");
            Log.e("RequestId", e.getRequestId());
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
            return null;
        }
    }

    /**
     * 分月存储，避免一个文件夹太多
     * @return yyyyMM
     */
    private static String getDateString() {
        return DateFormat.format("yyyyMM", new Date()).toString();
    }
    // portrait/201703/dawewqfas243rfawr234.jpg
    private static String getPortraitObjKey(String photoFilePath) {
        String fileMd5 = HashUtil.getMD5String(new File(photoFilePath));
        String dateString = getDateString();
        return String.format("portrait/%s/%s.jpg", dateString, fileMd5);
    }

    public static OSS getClient() {
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(
                "LTAIwPhrP4BtQjEy", "YooH7atgu8yVt5YU89scNQXx1A4HJQ");
        return new OSSClient(Factory.app(), ENDPOINT, credentialProvider);
    }
}
