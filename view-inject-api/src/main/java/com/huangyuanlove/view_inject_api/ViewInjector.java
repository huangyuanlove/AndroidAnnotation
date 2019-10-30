package com.huangyuanlove.view_inject_api;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

public class ViewInjector {
    private static final String SUFFIX = "$$ViewInject";

    public static void injectView(Activity activity) {
        ViewInject proxyActivity = findProxyActivity(activity);
        proxyActivity.inject(activity);
    }


    public static void injectView(Fragment fragment,View view) {
        ViewInject proxyActivity = findProxyActivity(fragment);
        proxyActivity.inject(view);
    }

    public static void injectView(View view) {
        ViewInject proxyActivity = findProxyActivity(view);
        proxyActivity.inject(view);
    }

    private static ViewInject findProxyActivity(Object activity) {
        try {
            Class clazz = activity.getClass();
            Class injectorClazz = Class.forName(clazz.getName() + SUFFIX);
            return (ViewInject) injectorClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format("can not find %s , something when compiler.", activity.getClass().getSimpleName() + SUFFIX));
    }
}
