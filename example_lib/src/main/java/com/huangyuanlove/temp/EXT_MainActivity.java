package com.huangyuanlove.temp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.huangyuanlove.view_inject_annotation.ClickResponder;
import com.huangyuanlove.view_inject_annotation.IntentValue;
import com.huangyuanlove.view_inject_api.Router;
import com.huangyuanlove.view_inject_api.ViewInjector;

import java.util.HashMap;

public class EXT_MainActivity extends AppCompatActivity {

    @IntentValue(key = "ABC")
    protected String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ext__main);
        ViewInjector.bind(this);


    }


    @ClickResponder(idStr = "toAppMainActivity")
    public void toAppMainActivity(View v){

        Router.to("App://main/toMain").addParam(this,123).done(new Router.InvokeResultListener() {
            @Override
            public void onError(Exception e) {
                Toast.makeText(EXT_MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(Object o) {

            }
        });

    }

    @ClickResponder(idStr = "invoke_main_method")
    public void invokeMainMethod(View v){

        Router.to("App://main/getInt").addParam("12345678").done(new Router.InvokeResultListener<Integer>() {
            @Override
            public void onError(Exception e) {
                Toast.makeText(EXT_MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Integer result) {
                Toast.makeText(EXT_MainActivity.this,result+"",Toast.LENGTH_SHORT).show();

            }
        });

    }

}
