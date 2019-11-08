package com.huangyuanlove.view_inject_api;


import android.app.Activity;
import android.content.BroadcastReceiver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BroadcastInject {

    static final Map<Class<?>, Method> BROADCAST_MAP = new LinkedHashMap<>();

    public static HashMap<Integer,BroadcastReceiver> registerReceiver(Activity activity) {
        try {
            Method method = findRegisterReceiverMethodForClass(activity.getClass());
            return (HashMap<Integer,BroadcastReceiver>)method.invoke(null,activity);
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
