package com.magi.mobilesecurity.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wesgin on 2016/2/21.
 *
 */
public class ToastUtils {
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
