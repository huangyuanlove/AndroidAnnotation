package com.huangyuanlove.view_inject_api;

public interface ViewInject<T>
{
    void inject(T t, Object source);
}

