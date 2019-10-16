package com.example.huangyuan.testandroid;

import android.app.Application;
import android.content.Context;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2018/7/30.
 * Job number: 1800829
 * Phone: 13120112517
 * Email: huangyuan@chunyu.me
 */
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
