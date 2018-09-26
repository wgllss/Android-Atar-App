package com.atar.app.activitys.htmls;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.utils.ShowLog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TabHost;

import com.atar.app.activitys.main.AtarTabActivity;
import com.atar.app.adapters.WeexTabAdapter;
import com.atar.app.R;
import com.atar.app.config.OnClickInfo;
import com.atar.app.config.AppConfigUtils;
import com.atar.app.config.TabMenuItemBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author:Atar
 * @createTime: 2018/9/5 下午2:24
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description :
 * **************************************************************************************************************************************************************************
 */
public class TabHostWithWebViewActivity extends AtarTabActivity {

    private String TAG = TabHostWithWebViewActivity.class.getSimpleName();

    private String tabOptionJsonKey;
    protected GridView buttomGridView;
    protected List<TabMenuItemBean> menuList = new ArrayList<TabMenuItemBean>();
    protected WeexTabAdapter mMenuAdapter = new WeexTabAdapter(menuList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIsExtendsAtarCommonActivity(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        initControl(savedInstanceState);
        init();
    }

    private void initControl(Bundle savedInstanceState) {
        tabHost = (TabHost) this.findViewById(android.R.id.tabhost);//获取TabHost
        buttomGridView = (GridView) findViewById(R.id.common_buttom_menu);
        //初始化activity管理者
        manager = new LocalActivityManager(this, false);
        //通过管理者保存当前页面状态
        manager.dispatchCreate(savedInstanceState);
        //将管理者类对象添加至TabHost
        tabHost.setup(manager);
        setOnDrawerBackEnabled(false);
    }

    @Override
    public void initValue() {
        if (tabOptionJsonKey == null || tabOptionJsonKey.length() == 0) {
            return;
        }
        String tab_option_json = getIntent().getStringExtra(tabOptionJsonKey);
        if (tab_option_json == null) {
            return;
        }
        Gson gson = new Gson();
        List<TabMenuItemBean> list = gson.fromJson(tab_option_json, new
                TypeToken<List<TabMenuItemBean>>() {
                }.getType());
        if (list != null && list.size() > 0) {
            menuList.clear();
            for (int i = 0; i < list.size(); i++) {
                setDynamicActivity(list.get(i), i, list.get(i).getID(), list.get(i)
                        .getOnClickInfo());
            }
            tabHost.setCurrentTab(0);
        }
        if (menuList.size() == 0) {
            return;
        }
        buttomGridView.setNumColumns(menuList.size());
        mMenuAdapter.setCurrentPostiotn(0);
        buttomGridView.setAdapter(mMenuAdapter);
        buttomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                try {
                    mMenuAdapter.setCurrentPostiotn(arg2);
                    tabHost.setCurrentTab(arg2);
                } catch (Exception e) {
                    ShowLog.e(TAG, "initValue-->" + e);
                }
            }
        });
    }

    /**
     * 设置动态Activity
     *
     * @param position
     * @param onClickInfo
     */
    protected void setDynamicActivity(TabMenuItemBean tabMenuItemBean, int position, int ID,
                                      OnClickInfo onClickInfo) {
        try {
            menuList.add(tabMenuItemBean);
            if (onClickInfo != null) {
                Intent intent = new Intent(this, Class.forName(onClickInfo.getClassName()));
                String optionJson = onClickInfo.getOptionJson();
                String onEventInfo = onClickInfo.getOnEventInfo();
                intent = AppConfigUtils.getIntentFromOptionJson(this, intent, optionJson,
                        onEventInfo);
                tabHost.addTab(tabHost.newTabSpec(onClickInfo.getClassName()).setIndicator("" +
                        position).setContent(intent));
            }
        } catch (Exception e) {
            ShowLog.e(TAG, "setDynamicActivity-->" + e);
        }
    }

    // super.onCreate(savedInstanceState);之前调用
    protected void setTabOptionJsonKey(String tabOptionJsonKey) {
        this.tabOptionJsonKey = tabOptionJsonKey;
    }


    @Override
    public void ChangeSkin(int skinType) {
        super.ChangeSkin(skinType);
        if (mMenuAdapter != null) {
            mMenuAdapter.setSkinType(skinType);
        }
    }

    public void setCurrentTab(int position) {
        try {
            if (mMenuAdapter != null) {
                mMenuAdapter.setCurrentPostiotn(position);
            }
            if (tabHost != null) {
                tabHost.setCurrentTab(position);
            }
        } catch (Exception e) {
            ShowLog.e(TAG, "setCurrentTab-->" + e);
        }
    }
}
