package com.huangyuanlove.view_inject_api.router;

public class RouterParamWrapper {

    private Object []paramArray;

    public RouterParamWrapper(Object [] paramArray) {
        this.paramArray = paramArray;
    }


    public Object[] getParamArray() {
        return paramArray;
    }

}
