package com.atar.other.activitys.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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
//                startActivity(new Intent(thisContext, SecondActivity.class));
                new AlertDialog.Builder(thisContext)
                        .setCancelable(false)
                        .setTitle("dfdd")
                        .setMessage("dfadffdfadfaf")
//                        .setOnKeyListener(new DialogInterface.OnKeyListener() {
//                            @Override
//                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                                return true;
//                            }
                        .setNegativeButton("AAA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("BBBB", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();
            }
        });
        setActivityTitle("056050161");
    }
}
