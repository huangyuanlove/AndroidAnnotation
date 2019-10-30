package com.huangyuanlove.view_inject_api;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-30
 * Email: huangyuan@chunyu.me
 */
public class ViewInjector {
    static final Map<Class<?>, Constructor> BINDINGS = new LinkedHashMap<>();

    public static void bind(Activity activity) {
        bind(activity, activity.getWindow().getDecorView());
    }

    public static void bind(Object target, View view) {
        Constructor constructor = findBindingConstructorForClass(target.getClass());
        try {
            constructor.newInstance(target, view);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Constructor findBindingConstructorForClass(Class<?> cls) {
        Constructor constructor = BINDINGS.get(cls);
        if (constructor == null) {
            try {
                Class<?> bindingClass = Class.forName(cls.getName() + "$ViewInjector");
                constructor = bindingClass.getDeclaredConstructor(cls, View.class);
                constructor.setAccessible(true);
                BINDINGS.put(cls, constructor);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return constructor;
    }
}
