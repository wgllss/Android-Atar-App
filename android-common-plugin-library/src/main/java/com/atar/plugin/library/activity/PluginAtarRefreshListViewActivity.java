package com.atar.plugin.library.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Message;
import android.reflection.NetWorkMsg;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.atar.activity.AtarRefreshListViewActivity;
import com.atar.interfaces.PluginInterface;
import com.atar.widgets.refresh.DataDispose;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author:Atar
 * @createTime: 2018/9/7 上午9:46
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description :
 * **************************************************************************************************************************************************************************
 */
public class PluginAtarRefreshListViewActivity extends AtarRefreshListViewActivity implements PluginInterface<AtarRefreshListViewActivity> {

    protected boolean isPlugin = false;
    protected AtarRefreshListViewActivity thisContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (!isPlugin) {
            thisContext = this;
            super.onCreate(savedInstanceState);
        }
    }

    @Override
    public void attachContext(AtarRefreshListViewActivity context) {
        isPlugin = true;
        thisContext = context;
    }

    @Override
    public void onRestart() {
        if (!isPlugin) {
            super.onRestart();
        }
    }

    @Override
    public void onStart() {
        if (!isPlugin) {
            super.onStart();
        }
    }

    @Override
    public void onResume() {
        if (!isPlugin) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (!isPlugin) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (!isPlugin) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (!isPlugin) {
            super.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!isPlugin) {
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!isPlugin) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onChangeSkin(int skinType) {
    }

    @Override
    public void setContentView(int layoutResID) {
        if (isPlugin) {
            thisContext.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void setContentView(View view) {
        if (isPlugin) {
            thisContext.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }


    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (isPlugin) {
            thisContext.setContentView(view, params);
        } else {
            super.setContentView(view, params);
        }
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        if (isPlugin) {
            return thisContext.getLayoutInflater();
        } else {
            return super.getLayoutInflater();
        }
    }

    @Override
    public Window getWindow() {
        if (isPlugin) {
            return thisContext.getWindow();
        } else {
            return super.getWindow();
        }
    }

    @Override
    public View findViewById(int id) {
        if (isPlugin) {
            return thisContext.findViewById(id);
        } else {
            return super.findViewById(id);
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (isPlugin) {
            return thisContext.getClassLoader();
        } else {
            return super.getClassLoader();
        }
    }

    @Override
    public WindowManager getWindowManager() {
        if (isPlugin) {
            return thisContext.getWindowManager();
        } else {
            return super.getWindowManager();
        }
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if (isPlugin) {
            return thisContext.getApplicationInfo();
        } else {
            return super.getApplicationInfo();
        }
    }

    @Override
    public void finish() {
        if (isPlugin) {
            thisContext.finish();
        } else {
            super.finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isPlugin) {
            return false;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public void onBackPressed() {
        if (isPlugin) {
            thisContext.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    /**************************************以上重写系统方法，以下重写自定义的公用方法***************************************************************************/

    @Override
    public void init() {
        if (isPlugin) {
            thisContext.init();
        } else {
            super.init();
        }
    }

    @Override
    public void initControl() {
        if (isPlugin) {
            thisContext.initControl();
        } else {
            super.initControl();
        }
    }

    @Override
    public void bindEvent() {
        if (isPlugin) {
            thisContext.bindEvent();
        } else {
            super.bindEvent();
        }
    }

    @Override
    public void initValue() {
        if (isPlugin) {
            thisContext.initValue();
        } else {
            super.initValue();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (isPlugin) {
            thisContext.startActivity(intent);
        } else {
            super.startActivity(intent);
        }
    }

    @Override
    public void addContentView(int layoutResId) {
        if (isPlugin) {
            thisContext.addContentView(layoutResId);
        } else {
            super.addContentView(layoutResId);
        }
    }

    @Override
    public void setActivityTitle(String strTitile) {
        if (isPlugin) {
            thisContext.setActivityTitle(strTitile);
        } else {
            super.setActivityTitle(strTitile);
        }
    }


    @Override
    public void NetWorkCall(NetWorkMsg msg) {
        if (isPlugin) {
            thisContext.NetWorkCall(msg);
        } else {
            super.NetWorkCall(msg);
        }
    }

    @Override
    public void onHandlerData(Message msg) {
        if (isPlugin) {
            thisContext.onHandlerData(msg);
        } else {
            super.onHandlerData(msg);
        }
    }

    @Override
    public void sendEmptyMessage(int msgWhat) {
        if (isPlugin) {
            thisContext.sendEmptyMessage(msgWhat);
        } else {
            super.sendEmptyMessage(msgWhat);
        }
    }

    @Override
    public void sendEmptyMessageDelayed(final int msgWhat, long delayMillis) {
        if (isPlugin) {
            thisContext.sendEmptyMessageDelayed(msgWhat, delayMillis);
        } else {
            super.sendEmptyMessageDelayed(msgWhat, delayMillis);
        }
    }

    @Override
    public void sendMessage(int msgWhat, Object obj) {
        if (isPlugin) {
            thisContext.sendMessage(msgWhat, obj);
        } else {
            super.sendMessage(msgWhat, obj);
        }
    }

    @Override
    public void sendMessage(int msgWhat, int msg1, int msg2) {
        if (isPlugin) {
            thisContext.sendMessage(msgWhat, msg1, msg2);
        } else {
            super.sendMessage(msgWhat, msg1, msg2);
        }
    }

    @Override
    public void sendMessage(int msgWhat, int msg1, int msg2, Object obj) {
        if (isPlugin) {
            thisContext.sendMessage(msgWhat, msg1, msg2, obj);
        } else {
            super.sendMessage(msgWhat, msg1, msg2, obj);
        }
    }

    @Override
    public void setRefreshing() {
        if (isPlugin) {
            thisContext.setRefreshing();
        } else {
            super.setRefreshing();
        }
    }

    @Override
    public void onStopRefresh() {
        if (isPlugin) {
            thisContext.onStopRefresh();
        } else {
            super.onStopRefresh();
        }
    }

    @Override
    public DataDispose<PullToRefreshListView, ListView> getDataDispose() {
        if (isPlugin) {
            return thisContext.getDataDispose();
        } else {
            return super.getDataDispose();
        }
    }

    @Override
    public boolean isPullDownRefresh() {
        if (isPlugin) {
            return thisContext.isPullDownRefresh();
        } else {
            return super.isPullDownRefresh();
        }
    }

    @Override
    public boolean isFirstLoad() {
        if (isPlugin) {
            return thisContext.isFirstLoad();
        } else {
            return super.isFirstLoad();
        }
    }

    @Override
    public void setIsFirstLoad(boolean isFirstLoad) {
        if (isPlugin) {
            thisContext.setIsFirstLoad(isFirstLoad);
        } else {
            super.setIsFirstLoad(isFirstLoad);
        }
    }

    @Override
    public PullToRefreshListView getPullView() {
        if (isPlugin) {
            return thisContext.getPullView();
        } else {
            return super.getPullView();
        }
    }

    @Override
    public ListView getRefreshView() {
        if (isPlugin) {
            return thisContext.getRefreshView();
        } else {
            return super.getRefreshView();
        }
    }

    @Override
    public void setRefreshMode(PullToRefreshBase.Mode mode) {
        if (isPlugin) {
            thisContext.setRefreshMode(mode);
        } else {
            super.setRefreshMode(mode);
        }
    }

    @Override
    public boolean isSwitchData() {
        if (isPlugin) {
            return thisContext.isSwitchData();
        } else {
            return super.isSwitchData();
        }
    }

    @Override
    public void setIsSwitchViewData(boolean isSwitchViewData) {
        if (isPlugin) {
            thisContext.setIsSwitchViewData(isSwitchViewData);
        } else {
            super.setIsSwitchViewData(isSwitchViewData);
        }
    }

    @Override
    public void setToastText(String strToastContent) {
        if (isPlugin) {
            thisContext.setToastText(strToastContent);
        } else {
            super.setToastText(strToastContent);
        }
    }


    @Override
    public void setToastTextGone() {
        if (isPlugin) {
            thisContext.setToastTextGone();
        } else {
            super.setToastTextGone();
        }
    }


    @Override
    public void setTextView(TextView txtToast) {
        if (isPlugin) {
            thisContext.setTextView(txtToast);
        } else {
            super.setTextView(txtToast);
        }
    }

    @Override
    public void setRefreshView(PullToRefreshListView t) {
        if (isPlugin) {
            thisContext.setRefreshView(t);
        } else {
            super.setRefreshView(t);
        }
    }

    @Override
    public void setAdapter(BaseAdapter adapter) {
        if (isPlugin) {
            thisContext.setAdapter(adapter);
        } else {
            super.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View contentView, int arg2, long arg3) {
        if (isPlugin) {
            thisContext.onItemClick(adapterView, contentView, arg2, arg3);
        } else {
            super.onItemClick(adapterView, contentView, arg2, arg3);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View contentView, int arg2, long arg3) {
        if (isPlugin) {
            return thisContext.onItemLongClick(adapterView, contentView, arg2, arg3);
        } else {
            return super.onItemLongClick(adapterView, contentView, arg2, arg3);
        }
    }

    @Override
    public void ChangeSkin(int skinType) {
        if (isPlugin) {
            thisContext.ChangeSkin(skinType);
        } else {
            super.ChangeSkin(skinType);
        }
    }

    @Override
    public void setRefreshSkin(int skinType) {
        if (isPlugin) {
            thisContext.setRefreshSkin(skinType);
        } else {
            super.setRefreshSkin(skinType);
        }
    }
}
