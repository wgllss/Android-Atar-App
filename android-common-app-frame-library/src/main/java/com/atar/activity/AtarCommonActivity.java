package com.atar.activity;

import android.activity.CommonActivity;
import android.application.CrashHandler;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.interfaces.NetWorkCallListener;
import android.interfaces.OnOpenDrawerCompleteListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.reflection.NetWorkMsg;
import android.skin.SkinUtils;
import android.utils.ApplicationManagement;
import android.utils.ScreenUtils;
import android.utils.ShowLog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atar.common.business.R;
import com.atar.utils.IntentUtil;

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author:Atar
 * @createTime: 2018/9/5 下午2:21
 * @version:1.0.0
 * @modifyTime:
 * @modifyAuthor:
 * @description :
 * **************************************************************************************************************************************************************************
 */
public class AtarCommonActivity extends CommonActivity implements View.OnClickListener,
        NetWorkCallListener {
    private String TAG = AtarCommonActivity.class.getSimpleName();

    protected ImageView imgCommonTopLeft;
    protected ImageView imgCommonTopRight;
    protected TextView txtCommonTopTitle;
    protected RelativeLayout topTitleBarBg;
    protected View commonContentBg;
    protected TextView txtCommonTopRight;
    public TextView txtCommonTopLeft;
    protected TextView txtActivityRightTxt;
    private LinearLayout linearLoading;
    /* 是否需要该父类所有布局 */
    private boolean isExtendsAtarCommonActivity = true;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isExtendsAtarCommonActivity) {
            try {
                setContentView(R.layout.activity_atar_common);
                txtCommonTopTitle = (TextView) findViewById(R.id.txt_common_top_title);
                imgCommonTopLeft = (ImageView) findViewById(R.id.img_common_top_left);
                imgCommonTopRight = (ImageView) findViewById(R.id.img_common_top_right);
                txtCommonTopRight = (TextView) findViewById(R.id.txt_common_top_right);
                txtCommonTopLeft = (TextView) findViewById(R.id.txt_common_top_left);
                txtActivityRightTxt = (TextView) findViewById(R.id.txt_common_top_title_right_txt);
                topTitleBarBg = (RelativeLayout) findViewById(R.id.common_top_title_bar);
                linearLoading = (LinearLayout) findViewById(R.id.linear_loading);
                imgCommonTopLeft.setOnClickListener(this);
                imgCommonTopRight.setOnClickListener(this);
                txtCommonTopRight.setOnClickListener(this);
                txtActivityRightTxt.setOnClickListener(this);
                txtCommonTopLeft.setOnClickListener(this);
            } catch (Exception e) {
                ShowLog.e(TAG, e);
            }
        }
    }

    @Override
    protected void OnRunBackground(boolean isOnBackGround) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_common_top_left) {
            IntentUtil.finish(this);
        } else if (v.getId() == R.id.img_common_top_right) {

        } else if (v.getId() == R.id.txt_common_top_right) {

        }
    }

    /**
     * 添加中间布局
     *
     * @param layoutResId
     * @author :Atar
     * @createTime:2014-6-13下午9:07:25
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void addContentView(int layoutResId) {
        if (isExtendsAtarCommonActivity) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            commonContentBg = inflater.inflate(layoutResId, null);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.TOP | Gravity.LEFT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && topTitleBarBg != null) {
                int statusBarHeight = ScreenUtils.getStatusBarHeight(this);
                int layoutHeight = (statusBarHeight > 0 ? statusBarHeight : 36) + (int)
                        ScreenUtils.getIntToDip(45);
                topTitleBarBg.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout
                        .LayoutParams.MATCH_PARENT, layoutHeight));
                topTitleBarBg.setPadding(0, statusBarHeight, 0, 0);
                lp.topMargin = layoutHeight;
            } else {
                lp.topMargin = (int) ScreenUtils.getIntToDip(45);
            }
            addContentView(commonContentBg, lp);
            if (linearLoading != null) {
                linearLoading.bringToFront();
            }
            init();
        }
    }

    /**
     * 设置Activity顶部标题
     *
     * @param strTitile
     * @author :Atar
     * @createTime:2014-6-13下午9:07:51
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void setActivityTitle(String strTitile) {
        if (txtCommonTopTitle != null)
            txtCommonTopTitle.setText(strTitile);
    }

    /**
     * 隐藏顶部左边
     *
     * @author :Atar
     * @createTime:2014-6-13下午9:03:42
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    protected void setTopLeftViewGone() {
        if (imgCommonTopLeft != null)
            imgCommonTopLeft.setVisibility(View.GONE);
    }

    /**
     * 隐藏顶部右边
     *
     * @author :Atar
     * @createTime:2014-6-13下午9:04:48
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    protected void setTopRightViewGone() {
        if (imgCommonTopRight != null)
            imgCommonTopRight.setVisibility(View.GONE);
    }

    /**
     * 显示顶部右边图片
     *
     * @author :Atar
     * @createTime:2014-10-22下午5:12:17
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    protected void setTopRightViewVisible() {
        if (imgCommonTopRight != null)
            imgCommonTopRight.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左边图片
     *
     * @author :Atar
     * @createTime:2014-7-1下午4:33:22
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    protected void setLeftImageDrawable(Drawable drawable) {
        if (imgCommonTopLeft != null) {
            imgCommonTopLeft.setImageDrawable(drawable);
            imgCommonTopLeft.setVisibility(View.VISIBLE);
            if (txtCommonTopLeft != null) {
                txtCommonTopLeft.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置顶部左边文字
     *
     * @param strLeftText
     * @author :Atar
     * @createTime:2015-3-26下午1:29:45
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    protected void setTopLeftText(String strLeftText) {
        if (txtCommonTopLeft != null) {
            txtCommonTopLeft.setText(strLeftText);
            txtCommonTopLeft.setVisibility(View.VISIBLE);
            if (imgCommonTopLeft != null) {
                imgCommonTopLeft.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置右边图片
     *
     * @author :Atar
     * @createTime:2014-7-1下午4:33:45
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    protected void setRightImageDrawable(Drawable drawable) {
        if (imgCommonTopRight != null) {
            imgCommonTopRight.setImageDrawable(drawable);
            imgCommonTopRight.setVisibility(View.VISIBLE);
            if (txtCommonTopRight != null) {
                txtCommonTopRight.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置顶部右边图片显示or ？？？
     *
     * @param visivility
     * @author :Atar
     * @createTime:2014-11-9下午4:47:52
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void setRightVisibility(int visivility) {
        if (imgCommonTopRight != null) {
            imgCommonTopRight.setVisibility(visivility);
        }
    }

    /**
     * 设置顶部右边文字
     *
     * @param strText
     * @author :Atar
     * @createTime:2014-11-13下午1:55:56
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    protected void setTopRightText(String strText) {
        if (txtCommonTopRight != null) {
            txtCommonTopRight.setText(strText);
            txtCommonTopRight.setVisibility(View.VISIBLE);
            if (imgCommonTopRight != null) {
                imgCommonTopRight.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 隐藏loading圈
     *
     * @author :Atar
     * @createTime:2014-9-3上午11:33:27
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void setLoadingViewGone() {
        if (linearLoading != null) {
            linearLoading.setVisibility(View.GONE);
        }
    }

    /**
     * 显示loading圈
     *
     * @author :Atar
     * @createTime:2014-9-3上午11:39:16
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void setLoadingViewVisible() {
        if (linearLoading != null) {
            linearLoading.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏标题右边文字
     *
     * @author :Atar
     * @createTime:2015-6-4下午5:35:01
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void setActivityRightTxtGone() {
        if (txtActivityRightTxt != null) {
            txtActivityRightTxt.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化整个activity
     *
     * @author :Atar
     * @createTime:2014-6-13下午9:06:21
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void init() {
        try {
            initControl();
            initValue();
            bindEvent();
        } catch (Exception e) {
            ShowLog.e(TAG, "init()-->" + CrashHandler.crashToString(e));
        }
    }

    /**
     * 装载控件
     *
     * @author :Atar
     * @createTime:2014-6-13上午11:35:38
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description: 如:findViewById(R.id.xxx)
     */
    public void initControl() {
    }// 装载控件

    /**
     * 绑定监听事件
     *
     * @author :Atar
     * @createTime:2014-6-13上午11:36:31
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description: 如:setOnClickListener(this)等
     */
    public void bindEvent() {
    }// 绑定监听事件

    /**
     * 初始化值:
     *
     * @author :Atar
     * @createTime:2014-6-13上午11:37:53
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description: 如:setText("XXXX")
     */
    public void initValue() {
    }// 初始化值

    @Override
    public void NetWorkCall(NetWorkMsg msg) {
        setLoadingViewGone();
        // CommonLoading.dissMissLoading();
    }

    @Override
    public void onOpenDrawerComplete() {
        IntentUtil.finishWithOutTween(this);
    }

    @Override
    public boolean onMoveRight() {
        return false;
    }

    /**
     * 是否继承该AtarCommonActivity所有布局
     *
     * @param isExtendsAtarCommonActivity 在super.onCreat() 之前调用
     * @author :Atar
     * @createTime:2014-8-25下午5:05:32
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void setIsExtendsAtarCommonActivity(boolean isExtendsAtarCommonActivity) {
        this.isExtendsAtarCommonActivity = isExtendsAtarCommonActivity;
    }


    /**
     * 确认退到后台
     *
     * @author :Atar
     * @createTime:2014-7-8下午2:13:03
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    public void runOnBackground() {
        if (linearLoading != null && linearLoading.getVisibility() == View.VISIBLE) {
            setLoadingViewGone();
            return;
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
            intent.addCategory(Intent.CATEGORY_HOME);
            this.startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (linearLoading != null && linearLoading.getVisibility() == View.VISIBLE) {
            setLoadingViewGone();
            return;
        }
        super.onBackPressed();
        IntentUtil.finish(this);
    }

    @Override
    public void ChangeSkin(int skinType) {
        if (isExtendsAtarCommonActivity) {
            SkinUtils.setImageDrawable(this, R.string.img_back, skinType, imgCommonTopLeft);
            SkinUtils.setBackgroundColor(this, R.string.common_tab_bg_color, skinType,
                    commonContentBg);
            SkinUtils.setTextColor(this, R.string.common_activity_title_color, skinType,
                    txtCommonTopTitle, txtCommonTopRight);
            SkinUtils.setBackgroundColor(this, R.string.common_top_title_bar_bg_color, skinType,
                    topTitleBarBg);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
