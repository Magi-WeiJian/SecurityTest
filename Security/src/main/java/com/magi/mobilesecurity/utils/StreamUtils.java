package com.magi.mobilesecurity.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wesgin on 2016/2/8.
 * 读取流的工具
 */
public class StreamUtils {

    /*
     *将流读取成String返回
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, len);
        }
        String result = outputStream.toString();
        inputStream.close();
        outputStream.close();
        return result;
    }
}
