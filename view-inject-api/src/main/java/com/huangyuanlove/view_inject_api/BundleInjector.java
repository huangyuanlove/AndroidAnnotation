package com.huangyuanlove.view_inject_api;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-11-05
 * Email: huangyuan@chunyu.me
 */
public class BundleInjector {

    static final Map<Class<?>, Method> BUNDLES = new LinkedHashMap<>();

    public static void parseBundle(Activity activity) {
        try {
            findParseBundleForClass(activity.getClass()).invoke(activity,activity.getIntent());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void parseBundle(Fragment fragment) {
        try {
            findParseBundleForClass(fragment.getClass()).invoke(fragment,fragment.getArguments());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static Method findParseBundleForClass(Class<?> cls) {
        Method parseBundle = BUNDLES.get(cls);
        if (parseBundle == null) {
            try {
                Class<?> bindingClass = Class.forName(cls.getName() + "$ViewInjector");
                parseBundle = bindingClass.getMethod("parseBundle");

                BUNDLES.put(cls, parseBundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return parseBundle;
    }


}
