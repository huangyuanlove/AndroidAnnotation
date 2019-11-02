package com.huangyuanlove.view_inject_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface LongClickResponder {
    int[] id() default {};
    String[] idStr() default {""};

}
