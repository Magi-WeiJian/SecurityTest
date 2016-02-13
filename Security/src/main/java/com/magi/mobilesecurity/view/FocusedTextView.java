package com.magi.mobilesecurity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wesgin on 2016/2/10.
 */
public class FocusedTextView extends TextView {

    //有style时调用的方法
    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //有属性时调用此方法
    public FocusedTextView(Context context) {
        super(context);
    }

    public FocusedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
     *表示有没有获取焦点
     *跑马灯运行需要焦点
     * 返回true让view有焦点
     */
    @Override
    public boolean isFocused() {
        return true;
    }
}
