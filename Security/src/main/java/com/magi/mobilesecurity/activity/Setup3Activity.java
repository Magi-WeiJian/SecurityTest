package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.magi.mobilesecurity.R;

public class Setup3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }

    public void next(View view) {
        startActivity(new Intent(this, Setup4Activity.class));
        finish();
    }
    public void previous(View view) {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
    }
}
