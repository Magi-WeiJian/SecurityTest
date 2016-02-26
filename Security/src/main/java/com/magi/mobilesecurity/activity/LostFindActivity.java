package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.magi.mobilesecurity.R;
import com.magi.mobilesecurity.utils.MD5Utils;

import java.util.zip.Inflater;

public class LostFindActivity extends AppCompatActivity {

    private SharedPreferences mSpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpref = getSharedPreferences("config", MODE_PRIVATE);
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

    public void setLockPassword(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog setLockPasswordDialog = builder.create();
        View view1 = View.inflate(this, R.layout.dialog_set_password, null);
        Button btnOk = (Button) view1.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view1.findViewById(R.id.btn_cancel);
        final EditText etPassword = (EditText) view1.findViewById(R.id.et_password);
        final EditText etPasswordConfirm = (EditText) view1.findViewById(R.id.et_password_confirm);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String passwordConfirm = etPasswordConfirm.getText().toString();
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm)) {
                    if (password.equals(passwordConfirm)) {
                        Toast.makeText(LostFindActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        mSpref.edit().putString("LockPassword", password).apply();
                        setLockPasswordDialog.dismiss();
                    } else {
                        Toast.makeText(LostFindActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LostFindActivity.this, "输入框内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLockPasswordDialog.dismiss();
            }
        });

        setLockPasswordDialog.setView(view1);
        setLockPasswordDialog.show();
    }
}
