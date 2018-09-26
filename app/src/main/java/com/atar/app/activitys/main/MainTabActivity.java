package com.atar.app.activitys.main;

import android.appconfig.AppConfigModel;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.atar.app.activitys.htmls.TabHostWithWebViewActivity;
import com.atar.app.config.AppConfigJson;
import com.atar.app.config.AppConfigUtils;
import com.atar.utils.IntentUtil;
import com.google.gson.Gson;

public class MainTabActivity extends TabHostWithWebViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTabOptionJsonKey(TAB_OPTION_JSON_KEY);
        super.onCreate(savedInstanceState);
    }

    private static String TAB_OPTION_JSON_KEY = "TAB_OPTION_JSON_KEY";

    /**
     * 跳转到app 主main tab host页面
     *
     * @param context
     */
    public static void startMainTabActivity(Context context) {
        String configJson = AppConfigModel.getInstance().getString(AppConfigUtils.ANDRIOD_APP_CONFIG_KEY, AppConfigUtils.getDefaultSetting());
        Gson gson = new Gson();
        AppConfigJson mAppConfigJson = gson.fromJson(configJson, AppConfigJson.class);
        if (mAppConfigJson != null && mAppConfigJson.getMain_tab_json() != null) {
            String tab_option_json = gson.toJson(mAppConfigJson.getMain_tab_json());
            Intent intent = new Intent(context, MainTabActivity.class);
            intent.putExtra(TAB_OPTION_JSON_KEY, tab_option_json);
            IntentUtil.startActivityWithOutTween(context, intent);
        }
    }
}
