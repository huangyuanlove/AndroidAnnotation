package com.example.huangyuan.testandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.huangyuan.testandroid.R;
import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_annotation.BroadcastResponder;
import com.huangyuanlove.view_inject_annotation.ClickResponder;
import com.huangyuanlove.view_inject_api.ViewInjector;
public class BroadcastSenderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_sender);

        ViewInjector.bind(this);

    }


    @ClickResponder(id= R.id.send_normal_local_broadcast)
    public void sendNormalLocalBroadcast(View v){
        Intent intent = new Intent();
        intent.setAction(TestBroadcastActivity.NORMAL_LOCAL);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @ClickResponder(id= R.id.send_normal_global_broadcast)
    public void sendNormalGlobalBroadcast(View v){
        Intent intent = new Intent();
        intent.setAction(TestBroadcastActivity.NORMAL_GLOBAL);
        sendBroadcast(intent);
    }
    @ClickResponder(id= R.id.send_permisson_global_broadcast)
    public void sendPermissonGlobalBroadcast(View v){
        Intent intent = new Intent();
        intent.setAction(TestBroadcastActivity.PERMISSION_GLOBAL);
        sendBroadcast(intent,TestBroadcastActivity.BROADCAST_PERMISSION);
    }





}
