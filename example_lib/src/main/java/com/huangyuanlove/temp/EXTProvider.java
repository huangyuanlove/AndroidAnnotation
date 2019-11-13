package com.huangyuanlove.temp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.huangyuanlove.view_inject_annotation.RouterModule;
import com.huangyuanlove.view_inject_annotation.RouterPath;

@RouterModule(host = "ExtLib",schema = "App")
public class EXTProvider {

    @RouterPath(value = "main_activity")
    public void startMain(Context context, Bundle bundle){
        Intent intent = new Intent(context,EXT_MainActivity.class);
       intent.putExtras(bundle);
       context.startActivity(intent);
    }


    public void testMethod(Context context){

    }
}
