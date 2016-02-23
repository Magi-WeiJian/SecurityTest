package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.magi.mobilesecurity.R;

public class Setup4Activity extends BaseSetupActivity {

    private CheckBox cbProtect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        cbProtect = (CheckBox) findViewById(R.id.cb_protect);
        boolean protect = mSpref.getBoolean("protect", false);
        if (protect) {
            cbProtect.setText("防盗保护已经开启");
            cbProtect.setChecked(true);
        } else {
            cbProtect.setText("防盗保护没有开启");
            cbProtect.setChecked(false);
        }
        //监听checkbox的变化
        cbProtect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbProtect.setText("防盗保护已经开启");
                    mSpref.edit().putBoolean("protect", true).apply();
                } else {
                    cbProtect.setText("防盗保护没有开启");
                    mSpref.edit().putBoolean("protect", false).apply();
                }
            }
        });
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this, LostFindActivity.class));
        finish();
        //两个界面切换的动画
        overridePendingTransition(R.transition.tran_in, R.transition.tran_out);
        mSpref.edit().putBoolean("configed", true).apply();
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.transition.tran_previous_in, R.transition.tran_previous_out);
    }
}
