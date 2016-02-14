package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.magi.mobilesecurity.R;

public class Setup4Activity extends AppCompatActivity {

    private SharedPreferences mSpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        mSpref = getSharedPreferences("config", MODE_PRIVATE);
    }

    public void next(View view) {
        startActivity(new Intent(this, LostFindActivity.class));
        finish();
        overridePendingTransition(R.transition.tran_in, R.transition.tran_out);
        mSpref.edit().putBoolean("configed", true).apply();
    }
    public void previous(View view) {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.transition.tran_previous_in, R.transition.tran_previous_out);
    }
}
