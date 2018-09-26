package com.atar.app.activitys.main;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.utils.ShowLog;
import android.widget.TabHost;

import com.atar.activity.AtarCommonActivity;

public class AtarTabActivity extends AtarCommonActivity {

    private String TAG = AtarTabActivity.class.getSimpleName();
    private static final String STATES_KEY = "android:states";

    protected LocalActivityManager manager;
    protected TabHost tabHost;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (tabHost != null && tabHost.getCurrentTabTag() != null && manager != null) {
                Activity activity = manager.getActivity(tabHost.getCurrentTabTag());
                if (activity != null && activity instanceof AtarCommonActivity) {
                    ((AtarCommonActivity) activity).onActivityResult(requestCode, resultCode, data);
                }
            }
        } catch (Exception e) {
            ShowLog.e(TAG, "onActivityResult-->" + e);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (tabHost != null && tabHost.getCurrentTabTag() != null && manager != null) {
                Activity activity = manager.getActivity(tabHost.getCurrentTabTag());
                if (activity != null && activity instanceof AtarCommonActivity) {
                    ((AtarCommonActivity) activity).onRequestPermissionsResult(requestCode,
                            permissions, grantResults);
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = savedInstanceState != null ? (Bundle) savedInstanceState.getBundle
                (STATES_KEY) : null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (manager != null) {
            Bundle state = manager.saveInstanceState();
            if (state != null) {
                outState.putBundle(STATES_KEY, state);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (manager != null) {
            manager.dispatchResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (manager != null) {
            manager.dispatchPause(isFinishing());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (manager != null) {
            manager.dispatchStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null) {
            manager.dispatchDestroy(isFinishing());
        }
    }
}
