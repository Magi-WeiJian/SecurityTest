package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.magi.mobilesecurity.R;

public class LostFindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mSpref = getSharedPreferences("config", MODE_PRIVATE);
        boolean configed = mSpref.getBoolean("configed", false);//判断是否进行过向导设置
        if (configed) {
            setContentView(R.layout.activity_lost_find);
            TextView tvSafePhone = (TextView) findViewById(R.id.tv_safephone);
            String safe_phone = mSpref.getString("safe_phone", "");
            tvSafePhone.setText(safe_phone);
            ImageView ivProtect = (ImageView) findViewById(R.id.iv_protect);
            boolean protect = mSpref.getBoolean("protect", false);
            if (protect) {
                ivProtect.setImageResource(R.mipmap.lock);
            } else {
                ivProtect.setImageResource(R.mipmap.unlock);
            }
        } else {
            startActivity(new Intent(this, Setup1Activity.class));
            finish();
        }
    }

    //重新进入设置向导
    public void reEnter(View view) {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
    }
}
