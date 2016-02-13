package com.magi.mobilesecurity.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.magi.mobilesecurity.R;
import com.magi.mobilesecurity.utils.StreamUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_URL_REEOR = 1;
    private static final int CODE_JSON_REEOR = 2;
    private static final int CODE_NET_REEOR = 3;
    private static final int CODE_ENTER_HOME = 4;
    private static final int PACK_INSTALL = 5;
    private String mVersionName; //服务器版本名
    private int mVersionCode;  //服务器版本号
    private String mDesc;   //服务器更新描述
    private String mDownloadUrl;   //更新下载地址
    private TextView tvProgress;

    @SuppressLint("HandlerLeak")
    private Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_URL_REEOR:
                    Toast.makeText(SplashActivity.this, "url错误", Toast.LENGTH_LONG).show();
                    enterHome();
                    break;
                case CODE_NET_REEOR:
                    Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                    enterHome();
                    break;
                case CODE_JSON_REEOR:
                    Toast.makeText(SplashActivity.this, "JSON错误", Toast.LENGTH_LONG).show();
                    enterHome();
                    break;
                case CODE_ENTER_HOME:
                    enterHome();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("版本号:" + getVersionName());
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        getVersionName();
        SharedPreferences spref = getSharedPreferences("config", MODE_PRIVATE);
        boolean autoUdate = spref.getBoolean("auto_update", true);
        if (autoUdate){
            checkVersion();
        } else {
            handle.sendEmptyMessageDelayed(CODE_ENTER_HOME, 2000); //延迟2秒后发送消息
        }

        //设置渐变动画
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1f);
        animation.setDuration(2000);
        rlRoot.setAnimation(animation);
    }

    //获取版本名称
    private String getVersionName(){
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0); //拿到包的信息
            //int versionCode = packageInfo.versionCode;
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //没有找到包名的时候抛出异常
            e.printStackTrace();
        }
        return null;
    }

    //获取版本名称
    private int getVersionCode(){
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0); //拿到包的信息
            //int versionCode = packageInfo.versionCode;
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            //没有找到包名的时候抛出异常
            e.printStackTrace();
        }
        return -1;
    }

    //从服务器获取版本信息进行校验
    private void checkVersion(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                Message msg = Message.obtain();
                HttpURLConnection conn = null;
                try {
                    URL url = new URL("http://192.168.56.1:8080/update.json");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");  //设置请求方法
                    conn.setConnectTimeout(5000);  //设置连接超时
                    conn.setReadTimeout(5000);    //设置响应超时
                    conn.connect();   //连接

                    int responseCode = conn.getResponseCode();//获取响应码
                    if (responseCode == 200){
                        InputStream inputStream = conn.getInputStream();
                        String result = StreamUtils.readFromStream(inputStream);
                        //Log.i("TAG", "checkVersion: 网络返回" + result);
                        //解释json
                        JSONObject jsonObject = new JSONObject(result);
                        mVersionName = jsonObject.getString("versionName");
                        mVersionCode = jsonObject.getInt("versionCode");
                        mDesc = jsonObject.getString("description");
                        mDownloadUrl = jsonObject.getString("downloadUrl");
//                        Log.i("TAG", "run: " + mDownloadUrl);
                        if (mVersionCode > getVersionCode()){    //判断是否有更新
                            //如果有升级，则弹出升级对话框
                            msg.what = CODE_UPDATE_DIALOG;
                        }else {
                            msg.what = CODE_ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    msg.what = CODE_URL_REEOR;
                    e.printStackTrace();
                } catch (IOException e) {
                    msg.what = CODE_NET_REEOR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    msg.what = CODE_JSON_REEOR;
                    e.printStackTrace();
                }finally {
                    long endTime = System.currentTimeMillis();
                    long timeUsed = endTime - startTime;
                    if (timeUsed < 2000){
                        try {
                            Thread.sleep(2000-timeUsed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handle.sendMessage(msg);
                    if (conn != null){
                        conn.disconnect(); //关闭网络连接
                    }
                }
            }
        };
        thread.start();
    }

    //升级对话框
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本：" + mVersionName);
        builder.setMessage(mDesc);
        //builder.setCancelable(false); //不让去取消对话框
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                download();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        //设置取消监听
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }

    /*
     *进入主界面
     */
    private void enterHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void download(){
        //框架XUtils
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            tvProgress.setVisibility(View.VISIBLE); //显示进度
            HttpUtils utils = new HttpUtils();
            String target = Environment.getExternalStorageDirectory() + "/update.apk";
            utils.download(mDownloadUrl, target, new RequestCallBack<File>() {

                //文件下载进度
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);

                    tvProgress.setText(getString(R.string.tv_progress) + current*100/total + "%");
                }

                //下载成功
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Toast.makeText(SplashActivity.this, "下载成功", Toast.LENGTH_LONG).show();
                    //跳到系统下载页面
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                    //startActivity(intent);
                    startActivityForResult(intent, PACK_INSTALL);
                }

                //下载失败
                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(SplashActivity.this, "没有找到SDcard", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PACK_INSTALL){
            enterHome();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}