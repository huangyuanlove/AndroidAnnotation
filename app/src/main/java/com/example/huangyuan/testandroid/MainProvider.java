package com.example.huangyuan.testandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.huangyuan.testandroid.view.MainActivity;
import com.huangyuanlove.view_inject_annotation.RouterModule;

@RouterModule(schema = "App",host = "main")
public class MainProvider {

    public void startMain(Context context, Bundle bundle){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
