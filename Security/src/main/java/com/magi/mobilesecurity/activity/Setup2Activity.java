package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.magi.mobilesecurity.R;

public class Setup2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
    }

    public void next(View view) {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.transition.tran_in, R.transition.tran_out);
    }
    public void previous(View view) {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
        overridePendingTransition(R.transition.tran_previous_in, R.transition.tran_previous_out);
    }
}
