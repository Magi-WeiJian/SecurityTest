package com.magi.mobilesecurity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.magi.mobilesecurity.R;

/**
 * Created by wesgin on 2016/2/24.
 * 拦截短信
 */
public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] objects = (Object[]) intent.getExtras().get("pdus");
        for (Object puds : objects) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) puds);
            String originatingAddress = message.getDisplayOriginatingAddress(); //短信来源号码
            String messageBody = message.getDisplayMessageBody(); //短信内容

            //Log.i("TAG", "onReceive: " + originatingAddress + ":" + messageBody);
            if ("#*location*#".equals(messageBody)) {
                //播放报警音乐
                MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                player.setVolume(1f, 1f);
                player.setLooping(true);
                player.start();
                abortBroadcast();  //中断短信的传递，从而系统app收不到短信
            }
        }
    }
}
