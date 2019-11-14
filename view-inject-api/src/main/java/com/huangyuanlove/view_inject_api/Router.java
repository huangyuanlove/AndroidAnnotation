package com.huangyuanlove.view_inject_api;

import android.net.Uri;

import java.util.Map;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-11-14
 * Email: huangyuan@chunyu.me
 */
public class Router {


    public static  interface InvokeResultListene{
        void  onError(Exception e);
        void onSuccess();

    }



    private  static final String PACKAGE_NAME = "com.huangyuanlove.router";


    public static RouterBuilder to(String path){
        return new RouterBuilder(path);
    }




    public static class RouterBuilder{
        private String path;
        private Map<String,Object> paramMap;
        private Object[] paramArray;
        private InvokeResultListene listene;

        private RouterBuilder(String path) {
            this.path = path;
        }

        public  RouterBuilder  addParam(Map<String,Object> paramMap){
            this.paramMap = paramMap;
            return this;
        }

        public RouterBuilder addParam(Object ... paramArray){
            this. paramArray = paramArray;
            return  this;

        }


        public void done(){
            done(null);
        }

        public void done(InvokeResultListene listene){

            try {
                Uri uri  = Uri.parse(path);
                String schema = uri.getScheme();
                String host = uri.getHost();

                Class routerInject = Class.forName(PACKAGE_NAME +"." + schema + host +"$$Router");



            }catch (Exception e){

                if(listene!=null){
                    listene.onError(e);
                }

            }











        }


    }

}
