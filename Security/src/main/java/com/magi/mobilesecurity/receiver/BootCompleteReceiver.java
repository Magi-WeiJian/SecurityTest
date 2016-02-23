package com.magi.mobilesecurity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by wesgin on 2016/2/20.
 * 监听开机启动的广播
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean protect = config.getBoolean("protect", false);
        if (protect) {  //只有防盗保护开启的时候才做这些事情
            String simSerialNumber = config.getString("simSerialNumber", null);  //获取绑定的sim卡
            if (!TextUtils.isEmpty(simSerialNumber)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String currentSim = telephonyManager.getSimSerialNumber();  //拿到当前的sim卡
                if (currentSim.equals(simSerialNumber)) {
                    Log.i("TAG", "onReceive: 手机安全");
                } else {
//                    Log.i("TAG", "onReceive: sim改变，发送报警短信");
                    String phone = config.getString("safe_phone", "");  //读取安全号码
                    //发送安全短信
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, "sim crad changed!", null, null);
                }
            }
        }
    }
}
