/**
 *
 */
package com.atar.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.application.CommonApplication;
import android.application.CrashHandler;
import android.content.Context;
import android.interfaces.CommonNetWorkExceptionToast;
import android.os.Build;
import android.reflection.ThreadPoolTool;
import android.skin.SkinResourcesManager;
import android.support.multidex.MultiDex;
import android.utils.ShowLog;

import com.atar.app.R;
import com.atar.net.UrlParamCommon;

/**
 * ø
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2017-8-9下午2:54:23
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class AtarApplication extends Application {
    public static AtarApplication mInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onCreate() {
        super.onCreate();
        ThreadPoolTool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                mInstance = AtarApplication.this;
                CommonApplication.initApplication(AtarApplication.this);// 初始化全局Context
                SkinResourcesManager.getInstance(AtarApplication.this).initSkinResources(true,
                        "com.atar.skin", UrlParamCommon.download_skin_url);
                CommonNetWorkExceptionToast.initToastError(AtarApplication.this, R.array
                        .err_toast_string);// 初始化全局网络错误提示信息
                ShowLog.setDebug(true);// 设置不显示日志 上线前记得改成false
                CommonNetWorkExceptionToast.setIsShowErrorToast(false);// 上线前记得设置不显示错误网络具体提示 测试时可开启
                CommonApplication.initImageLoader(getApplicationContext());// 初始化加载图片配置
                // CommonToast.initToastResouseId(R.drawable.corners_toast, R.color.black);//
                // 初始化toast字体颜色和背景
                CrashHandler.getInstance().init(AtarApplication.this);// 接收错误异常
            }
        });
    }

    public static AtarApplication getApplication() {
        return mInstance;
    }
}
