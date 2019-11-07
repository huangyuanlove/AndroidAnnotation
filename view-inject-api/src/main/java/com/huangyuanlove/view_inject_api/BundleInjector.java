package com.huangyuanlove.view_inject_api;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;


public class BundleInjector {

    static final Map<Class<?>, Method> BUNDLES = new LinkedHashMap<>();

    public static void parseBundle(Activity activity) {
        try {
            Method method = findParseBundleMethodForClass(activity.getClass(),activity.getClass());
            method.invoke(null,activity,activity.getIntent()==null?null:activity.getIntent().getExtras());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void parseBundle(Fragment fragment) {
        try {
            Method method =findParseBundleMethodForClass(fragment.getClass(),fragment.getClass());
            method .invoke(null,fragment,fragment.getArguments());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static Method findParseBundleMethodForClass(Class<?> cls,Class clazz) {
        Method parseBundle = BUNDLES.get(cls);
        if (parseBundle == null) {
            try {
                Class<?> bindingClass = Class.forName(cls.getName() + "$ViewInjector");
                parseBundle=   bindingClass.getDeclaredMethod("parseBundle",clazz, Bundle.class);
                BUNDLES.put(cls, parseBundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return parseBundle;
    }


}
