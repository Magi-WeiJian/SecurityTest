package com.magi.mobilesecurity.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wesgin on 2016/2/20.
 * 引导页的基类
 */
public abstract class BaseSetupActivity extends AppCompatActivity {

    public SharedPreferences mSpref;
    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpref = getSharedPreferences("config", MODE_PRIVATE);
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            //监听手势滑动事件

            /**
             *
             * @param e1 滑动起点
             * @param e2 滑动终点
             * @param velocityX 水平速度
             * @param velocityY 垂直速度
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                //判断纵向滑动幅度是否过大
                if (Math.abs(e2.getRawY() - e1.getRawY()) > 100){
                    return true;
                }
                //判断滑动的速度，太慢的不可以
                if (Math.abs(velocityX) < 100) {
                    return true;
                }
                //向右滑，上一页
                if (e2.getRawX() - e1.getRawX() > 200) {
                    showPreviousPage();
                    return true;
                }
                //向左滑，下一页
                if (e1.getRawX() - e2.getRawX() > 200) {
                    showNextPage();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    public abstract void showNextPage();

    public abstract void showPreviousPage();

    public void next(View view) {
        showNextPage();
    }

    public void previous(View view) {
        showPreviousPage();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);  //委托手势识别器处理event触摸事件
        return super.onTouchEvent(event);
    }
}
