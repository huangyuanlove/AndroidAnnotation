package com.huangyuanlove.view_inject_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface BroadcastResponder {

    int LOCAL_BROADCAST = 1;
    int GLOBAL_BROADCAST = 2;

    String[] action();
    int type() default LOCAL_BROADCAST;
}
