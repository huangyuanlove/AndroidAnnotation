package com.huangyuanlove.view_inject_api;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-30
 * Email: huangyuan@chunyu.me
 */
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
}
