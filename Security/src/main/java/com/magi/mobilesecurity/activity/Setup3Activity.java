package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.os.Bundle;

import com.magi.mobilesecurity.R;

public class Setup3Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this, Setup4Activity.class));
        finish();
        //两个界面切换的动画
        overridePendingTransition(R.transition.tran_in, R.transition.tran_out);
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
        overridePendingTransition(R.transition.tran_previous_in, R.transition.tran_previous_out);
    }
}
