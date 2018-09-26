/**
 *
 */
package com.atar.app.activitys.load;

import android.activity.ActivityManager;
import android.app.Activity;
import android.appconfig.AppConfigModel;
import android.content.Intent;
import android.graphics.Bitmap;
import android.interfaces.HandlerListener;
import android.os.Bundle;
import android.os.Message;
import android.skin.SkinResourcesManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.utils.ApplicationManagement;
import android.utils.ScreenUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.atar.app.R;
import com.atar.app.activitys.main.MainTabActivity;
import com.atar.app.config.AppConfigJson;
import com.atar.app.config.AppConfigUtils;
import com.atar.utils.IntentUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :Atar
 * @createTime:2017-9-20上午10:47:05
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description: ****************************************************************************************************************************************************************************
 */
public class AtarLoadActivity extends Activity implements OnPageChangeListener, HandlerListener {
    /**
     * 引导图版本key
     */
    public static final String LOADIMAGE_VERSION_KEY = "LOADIMAGE_VERSION_KEY";
    /**
     * 引导图版本
     */
    public static String loadImage_Version = "1.0";// 本次版本有引导图时or 延用上一次引导图 则 改成和本次发布版本号一样 (如本次发布
    // 6.00版本，本次6.00版本有引导图 此处就填 6.00 反之不用处理)

    private String versionName = "";

    private ViewPager mViewPager;
    private LinearLayout indicator;
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private List<String> loading_images;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().setLoadActivity(this);
        if (!this.isTaskRoot()) { // 判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            // 如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;// finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }

        setContentView(R.layout.activity_load);
        // 获取app配置首页txt文件配置信息 及 开机引导图片 获取配置版本要放在前面getServerTextJson
        AppConfigUtils.getServerTextJson(AppConfigUtils.andriod_app_config_home_url,
                AppConfigUtils.ANDRIOD_APP_CONFIG_KEY, this, 50505);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        indicator = (LinearLayout) findViewById(R.id.ad_indicator);
        versionName = ApplicationManagement.getVersionName();
        loadImage_Version = AppConfigModel.getInstance().getString(LOADIMAGE_VERSION_KEY,
                loadImage_Version);// 获取引导版本
        // 获取app配置首页txt文件配置信息 及 开机引导图片 获取配置版本要放在前面getServerTextJson
        try {
            options = new DisplayImageOptions.Builder().showStubImage(0).showImageForEmptyUri(0)
                    .showImageOnFail(0).cacheInMemory().cacheOnDisc().extraForDownloader(1)
                    .displayer(new RoundedBitmapDisplayer(0)).bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        } catch (Exception e) {

        }

        // 获取app assets下离线文件 供AtarDynamicWebViewActivity用 需要放在上面try catch 判断版本之后
        // 防止被AppConfigModel.getInstance().cleanAll() 清除掉
        AppConfigUtils.getOffineFilePath(this);
        if (versionName.compareToIgnoreCase(loadImage_Version) > 0) {
            MainTabActivity.startMainTabActivity(this);
//            finishThis(MainTabActivity.class);
        } else {
            if (AppConfigModel.getInstance().getBoolean(AtarLoadActivity.class.getSimpleName() +
                    loadImage_Version, true)) {
                String imagesJson = AppConfigModel.getInstance().getString(AppConfigUtils
                        .APP_LOADING_IMAGES_KEY, "");
                if (imagesJson == null || imagesJson.length() == 0) {
                    imagesJson = "[\"assets://images/loading1.png\"," +
                            "\"assets://images/loading2.jpg\",\"assets://images/loading3.jpeg\"]";
                }
                try {
                    Gson gson = new Gson();
                    loading_images = gson.fromJson(imagesJson, new TypeToken<List<String>>() {
                    }.getType());
                } catch (Exception e) {

                }

                mViewPager.setAdapter(mPagerAdapter);
                if (loading_images.size() > 1) {
                    for (int i = 0; i < loading_images.size(); i++) {
                        ImageView imagerView = new ImageView(this);
                        int size = (int) ScreenUtils.getIntToDip(20);
                        imagerView.setLayoutParams(new LinearLayout.LayoutParams(size, size));
                        imagerView.setPadding(15, 0, 15, 0);
                        imagerView.setScaleType(ScaleType.FIT_CENTER);
                        if (i == 0) {
                            imagerView.setImageResource(R.mipmap.jinr02);
                        } else {
                            imagerView.setImageResource(R.mipmap.jinr011);
                        }
                        indicator.addView(imagerView);
                    }
                    indicator.setVisibility(View.GONE);
                } else {
                    indicator.setVisibility(View.GONE);
                }
                mViewPager.setOffscreenPageLimit(loading_images.size());
                mViewPager.setOnPageChangeListener(this);

                AppConfigModel.getInstance().putBoolean(AtarLoadActivity.class.getSimpleName() +
                        loadImage_Version, false, true);
            } else {
//                finishThis(MainTabActivity.class);
                MainTabActivity.startMainTabActivity(this);
            }
        }
    }

    @Override
    public void onHandlerData(Message msg) {
        switch (msg.what) {
            case 50505:
                if (msg.arg1 == 0) {// 下载引导图
                    try {
                        @SuppressWarnings("unchecked")
                        List<String> loading_images = (List<String>) msg.obj;
                        if (loading_images != null && loading_images.size() > 0) {
                            for (int i = 0; i < loading_images.size(); i++) {
                                ImageView imagerView = new ImageView(AtarLoadActivity.this);
                                mImageLoader.displayImage(loading_images.get(i), imagerView,
                                        options, null);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (msg.arg1 == 1) {// 下载皮肤
                    if (msg.obj != null) {
                        AppConfigJson mAppConfigJson = (AppConfigJson) msg.obj;
                        SkinResourcesManager.getInstance(this).downLoadSkin(this, mAppConfigJson
                                .getSkinVersion(), mAppConfigJson.getReplaceMinVersion());
                    }
                }
                break;
        }
    }

//    private void finishThis(Class<?> cls) {
//        IntentUtil.startActivityWithOutTween(this, new Intent(this, cls));
//        overridePendingTransition(R.anim.anim_alpha_121, R.anim.anim_alpha_121);
//    }

    PagerAdapter mPagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView imgaeView = new ImageView(AtarLoadActivity.this);
            imgaeView.setScaleType(ScaleType.FIT_XY);
            mImageLoader.displayImage(loading_images.get(position), imgaeView, options, null);
            container.addView(imgaeView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            if (position == loading_images.size() - 1) {
                imgaeView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
//                        finishThis(MainTabActivity.class);
                        MainTabActivity.startMainTabActivity(AtarLoadActivity.this);
                    }
                });
            }
            return imgaeView;
        }

        @Override
        public int getCount() {
            return loading_images == null ? 0 : loading_images.size();
        }
    };

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        if (indicator != null) {
            for (int i = 0; i < indicator.getChildCount(); i++) {
                if (i == arg0) {
                    if (indicator.getChildCount() > 0 && indicator.getChildAt(i) != null) {
                        ((ImageView) indicator.getChildAt(i)).setImageResource(R.mipmap.jinr02);
                    }
                } else {
                    if (indicator.getChildCount() > 0 && indicator.getChildAt(i) != null) {
                        ((ImageView) indicator.getChildAt(i)).setImageResource(R.mipmap.jinr011);
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        /* 复写 不做任务处理 按返回键无效 */
    }
}
