package com.magi.mobilesecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.magi.mobilesecurity.R;
import com.magi.mobilesecurity.utils.ToastUtils;

public class Setup3Activity extends BaseSetupActivity {

    private EditText et_phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        et_phoneNumber = (EditText) findViewById(R.id.et_phonenumber);
        String phone = mSpref.getString("safe_phone", "");
        et_phoneNumber.setText(phone);
    }

    public void choose_contact(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void showNextPage() {
        String phone = et_phoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
//            Toast.makeText(this, "安全号码不能为空", Toast.LENGTH_SHORT).show();
            ToastUtils.showToast(this, "安全号码不能为空");
            return ;
        }
        mSpref.edit().putString("safe_phone", phone).apply();  //保存安全号码
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            String phone = data.getStringExtra("phone");
            phone = phone.replaceAll("-", "").replaceAll(" ", ""); //替换-和空格
            et_phoneNumber.setText(phone);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
