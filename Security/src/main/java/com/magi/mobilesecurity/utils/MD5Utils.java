package com.magi.mobilesecurity.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wesgin on 2016/2/12.
 * MD5加密密码工具
 */
public class MD5Utils {

    public static String encode(String password) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");  //获取MD5算法对象
            byte[] digest = instance.digest(password.getBytes());  //对字符串进行加密，返回字节数组
            StringBuffer buffer = new StringBuffer();
            for (byte b : digest) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);  //将整数转换成16进制
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                buffer.append(hexString);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
