/**
 *
 */
package com.atar.app.activitys.main;

import android.activity.ActivityManager;
import android.common.CommonHandler;
import android.os.Message;
import android.reflection.NetWorkMsg;
import android.utils.ApplicationManagement;
import android.view.View;
import android.widget.AdapterView;

import com.atar.activity.AtarRefreshListViewActivity;
import com.atar.app.R;
import com.atar.app.activitys.proxy.ProxyActivity;
import com.atar.app.adapters.MainDemoAdapter;
import com.atar.beans.MenuItemBean;
import com.atar.enums.EnumMsgWhat;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2017-8-9下午4:04:04
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class MainDemoActivity extends AtarRefreshListViewActivity {

    private String TAG = MainDemoActivity.class.getSimpleName();

    private List<MenuItemBean> list = new ArrayList<MenuItemBean>();
    private MainDemoAdapter mMainDemoAdapter = new MainDemoAdapter(list);

    @Override
    public void initValue() {
        super.initValue();
        setOnDrawerBackEnabled(false);

        setActivityTitle("主界面");
//        list.add(new MenuItemBean("0", "网络测试1"));
//        list.add(new MenuItemBean("1", "网络测试2"));
//        list.add(new MenuItemBean("2", "ViewPagerDemoActivity"));
//        list.add(new MenuItemBean("21", "ViewPagerTab"));
//        list.add(new MenuItemBean("3", "相册"));
//        // list.add(new MenuItemBean("4", "DemoRefreshListViewActivity"));
//        list.add(new MenuItemBean("5", "图片浏览"));
//        list.add(new MenuItemBean("6", "下载"));
//        list.add(new MenuItemBean("7", "刷新"));
//        list.add(new MenuItemBean("8", "设置"));
//        list.add(new MenuItemBean("9", "语音录放"));
//        list.add(new MenuItemBean("10", "动态html"));
//        list.add(new MenuItemBean("11", "jni"));
//        list.add(new MenuItemBean("12", "当前版本" + ApplicationManagement.getVersionName()));
//        list.add(new MenuItemBean("13", "科大讯飞语音识别"));
//        list.add(new MenuItemBean("14", "百度语音识别"));
//        list.add(new MenuItemBean("15", "动态加载sd卡上的布局"));
//        list.add(new MenuItemBean("16", "class加载"));
        list.add(new MenuItemBean("17", "加载插件apk"));
//        list.add(new MenuItemBean("18", "wave曲线"));
//        list.add(new MenuItemBean("19", "wave曲线2"));

        // list.add(new MenuItemBean("8", "网络测试1"));
        mMainDemoAdapter.notifyDataSetChanged();
        setAdapter(mMainDemoAdapter);
    }

    @Override
    public void onHandlerData(Message msg) {
        super.onHandlerData(msg);
        switch (msg.what) {
            case EnumMsgWhat.REFRESH_PULL_DOWN:
            case EnumMsgWhat.REFRESH_PULL_UP:
                CommonHandler.getInstatnce().getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onStopRefresh();
                    }
                }, 1000);
                break;
        }
    }

    @Override
    public void NetWorkCall(NetWorkMsg msg) {
        super.NetWorkCall(msg);
        switch (msg.what) {
            case EnumMsgWhat.EInterface_Get_Wonder_Topic_List:
                if (msg.obj != null) {
//                    WonderfulTopicJson mWonderfulTopicJson = (WonderfulTopicJson) msg.obj;
//                    if (mWonderfulTopicJson != null) {
//                        CommonToast.show(mWonderfulTopicJson.getDto().get(1).getSubject());
//                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        super.onItemClick(arg0, arg1, arg2, arg3);
        int itemID = Integer.valueOf(list.get((int) arg3).getItemId());
        switch (itemID) {
//            case 0:
//                NetWorkInterfaces.GetWonderTopicList(this, this, "1");
//                break;
//            case 1:
//                Map<String, String> map = new HashMap<String, String>();
//                CommonStringUtil.setMap(map, "pageNo", "1");
//                NetWorkInterfaces.NetWorkCall(this, new NetWorkCallTListenet<WonderfulTopicJson>() {
//                    @Override
//                    public void NetWorkCall(WonderfulTopicJson t) {
//                        if (t != null) {
//                            CommonToast.show(t.getDto().get(0).getSubject());
//                        }
//                    }
//                }, UrlParamCommon.UrlWonderfulList, "GET", map, WonderfulTopicJson.class);
//                break;
//            case 2:
//                startActivity(new Intent(this, ViewPagerDemoActivity.class));
//                break;
//            case 3:
//                startActivity(new Intent(this, AlbumActivity.class));
//                break;
//            case 4:
//                startActivity(new Intent(this, DemoRefreshListViewActivity.class));
//                break;
//            case 5:
//                ArrayList<String> imgList = new ArrayList<String>();
//                imgList.add("http://img4.imgtn.bdimg.com/it/u=1906744648,758477532&fm=26&gp=0.jpg");
//                imgList.add("http://img0.imgtn.bdimg.com/it/u=3368323297," +
//                        "2417132385&fm=26&gp=0.jpg");
//                imgList.add("http://img2.imgtn.bdimg.com/it/u=917052547,4265044967&fm=26&gp=0.jpg");
//                ShowImageActivity.startShowImage(this, imgList, 1);
//                break;
//            case 6:
//                startActivity(new Intent(this, DownLoadActivity.class));
//                break;
//            case 7:
//                startActivity(new Intent(this, DemoRefreshActivity.class));
//                break;
//            case 8:
//                startActivity(new Intent(this, DemoSettingActivity.class));
//                break;
//            case 9:
//                startActivity(new Intent(this, DemoSpeexAudioActivity.class));
//                break;
//            case 21:
//                startActivity(new Intent(this, DemoRefreshInFragmentActivity.class));
//                break;
//            case 10:
//                AtarDynamicWebViewActivity.startAtarDynamicWebViewActivity(this, WeexUtils
//                        .HTML_TEST_URL, "", "", "", "", "0", false);
//                break;
//            case 11:
//                startActivity(new Intent(this, TestJNIActivity.class));
//                break;
//            case 13:
//                startActivity(new Intent(this, AudioListenerActivity.class));
//                break;
//            case 14:
//                startActivity(new Intent(this, BaiduAudioListenerActivity.class));
//                break;
//            case 15:
//                startActivity(new Intent(this, DemoLoadSDLayoutActivity.class));
//                break;
//            case 16:
//                startActivity(new Intent(this, ClassLoaderActivity.class));
//                break;
            case 17:
                final String className = "com.atar.other.activitys.demo.MainActivity";
                ProxyActivity.startProxyActivity(this, className);
                break;
//            case 18:
//                startActivity(new Intent(this, WavaLineActivity.class));
//                break;
//            case 19:
//                startActivity(new Intent(this, VoiceLineActivity.class));
//                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_common_top_left:// 顶部左边
                onBackPressed();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        ActivityManager.getActivityManager().exitApp();
//        runOnBackground();
    }

    @Override
    public void ChangeSkin(int skinType) {
        super.ChangeSkin(skinType);
        if (mMainDemoAdapter != null) {
            mMainDemoAdapter.setSkinType(skinType);
        }
    }
}
