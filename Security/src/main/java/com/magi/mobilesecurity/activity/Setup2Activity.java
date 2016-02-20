package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.os.Bundle;

import com.magi.mobilesecurity.R;


/**
 * 向导页2
 */
public class Setup2Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        //两个界面切换的动画
        overridePendingTransition(R.transition.tran_in, R.transition.tran_out);
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
        overridePendingTransition(R.transition.tran_previous_in, R.transition.tran_previous_out);
    }

}
