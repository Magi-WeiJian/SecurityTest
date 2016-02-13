package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.magi.mobilesecurity.R;

public class LostFindActivity extends AppCompatActivity {

    private SharedPreferences mSprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSprefs = getSharedPreferences("config", MODE_PRIVATE);
        boolean configed = mSprefs.getBoolean("configed", false);//判断是否进行过向导设置
        if (configed) {
            setContentView(R.layout.activity_lost_find);
        } else {
            startActivity(new Intent(this, Setup1Activity.class));
            finish();
        }
    }
}
