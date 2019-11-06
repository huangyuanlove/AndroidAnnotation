package com.huangyuanlove.view_inject_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface IntentValue {

    int DEFAULT_TYPE = -1;
    int PARCELABLE_OBJECT = 1;
    int PARCELABLE_ARRAY_OBJECT = 2;
    int PARCELABLE_ARRAYLIST_OBJECT = 3;
    int SERIALIZABLE_OBJECT = 4;


    String key();
    int type() default DEFAULT_TYPE;
}
