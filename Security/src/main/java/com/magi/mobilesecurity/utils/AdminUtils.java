package com.magi.mobilesecurity.utils;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.magi.mobilesecurity.receiver.AdminReceiver;

/**
 * Created by wesgin on 2016/2/26.
 * 超级设备管理器工具类
 */
public class AdminUtils {

    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdminSample;

    public AdminUtils(Context context) {
        mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);// 获取设备策略服务
        mDeviceAdminSample = new ComponentName(context, AdminReceiver.class);// 设备管理组件
    }

    public void lockScreen(Context context) {
        if (mDPM.isAdminActive(mDeviceAdminSample)) {// 判断设备管理器是否已经激活
            SharedPreferences config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
            String lockPassword = config.getString("LockPassword", "");
            mDPM.resetPassword(lockPassword, 0);
            mDPM.lockNow();// 立即锁屏
        } else {
            activeAdmin(context);
        }
    }

    public void clearData(Context context) {
        if (mDPM.isAdminActive(mDeviceAdminSample)) {// 判断设备管理器是否已经激活
            mDPM.wipeData(0);// 清除数据,恢复出厂设置
        } else {
            activeAdmin(context);
        }
    }

    public void activeAdmin(Context context) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "哈哈哈, 我们有了手机卫士管理器, 好NB!");
        context.startActivity(intent);
    }
}
