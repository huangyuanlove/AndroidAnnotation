package com.huangyuanlove.view_inject_api;

import android.net.Uri;

import com.huangyuanlove.view_inject_api.router.RouterParamWrapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;


public class Router {

    public  interface  InvokeResultListener<T>{
        void  onError(Exception e);
        void onSuccess( T result);
    }

    private  static final String PACKAGE_NAME = "com.huangyuanlove.router";

    public static RouterBuilder to(String path){
        return new RouterBuilder(path);
    }

    public static class RouterBuilder{
        private String path;
        private Object[] paramArray;

        private RouterBuilder(String path) {
            this.path = path;
        }

        public RouterBuilder addParam(Object ... paramArray){
            this. paramArray = paramArray;
            return  this;

        }

        public void done(){
            done(null);
        }

        public void done(InvokeResultListener listener){

            try {
                Uri uri  = Uri.parse(path);
                String schema = uri.getScheme();
                String host = uri.getHost();
                String path = uri.getPath();

                Class routerInject = Class.forName(PACKAGE_NAME +"." + schema + host +"$$Router");
                Constructor constructor =  routerInject.getConstructor();
                constructor.setAccessible(true);
                RouterParamWrapper paramWrapper = new RouterParamWrapper(paramArray);

                Method invokeMethod = routerInject.getMethod("invoke",String.class,RouterParamWrapper.class);
                invokeMethod.setAccessible(true);
                Object result = invokeMethod.invoke(constructor.newInstance(),path,paramWrapper);
                if(listener!=null){
                    listener.onSuccess(result);
                }
            }catch (Exception e){

                if(listener!=null){
                    listener.onError(e);
                }
            }
        }
    }
}
