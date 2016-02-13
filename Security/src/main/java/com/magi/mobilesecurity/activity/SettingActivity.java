package com.magi.mobilesecurity.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.magi.mobilesecurity.R;
import com.magi.mobilesecurity.view.SettingItemView;

public class SettingActivity extends AppCompatActivity {

    private SettingItemView sivUpdate;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mPref = getSharedPreferences("config", MODE_PRIVATE);  //创建ShredPreferences用来记录setting是否启用或者关闭
        sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
        //sivUpdate.setTitle("自动更新设置");
        boolean autoUpdate = mPref.getBoolean("auto_update", true);
        if (autoUpdate){
            //sivUpdate.setDesc("自动更新已开启");
            sivUpdate.setChecked(true);
        } else {
            //sivUpdate.setDesc("自动更新已关闭");
            sivUpdate.setChecked(false);
        }
        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前勾选状态
                if (sivUpdate.isChecked()){
                    sivUpdate.setChecked(false);
                 //   sivUpdate.setDesc("自动更新已关闭");
                    mPref.edit().putBoolean("auto_update", false).apply();
                } else {
                    sivUpdate.setChecked(true);
                 //   sivUpdate.setDesc("自动更新已开启");
                    mPref.edit().putBoolean("auto_update", true).apply();
                }
            }
        });
    }
}
