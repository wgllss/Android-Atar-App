package com.atar.net;

import android.appconfig.AppConfigModel;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author:Atar
 * @createTime: 2018/9/5 下午1:59
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description :
 * **************************************************************************************************************************************************************************
 */
public class UrlParamCommon {
    // /**ip*/
    public static final String IP = AppConfigModel.getInstance().getString("WEEX_IP_KEY",
            "192.168.60.35:8080");
    // /**网络请求方式http or https*/
    public static final String HTTP = IP.contains("com.cn") ? "https" : "http";

    /**
     * 打包上线时用 注意 注释掉上面2行 放开下面2行 不用走这么多判断
     */
//    public static final String IP = "192.168.0.106:8080";
//    public static final String HTTP = "http";
    /**
     * weex 服务器地址
     */
    public static final String WEEX_HOST = HTTP + "://" + IP + "/";

    public static final String HTML_TEST_URL = WEEX_HOST + "assets/html/index.html";

    public static final String download_skin_url = WEEX_HOST +
            "assets/html/download_skin.apk";
}
