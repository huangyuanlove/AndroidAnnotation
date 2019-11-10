package com.huangyuanlove.temp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.huangyuanlove.view_inject_annotation.RouterModule;
import com.huangyuanlove.view_inject_annotation.RouterPath;

@RouterModule(host = "ext_lib",schema = "App")
public class EXTProviderOne {

    @RouterPath(value = "main_activity_one")
    public void startMain(Context context, Bundle bundle){
        Intent intent = new Intent(context,EXT_MainActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
