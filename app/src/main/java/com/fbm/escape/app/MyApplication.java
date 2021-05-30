package com.fbm.escape.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static   Context context;

    private MyApplication(){
        context=this;
    }

    public static Context getContext(){
        return context;
    }
}
