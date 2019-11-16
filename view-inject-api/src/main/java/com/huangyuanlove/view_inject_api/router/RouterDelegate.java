package com.huangyuanlove.view_inject_api.router;

import java.lang.reflect.Method;

public class RouterDelegate {


    public static Object invoke(Method method, Object target, RouterParamWrapper paramWrapper) throws Exception{
       return  method.invoke(target,paramWrapper.getParamArray());
    }


}
