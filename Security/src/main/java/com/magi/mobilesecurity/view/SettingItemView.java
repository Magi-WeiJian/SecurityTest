package com.magi.mobilesecurity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.magi.mobilesecurity.R;

/**
 * Created by wesgin on 2016/2/10.
 */
public class SettingItemView extends RelativeLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private CheckBox cbStatus;
    private TextView tvDesc;
    private TextView tvTitle;
    private String mTitle;
    private String mDescOn;
    private String mDescOff;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTitle = attrs.getAttributeValue(NAMESPACE, "Setting_title");   //根据命名空间拿到属性的值
        mDescOn = attrs.getAttributeValue(NAMESPACE, "Setting_desc_on");
        mDescOff = attrs.getAttributeValue(NAMESPACE, "Setting_desc_off");
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化布局
    private void initView(){
        //将指定好的布局文件设置给当前的SettingItemView
        View.inflate(getContext(), R.layout.view_setting_item, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        cbStatus = (CheckBox) findViewById(R.id.cb_status);
        setTitle(mTitle);
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setDesc(String desc){
        tvDesc.setText(desc);
    }

    //返回勾选状态
    public boolean isChecked(){
        return cbStatus.isChecked();
    }

    public void setChecked(boolean checked){
        cbStatus.setChecked(checked);
        if (checked) {
            setDesc(mDescOn);
        } else {
            setDesc(mDescOff);
        }
    }
}
