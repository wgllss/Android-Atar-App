package com.atar.other.activitys.demo;

import android.os.Bundle;

import com.atar.other.R;
import com.atar.plugin.library.activity.PluginAtarCommonActivity;

public class SecondActivity extends PluginAtarCommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_second);
        setActivityTitle("dfsfdsfs");
    }
}
