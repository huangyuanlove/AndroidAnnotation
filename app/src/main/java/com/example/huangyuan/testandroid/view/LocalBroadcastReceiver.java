package com.example.huangyuan.testandroid.view;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LocalBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"收到广播"+ intent.getAction() ,Toast.LENGTH_SHORT).show();

    }
}
