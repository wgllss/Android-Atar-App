package com.atar.other.activitys.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atar.other.R;
import com.atar.plugin.library.activity.PluginAtarCommonActivity;

public class MainActivity extends PluginAtarCommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_main);
        findViewById(R.id.txt_onclick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(thisContext, SecondActivity.class));
            }
        });
        setActivityTitle("056050161");
    }
}
