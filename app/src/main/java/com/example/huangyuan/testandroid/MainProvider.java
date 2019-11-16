package com.example.huangyuan.testandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.huangyuan.testandroid.view.MainActivity;
import com.huangyuanlove.view_inject_annotation.RouterModule;
import com.huangyuanlove.view_inject_annotation.RouterPath;

@RouterModule(schema = "App",host = "main")
public class MainProvider {

    @RouterPath(value = "/toMain")
    public void startMain(Activity context, int id){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @RouterPath(value = "/toMainWithResult")
    public void startMain(Activity context, String title,int requestCode){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }

    @RouterPath(value = "/getInt")
    public int getIntValue(String s){
        return s.length();
    }

}
