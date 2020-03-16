package com.huangyuanlove.view_inject_api;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ViewInjector {
    static final Map<Class<?>, Method> BINDINGS = new LinkedHashMap<>();

    public static void bind(Activity activity) {
        bind(activity, activity.getWindow().getDecorView());
    }

    public static void bind(Object target, View view) {
        Method constructor = findBindMethodForClass(target);
        try {
            constructor.invoke(null,target, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Method findBindMethodForClass(Object target) {
        Method constructor = BINDINGS.get(target.getClass());
        if (constructor == null) {
            try {
                Class<?> bindingClass = Class.forName(target.getClass().getName() + "$ViewInjector");
                constructor = bindingClass.getMethod("bind",target.getClass(), View.class);
                BINDINGS.put(target.getClass(), constructor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return constructor;
    }


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


    static final Map<Class<?>, Method> URIS = new LinkedHashMap<>();

    public static void parseUri(Activity activity) {
        try {
            Method method = findParseUriMethodForClass(activity.getClass(),activity.getClass());
            method.invoke(null,activity,activity.getIntent()==null?null:activity.getIntent().getData());
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private static Method findParseUriMethodForClass(Class<?> cls,Class clazz) {
        Method parseUri = URIS.get(cls);
        if (parseUri == null) {
            try {
                Class<?> bindingClass = Class.forName(cls.getName() + "$ViewInjector");
                parseUri=  bindingClass.getDeclaredMethod("parseUri",clazz, Uri.class);
                URIS.put(cls, parseUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return parseUri;
    }


    static final Map<Class<?>, Method> BROADCAST_MAP = new LinkedHashMap<>();

    public static HashMap<Integer, ArrayList<BroadcastReceiver>> registerReceiver(Activity activity) {
        try {
            Method method = findRegisterReceiverMethodForClass(activity.getClass());
            return (HashMap<Integer, ArrayList<BroadcastReceiver>>)method.invoke(null,activity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;


    }


    private static Method findRegisterReceiverMethodForClass(Class<?> cls) {
        Method registerReceiver = BROADCAST_MAP.get(cls);
        if (registerReceiver == null) {
            try {
                Class<?> bindingClass = Class.forName(cls.getName() + "$ViewInjector");
                registerReceiver=   bindingClass.getDeclaredMethod("registerReceiver",cls);
                BROADCAST_MAP.put(cls, registerReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return registerReceiver;
    }

}
