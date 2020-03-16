package com.example.huangyuan.testandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_annotation.BroadcastResponder;
import com.huangyuanlove.view_inject_annotation.ClickResponder;
import com.huangyuanlove.view_inject_api.ViewInjector;

import java.util.ArrayList;
import java.util.HashMap;

public class TestBroadcastActivity extends AppCompatActivity {

    public static final String NORMAL_LOCAL = "normal_local";
    public static final String NORMAL_GLOBAL = "normal_global";
    public static final String PERMISSION_GLOBAL = "permission_global";

    public static final String BROADCAST_PERMISSION = "com.huangyuanlove.permission_broadcast";

    @BindView(id = R.id.show_action)
    TextView showAction;
    HashMap<Integer, ArrayList<BroadcastReceiver>> integerArrayListHashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_broadcast);
        ViewInjector.bind(this);
        integerArrayListHashMap = ViewInjector.registerReceiver(this);
    }

    @ClickResponder(id = R.id.open_sender)
    void openSender(View v) {
        startActivity(new Intent(this, BroadcastSenderActivity.class));

    }

    @BroadcastResponder(action = NORMAL_LOCAL)
    void onReceiveNormalLocalBroadcast(Context context, Intent intent) {
        showAction.setText("onReceiveNormalLocalBroadcast:"+intent.getAction());
    }

    @BroadcastResponder(action = NORMAL_GLOBAL,type = BroadcastResponder.GLOBAL_BROADCAST)
    void onReceiveNormalGlobalBroadcast(Context context, Intent intent) {
        showAction.setText("onReceiveNormalGlobalBroadcast:"+intent.getAction());
    }

    @BroadcastResponder(action = PERMISSION_GLOBAL,permission = BROADCAST_PERMISSION,type = BroadcastResponder.GLOBAL_BROADCAST)
    void onReceivePermissionGlobalBroadcast(Context context, Intent intent) {
        showAction.setText("onReceivePermissionGlobalBroadcast"+intent.getAction());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(integerArrayListHashMap!=null){
            ArrayList<BroadcastReceiver> localReceiverList = integerArrayListHashMap.get(BroadcastResponder.LOCAL_BROADCAST);
            if(localReceiverList!=null && localReceiverList.size()>0){
                for(BroadcastReceiver receiver : localReceiverList){
                    LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
                }
            }
            ArrayList<BroadcastReceiver> globalReceiverList = integerArrayListHashMap.get(BroadcastResponder.GLOBAL_BROADCAST);

            if(globalReceiverList!=null && globalReceiverList.size()>0){
                for(BroadcastReceiver receiver : globalReceiverList){
                   unregisterReceiver(receiver);
                }
            }

        }
    }
}
