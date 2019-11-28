package com.example.huangyuan.testandroid;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {


    private static Context applicationContext;


    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }





}
