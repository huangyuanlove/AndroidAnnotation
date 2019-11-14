package com.huangyuanlove.temp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.huangyuanlove.view_inject_annotation.ClickResponder;
import com.huangyuanlove.view_inject_annotation.IntentValue;
import com.huangyuanlove.view_inject_api.Router;

import java.util.HashMap;

public class EXT_MainActivity extends AppCompatActivity {

    @IntentValue(key = "ABC")
    protected String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ext__main);


    }


    @ClickResponder(idStr = "toAppMainActivity")
    public void toAppMainActivity(View v){

        HashMap<String,Object> params = new HashMap<>();
        Router.to("App://main/toMain").addParam(params).done(new Router.InvokeResultListene() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess() {

            }
        });

    }

}
