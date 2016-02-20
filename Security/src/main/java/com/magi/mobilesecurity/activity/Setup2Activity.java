package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.magi.mobilesecurity.R;
import com.magi.mobilesecurity.view.SettingItemView;


/**
 * 向导页2
 */
public class Setup2Activity extends BaseSetupActivity {

    private SettingItemView sivSim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        sivSim = (SettingItemView) findViewById(R.id.siv_sim);
        String simSerialNumber = mSpref.getString("simSerialNumber", null);
        if (!TextUtils.isEmpty(simSerialNumber)) {
            sivSim.setChecked(true);
        } else {
            sivSim.setChecked(false);
        }
        sivSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sivSim.isChecked()) {
                    sivSim.setChecked(false);
                    mSpref.edit().remove("simSerialNumber").apply();  //删除绑定的sim卡
                } else {
                    sivSim.setChecked(true);
                    //保存sim卡信息
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    String simSerialNumber = telephonyManager.getSimSerialNumber();  //获取sim卡序列号
//                    Log.i("TAG", "onClick: sim卡序列号：" + simSerialNumber);
                    mSpref.edit().putString("simSerialNumber", simSerialNumber).apply(); //将sim卡序列号存储在sharePref中
                }
            }
        });
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
