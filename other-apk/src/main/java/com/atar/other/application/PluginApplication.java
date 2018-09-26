package com.atar.other.application;

import android.app.Application;
import android.application.CommonApplication;
import android.application.CrashHandler;
import android.interfaces.CommonNetWorkExceptionToast;
import android.reflection.ThreadPoolTool;
import android.skin.SkinResourcesManager;
import android.utils.ShowLog;

import com.atar.net.UrlParamCommon;
import com.atar.other.R;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author:Atar
 * @createTime: 2018/9/6 下午3:47
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description :
 * **************************************************************************************************************************************************************************
 */
public class PluginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadPoolTool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                CommonApplication.initApplication(PluginApplication.this);// 初始化全局Context
                SkinResourcesManager.getInstance(PluginApplication.this).initSkinResources(true,
                        "com.atar.skin", UrlParamCommon.download_skin_url);
                CommonNetWorkExceptionToast.initToastError(PluginApplication.this, R.array.err_toast_string);// 初始化全局网络错误提示信息
                ShowLog.setDebug(true);// 设置不显示日志 上线前记得改成false
                CommonNetWorkExceptionToast.setIsShowErrorToast(false);// 上线前记得设置不显示错误网络具体提示 测试时可开启
                CommonApplication.initImageLoader(getApplicationContext());// 初始化加载图片配置
                // CommonToast.initToastResouseId(R.drawable.corners_toast, R.color.black);//
                // 初始化toast字体颜色和背景
                CrashHandler.getInstance().init(PluginApplication.this);// 接收错误异常
            }
        });
    }
}
