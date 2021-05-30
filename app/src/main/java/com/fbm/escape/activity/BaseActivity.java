package com.fbm.escape.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.fbm.escape.utils.ScreenSize;


/**
 * 2021-05-09
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setFullScreen();
        ScreenSize.obtainScreenSize(this);
    }

    private void setFullScreen() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//布局隐藏导航
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//布局全屏
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航
            | View.SYSTEM_UI_FLAG_FULLSCREEN //全屏
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;//沉浸式系统;
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.i(TAG, "onWindowFocusChanged");
            setFullScreen();
        }
    }
}