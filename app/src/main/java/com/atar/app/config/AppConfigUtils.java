/**
 *
 */
package com.atar.app.config;

import android.activity.ActivityManager;
import android.app.Activity;
import android.appconfig.AppConfigDownloadManager;
import android.appconfig.AppConfigDownloadManager.HttpCallBackResult;
import android.appconfig.AppConfigModel;
import android.application.CrashHandler;
import android.common.CommonHandler;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.interfaces.HandlerListener;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.reflection.ThreadPoolTool;
import android.utils.ApplicationManagement;
import android.utils.ShowLog;
import android.widget.CommonToast;

import com.atar.app.activitys.load.AtarLoadActivity;
import com.atar.net.UrlParamCommon;
import com.atar.utils.IntentUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2017-5-24上午11:13:53
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class AppConfigUtils {
    private static String TAG = AppConfigUtils.class.getSimpleName();

    /**
     * 下载配置andriodAppConfig地址key
     */
    public static final String andriod_app_config_home_url = UrlParamCommon.WEEX_HOST +
            "andriodAppConfig_atar.txt";
//    public static final String andriod_app_config_home_url =
// "http://192.168.0.104:8080/andriodAppConfig.txt";
    // public static final String andriod_app_config_home_url =
    // "http://192.168.1.10:8080/andriodAppConfig.txt";
    /**
     * 保存配置文件json key
     */
    public static final String ANDRIOD_APP_CONFIG_KEY = "ANDRIOD_APP_CONFIG_HOME_KEY";
    /**
     * 保存开机引道json key
     */
    public static final String APP_LOADING_IMAGES_KEY = "APP_LOADING_IMAGES_KEY";
    /**
     * 保存webview 已有离线文件 key
     */
    public static final String OFFINE_FILE_PATH_KEY = "OFFINE_FILE_PATH_KEY";
    /**
     * 保存andriodAppConfig.txt文件版本 key
     */
    public static final String APP_CONFIG_TEXT_VERSION_KEY = "APP_CONFIG_TEXT_VERSION_KEY";

    /**
     * 获取离线assets下webview 所用文件
     *
     * @author :Atar
     * @createTime:2017-6-6上午10:13:20
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void getOffineFilePath(final Activity activity) {
        ThreadPoolTool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                AssetManager am = activity.getAssets();
                try {

                    String[] resJs = am.list("js");
                    String[] resImg = am.list("img");
                    String[] resCss = am.list("css");
                    String strOfflineResources = "";
                    if (resJs != null && resJs.length > 0) {
                        for (int i = 0; i < resJs.length; i++) {
                            strOfflineResources += resJs[i];
                        }
                    }
                    if (resImg != null && resImg.length > 0) {
                        for (int i = 0; i < resImg.length; i++) {
                            strOfflineResources += resImg[i];
                        }
                    }
                    if (resCss != null && resCss.length > 0) {
                        for (int i = 0; i < resCss.length; i++) {
                            strOfflineResources += resCss[i];
                        }
                    }
                    AppConfigModel.getInstance().putString(OFFINE_FILE_PATH_KEY,
                            strOfflineResources);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 读取服务端文件 如txt中的json
     *
     * @param FileUrl                    :文件地址
     * @param saveToSharedPreferencesKey :需要保存到SharedPreferences中的Key
     * @author :Atar
     * @createTime:2017-5-24上午11:15:57
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void getServerTextJson(final String FileUrl, final String
            saveToSharedPreferencesKey, final HandlerListener handlerListener, final int
                                                 resultMsgWhat) {
        AppConfigDownloadManager.getInstance().getServerJson(FileUrl, new HttpCallBackResult() {
            @Override
            public void onResult(String result) {
                if (result != null && saveToSharedPreferencesKey != null && result.length() > 0) {
                    Gson gson = new Gson();
                    AppConfigJson mAppConfigJson = gson.fromJson(result, AppConfigJson.class);
                    if (mAppConfigJson == null) {
                        return;
                    }
                    String versionName = "";
                    String localVersion = "";
                    if (AppConfigModel.getInstance().getString(AppConfigModel.VERSION_KEY, "") !=
                            null && AppConfigModel.getInstance().getString(AppConfigModel
                            .VERSION_KEY, "").length() > 0) {
                        localVersion = AppConfigModel.getInstance().getString(AppConfigModel
                                .VERSION_KEY, "");
                    } else {
                        try {
                            localVersion = ApplicationManagement.getVersionName();
                        } catch (Exception e) {
                            ShowLog.e(TAG, CrashHandler.crashToString(e));
                        }
                    }
                    boolean isReplace = true;
                    // versionName 版本号 和apk 的versionName一样的值
                    // isReplace 如果apk新发版本 这个配置也新发配置.txt文件，为true: 老版本要替换该配置.txt, false :老版本不替换该.txt
                    versionName = mAppConfigJson.getVersionName();
                    AppConfigModel.getInstance().putString(APP_CONFIG_TEXT_VERSION_KEY,
                            versionName, true);

                    isReplace = mAppConfigJson.isReplace();
                    if (versionName.compareToIgnoreCase(localVersion) > 0) {
                        if (isReplace) {
                            AppConfigModel.getInstance().putString(saveToSharedPreferencesKey,
                                    result, true);
                            AppConfigModel.getInstance().putString(AtarLoadActivity
                                            .LOADIMAGE_VERSION_KEY, mAppConfigJson
                                            .getLoadImage_Version()
                                    , true);
                        }
                    } else {
                        AppConfigModel.getInstance().putString(saveToSharedPreferencesKey,
                                result, true);
                    }
                    if (mAppConfigJson.getLoading_images() != null && mAppConfigJson
                            .getLoading_images().size() > 0 && handlerListener != null) {
                        AppConfigModel.getInstance().putString(APP_LOADING_IMAGES_KEY, gson
                                .toJson(mAppConfigJson.getLoading_images()), true);
                        CommonHandler.getInstatnce().handerMessage(handlerListener,
                                resultMsgWhat, 0, 0, mAppConfigJson.getLoading_images());
                    }
                    // 处理下载皮肤
                    CommonHandler.getInstatnce().handerMessage(handlerListener, resultMsgWhat, 1,
                            0, mAppConfigJson);
                }
            }
        });
    }

    /**
     * @author :Atar
     * @createTime:2017-5-24下午3:17:37
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void switchActivity(OnClickInfo onClickInfo, Context context) {
        try {
            if (onClickInfo != null) {
                // if (onClickInfo.isNeedLogin() && JoinInTGBActivity.startJoinInTGBActivity
                // (context)) {// 需要登陆
                // return;
                // }

                String className = onClickInfo.getClassName();
                if (className != null && className.contains("com.open.weex") && VERSION.SDK_INT <
                        VERSION_CODES.ICE_CREAM_SANDWICH) {
                    CommonToast.show("Android系统版本太低,请升级系统");
                    return;
                }
                if (onClickInfo.getSpecialInfo() != null && onClickInfo.getSpecialInfo().length()
                        > 0) {// 特殊点击事件 跳转 切换tabhost等
                    switchMainTab(onClickInfo.getSpecialInfo(), className);
                    return;
                }
                if (className == null || className.length() == 0) {
                    return;
                }
                Class<?> cls = Class.forName(className);
                Intent intent = new Intent(context, cls);
                if (ActivityManager.getActivityManager().getActivityStack() != null &&
                        ActivityManager.getActivityManager().getActivityStack().size() > 0) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent = getIntentFromOptionJson(context, intent, onClickInfo.getOptionJson(),
                        onClickInfo.getOnEventInfo());
                IntentUtil.startOtherActivity(context, intent);
            }
        } catch (Exception e) {
            ShowLog.e(TAG, CrashHandler.crashToString(e));
        }
    }

    /**
     * 处理特殊跳转事件
     *
     * @param specialInfo
     * @param subTabClassActivityName
     * @author :Atar
     * @createTime:2017-5-24下午3:15:07
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void switchMainTab(String specialInfo, String subTabClassActivityName) {
        try {
            if (specialInfo != null && specialInfo.length() > 0) {
                JSONObject specialJson = new JSONObject(specialInfo);
                int tabHostposition = 0;
                int viewPagerItem = -1;
                if (specialJson != null && specialJson.length() > 0) {
                    if (specialJson.has("tabHostposition")) {
                        tabHostposition = specialJson.getInt("tabHostposition");
                    }
                    if (specialJson.has("viewPagerItem")) {
                        viewPagerItem = specialJson.getInt("viewPagerItem");
                    } else {
                        viewPagerItem = -1;
                    }
                }
                // if (ActivityManager.getActivityManager().getActivity(MainTabActivity.class) !=
                // null) {
                // ActivityManager.getActivityManager().getActivity(MainTabActivity.class)
                // .switchMainTab(tabHostposition, subTabClassActivityName, viewPagerItem);
                // }
            }
        } catch (Exception e) {
            ShowLog.e(TAG, CrashHandler.crashToString(e));
        }
    }

    /**
     * 从json中获取参数传入到intent中去 适合app中所有跳转
     *
     * @param intent
     * @return
     * @author :Atar
     * @createTime:2017-5-24上午11:25:36
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static Intent getIntentFromOptionJson(Context context, Intent intent, String
            optionJson, String onEventInfo) {
        try {
            if (optionJson != null && optionJson.length() > 0) {// 解析跳转传入参数
                JSONArray jsonArray = new JSONArray(optionJson);
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject != null) {
                            if (jsonObject.has("intentKeyValueClassName")) {
                                String intentKeyValueClassName = jsonObject.getString
                                        ("intentKeyValueClassName");
                                String intentKey = jsonObject.getString("intentKey");
                                if ("int".equals(intentKeyValueClassName)) {
                                    int intentKeyValue = jsonObject.getInt("intentKeyValue");
                                    intent.putExtra(intentKey, intentKeyValue);
                                } else if ("String".equals(intentKeyValueClassName)) {
                                    String intentKeyValue = jsonObject.getString("intentKeyValue");
                                    if (intentKeyValue != null) {
                                        if (!"TAB_OPTION_JSON_KEY".equals(intentKey) &&
                                                !"VIEWPAGER_OPTION_JSON_KEY".equals(intentKey) &&
                                                intentKeyValue.contains(".js")
                                                && intentKeyValue.contains("build/src")) {
                                            // 传入weex js地址 ，自动原生 拼接可切换环境
                                            try {
                                                String[] str = intentKeyValue.split("/");
                                                if (str != null && str.length > 0) {
                                                    String path = str[0];
                                                    path = UrlParamCommon.IP.contains("com.cn") ?
                                                            path
                                                                    + "/" : "";
                                                    intentKeyValue = UrlParamCommon.WEEX_HOST +
                                                            path +
                                                            intentKeyValue;
                                                }
                                            } catch (Exception e) {

                                            }
                                        } else if (!"TAB_OPTION_JSON_KEY".equals(intentKey) &&
                                                !"VIEWPAGER_OPTION_JSON_KEY".equals(intentKey) &&
                                                intentKeyValue.contains(".html")
                                                && intentKeyValue.contains("assets/html")) {
                                            // 传入html地址 ，自动原生 拼接可切换环境
                                            intentKeyValue = UrlParamCommon.WEEX_HOST +
                                                    intentKeyValue;
                                        }
                                    }
                                    intent.putExtra(intentKey, intentKeyValue);
                                } else if ("double".equals(intentKeyValueClassName)) {
                                    double intentKeyValue = jsonObject.getDouble("intentKeyValue");
                                    intent.putExtra(intentKey, intentKeyValue);
                                } else if ("ArrayList<String>".equals(intentKeyValueClassName)) {
                                    String json = jsonObject.getString("intentKeyValue");
                                    if (json != null && json.length() > 0) {
                                        JSONArray jsonArray1 = new JSONArray(json);
                                        ArrayList<String> list = new ArrayList<String>();
                                        for (int j = 0; j < jsonArray1.length(); j++) {
                                            list.add(jsonArray1.getString(j));
                                        }
                                        intent.putStringArrayListExtra(intentKey, list);
                                    }
                                } else if ("MsgAndReMsgBean".equals(intentKeyValueClassName)) {
                                    // String json = jsonObject.getString("intentKeyValue");
                                    // Gson gson = new Gson();
                                    // MsgAndReMsgBean mMsgAndReMsgBean = gson.fromJson(json,
                                    // MsgAndReMsgBean.class);
                                    // intent.putExtra(intentKey, (Serializable) mMsgAndReMsgBean);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            ShowLog.e(TAG, CrashHandler.crashToString(e));
        }
        setEventInfo(context, onEventInfo);
        return intent;
    }

    /**
     * 设置统计事件
     *
     * @param onEventInfo
     * @author :Atar
     * @createTime:2017-7-24下午4:11:49
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static void setEventInfo(Context context, String onEventInfo) {
        try {
            if (onEventInfo != null && onEventInfo.length() > 0) {// 解析点击统计事件
                JSONObject onEventJson = new JSONObject(onEventInfo);
                if (onEventJson != null) {
                    String eventType = "";
                    String eventID = "";
                    String eventName = "";
                    if (onEventJson.has("eventType")) {
                        eventType = onEventJson.getString("eventType");
                    }
                    if (onEventJson.has("eventID")) {
                        eventID = onEventJson.getString("eventID");
                    }
                    if (onEventJson.has("eventName")) {
                        eventName = onEventJson.getString("eventName");
                    }
                    if ("0".equals(eventType)) {
                        // 百度统计
                        // StatService.onEvent(context, eventID, eventName, 1);
                    } else if ("1".equals(eventType)) {
                        // 友盟统计
                        // MobclickAgent.onEvent(context, eventName);
                    }
                }
            }
        } catch (Exception e) {
            ShowLog.e(TAG, CrashHandler.crashToString(e));
        }
    }

    /**
     * 得到默认配置数据
     *
     * @return
     * @author :Atar
     * @createTime:2017-5-24下午1:28:35
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public static String getDefaultSetting() {
        return "{\n" +
                "\t\"versionName\": \"3.00\",\n" +
                "\t\"isReplace\": true,\n" +
                "\t\"skinVersion\": \"3.00\",\n" +
                "\t\"replaceMinSkinVersion\": \"3.00\",\n" +
                "\t\"loadImage_Version\": \"3.00\",\n" +
                "\t\"loading_images\": [\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532067486482&di=b5158f26c54208acfd12a040bc70f1db&imgtype=0&src=http://img.zcool" +
                ".cn/community/01b9c5557555780000002d5c219c7f.jpg\", \"http://img.zcool.cn/community/01a03b564da3d632f87512f695e7b4.png\"],\n" +
                "\t\"all_skins\": [\"https://raw.githubusercontent.com/wgllss/atar-skin-eclipse/master/skin/purple/download_skin.apk,下载紫色皮肤,加载紫色皮肤,#FF0FF0,purple\", \"https://raw.githubusercontent" +
                ".com/wgllss/atar-skin-eclipse/master/skin/red/download_skin.apk,下载红色皮肤,加载红色皮肤,#FF0000,red\", \"https://raw.githubusercontent" +
                ".com/wgllss/atar-skin-eclipse/master/skin/green/download_skin.apk,下载绿色皮肤,加载绿色皮肤,#0ef000,green\", \"https://raw.githubusercontent" +
                ".com/wgllss/atar-skin-eclipse/master/skin/blue/download_skin.apk,下载蓝色皮肤,加载蓝色皮肤,#1191f6,blue\"],\n" +
                "\t\"main_tab_json\": [{\n" +
                "\t\t\"ID\": 10000,\n" +
                "\t\t\"menuName\": \"首页\",\n" +
                "\t\t\"menuNameColor\": \"#ffffff,#ffffff\",\n" +
                "\t\t\"menuNameSelcetColor\": \"#ffffff,#ffffff\",\n" +
                "\t\t\"menuNameTextSize\": 12,\n" +
                "\t\t\"menuNameMarginTop\": 5,\n" +
                "\t\t\"itemBgColor\": \"#212423,#212423\",\n" +
                "\t\t\"itemBgSelectColor\": \"#D53533,#D53533\",\n" +
                "\t\t\"isShowIcon\": true,\n" +
                "\t\t\"menuIcon\": \"assets://images/icon_tab_home_nor.png\",\n" +
                "\t\t\"selectMenuIcon\": \"assets://images/icon_tab_home_pre.png\",\n" +
                "\t\t\"menuIconWidthAndHeight\": 20,\n" +
                "\t\t\"orientation\": 1,\n" +
                "\t\t\"onClickInfo\": {\n" +
                "\t\t\t\"className\": \"com.atar.app.activitys.main.MainDemoActivity\"\n" +
                "\t\t}\n" +
                "\t}, {\n" +
                "\t\t\"ID\": 10001,\n" +
                "\t\t\"menuName\": \"订单记录\",\n" +
                "\t\t\"menuNameColor\": \"#ffffff,#ffffff\",\n" +
                "\t\t\"menuNameSelcetColor\": \"#ffffff,#ffffff\",\n" +
                "\t\t\"menuNameTextSize\": 12,\n" +
                "\t\t\"menuNameMarginTop\": 5,\n" +
                "\t\t\"itemBgColor\": \"#212423,#212423\",\n" +
                "\t\t\"itemBgSelectColor\": \"#D53533,#D53533\",\n" +
                "\t\t\"isShowIcon\": true,\n" +
                "\t\t\"menuIcon\": \"assets://images/icon_tab_order.png\",\n" +
                "\t\t\"selectMenuIcon\": \"assets://images/icon_tab_order.png\",\n" +
                "\t\t\"menuIconWidthAndHeight\": 20,\n" +
                "\t\t\"orientation\": 1,\n" +
                "\t\t\"isShowinfoNumRedBg\": true,\n" +
                "\t\t\"infoNum\": 900,\n" +
                "\t\t\"infoNumColor\": \"#ffffff\",\n" +
                "\t\t\"infoNumSize\": 15,\n" +
                "\t\t\"infoNumMarginLeft\": 18,\n" +
                "\t\t\"infoNumMarginTop\": 8,\n" +
                "\t\t\"onClickInfo\": {\n" +
                "\t\t\t\"className\": \"com.cjml.app.ui.activitys.main.OrderHistoryActivity\"\n" +
                "\t\t}\n" +
                "\t}, {\n" +
                "\t\t\"ID\": 10002,\n" +
                "\t\t\"menuName\": \"扫描记录\",\n" +
                "\t\t\"menuNameColor\": \"#ffffff,#ffffff\",\n" +
                "\t\t\"menuNameSelcetColor\": \"#ffffff,#ffffff\",\n" +
                "\t\t\"menuNameTextSize\": 12,\n" +
                "\t\t\"menuNameMarginTop\": 5,\n" +
                "\t\t\"itemBgColor\": \"#212423,#212423\",\n" +
                "\t\t\"itemBgSelectColor\": \"#D53533,#D53533\",\n" +
                "\t\t\"isShowIcon\": true,\n" +
                "\t\t\"menuIcon\": \"assets://images/icon_tab_scan.png\",\n" +
                "\t\t\"selectMenuIcon\": \"assets://images/icon_tab_scan.png\",\n" +
                "\t\t\"menuIconWidthAndHeight\": 20,\n" +
                "\t\t\"orientation\": 1,\n" +
                "\t\t\"onClickInfo\": {\n" +
                "\t\t\t\"className\": \"com.cjml.app.ui.activitys.main.ScanHistoryActivity\"\n" +
                "\t\t}\n" +
                "\t}, {\n" +
                "\t\t\"ID\": 10002,\n" +
                "\t\t\"menuName\": \"我的\",\n" +
                "\t\t\"menuNameColor\": \"#ffffff,#ffffff\",\n" +
                "\t\t\"menuNameSelcetColor\": \"#ffffff,#ffffff\",\n" +
                "\t\t\"menuNameTextSize\": 12,\n" +
                "\t\t\"menuNameMarginTop\": 5,\n" +
                "\t\t\"itemBgColor\": \"#212423,#212423\",\n" +
                "\t\t\"itemBgSelectColor\": \"#D53533,#D53533\",\n" +
                "\t\t\"isShowIcon\": true,\n" +
                "\t\t\"menuIcon\": \"assets://images/icon_tab_me_nor.png\",\n" +
                "\t\t\"selectMenuIcon\": \"assets://images/icon_tab_me_pre.png\",\n" +
                "\t\t\"menuIconWidthAndHeight\": 20,\n" +
                "\t\t\"orientation\": 1,\n" +
                "\t\t\"onClickInfo\": {\n" +
                "\t\t\t\"className\": \"com.cjml.app.ui.activitys.main.MeActivity\"\n" +
                "\t\t}\n" +
                "\t}]\n" +
                "}";
    }
}
