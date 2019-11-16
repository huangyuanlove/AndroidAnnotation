package com.huangyuanlove.view_inject_api.router;

public class RouterParamWrapper {

    private Object []paramArray;

    public RouterParamWrapper(Object [] paramArray) {
        this.paramArray = paramArray;
    }


    public Object[] getParamArray() {
        return paramArray;
    }

    public Class[] getParamArrayClass(){
        if(paramArray==null || paramArray.length == 0){
            return null;
        }
        Class[] clazz = new Class[paramArray.length];
        for (int i = 0; i < paramArray.length; i++) {
            clazz[i] = paramArray[i].getClass();
        }
        return clazz;

    }


    public void setParamArray(Object[] paramArray) {
        this.paramArray = paramArray;
    }
}
