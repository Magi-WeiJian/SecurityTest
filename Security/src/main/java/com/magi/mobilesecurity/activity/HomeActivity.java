package com.magi.mobilesecurity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.magi.mobilesecurity.R;
import com.magi.mobilesecurity.utils.MD5Utils;

public class HomeActivity extends AppCompatActivity {

    private final int SAFE = 0, CALLMAGSAFE = 1, APPS = 2, TASKMANAGER = 3, NETMANAGER = 4, TROJAN = 5, SYSOPTIMIZE = 6, TOOLS = 7, SETTINGS = 8;
    private String[] mTvIcon = new String[]{"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量监控", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
    private int[] mPics = new int[]{R.mipmap.home_safe, R.mipmap.home_callmsgsafe, R.mipmap.home_apps,
            R.mipmap.home_taskmanager, R.mipmap.home_netmanager, R.mipmap.home_trojan,
            R.mipmap.home_sysoptimize, R.mipmap.home_tools, R.mipmap.home_settings};
    private SharedPreferences spref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GridView gvHome = (GridView) findViewById(R.id.gv_home);
        spref = getSharedPreferences("config", MODE_PRIVATE);
        gvHome.setAdapter(new HomeAdapter());
        //设置按钮监听
        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case SAFE:
                        showPasswordDialog();
                        break;
                    case SETTINGS:
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        break;
                }
            }
        });
    }

    //显示密码弹窗
    private void showPasswordDialog() {
        //判断是否设置密码， 如果没有设置过，弹出密码的弹窗
        String savedPassword = spref.getString("password", null);
        if (!TextUtils.isEmpty(savedPassword)) {
            showPasswordInputDialog();
        } else {
            showPasswordSetDialog();
        }
    }

    //输入密码的dialog
    private void showPasswordInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog inputDialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_input_password, null);
        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String savedPassword = spref.getString("password", null);
                if (MD5Utils.encode(password).equals(savedPassword)) {
                    //Toast.makeText(HomeActivity.this, "验证成功", Toast.LENGTH_LONG).show();
                    inputDialog.dismiss();
                    startActivity(new Intent(HomeActivity.this, LostFindActivity.class));
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();
            }
        });
        inputDialog.setView(view);
        inputDialog.show();
    }

    private void showPasswordSetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog SetDialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_set_password, null);
        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        final EditText etPasswordConfirm = (EditText) view.findViewById(R.id.et_password_confirm);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String passwordConfirm = etPasswordConfirm.getText().toString();
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm)) {
                    if (password.equals(passwordConfirm)) {
                        Toast.makeText(HomeActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        spref.edit().putString("password", MD5Utils.encode(password)).apply();
                        SetDialog.dismiss();
                        startActivity(new Intent(HomeActivity.this, LostFindActivity.class));
                    } else {
                        Toast.makeText(HomeActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "输入框内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDialog.dismiss(); //关闭dialong
            }
        });

        SetDialog.setView(view);
        SetDialog.show();
    }

    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTvIcon.length;
        }

        @Override
        public Object getItem(int position) {
            return mTvIcon[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View view = convertView;
            if (view == null){
                view = View.inflate(HomeActivity.this, R.layout.home_list_item, null);
                holder = new ViewHolder();
                holder.iv_home_icon = (ImageView) view.findViewById(R.id.iv_home_icon);
                holder.tv_home_icon = (TextView) view.findViewById(R.id.tv_home_icon);
                view.setTag(holder);
            }else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tv_home_icon.setText(mTvIcon[position]);
            holder.iv_home_icon.setImageResource(mPics[position]);
            return view;
        }
    }

    static class ViewHolder{
        ImageView iv_home_icon;
        TextView tv_home_icon;
    }
}
