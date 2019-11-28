package com.example.huangyuan.testandroid.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.base.ParcelableObject;
import com.huangyuanlove.base.UnParcelableObject;
import com.huangyuanlove.base.Util;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_annotation.BroadcastResponder;
import com.huangyuanlove.view_inject_annotation.ClickResponder;
import com.huangyuanlove.view_inject_annotation.LongClickResponder;
import com.huangyuanlove.view_inject_api.ViewInjector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestViewInjectActivity extends AppCompatActivity {

    @BindView(id = R.id.test_view_inject_one)
    protected Button buttonOne;
    @BindView(idStr = "test_view_inject_two")
    protected Button buttonTwo;
    @BindView(id = R.id.list_view)
    protected ListView listView;

    HashMap<Integer, BroadcastReceiver> broadcastReceiverHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_inject);
        ViewInjector.bind(this);
        broadcastReceiverHashMap = ViewInjector.registerReceiver(this);


        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("item-->" + i);
        }

        ListViewAdapter adapter = new ListViewAdapter(data, this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();






    }

    @BroadcastResponder(action = {"com.huangyuanblog","com.huangyuanblog.www"})
    public void onReceiveBroadcast(Context context, Intent intent){
        Toast.makeText(context,intent.getAction(),Toast.LENGTH_SHORT).show();
    }


    @BroadcastResponder(action = {"com.huangyuanlove",Intent.ACTION_AIRPLANE_MODE_CHANGED},type = BroadcastResponder.GLOBAL_BROADCAST)
    public void onReceiveBroadcastOther(Context context, Intent intent){
        Toast.makeText(context,intent.getAction(),Toast.LENGTH_SHORT).show();
    }






    @ClickResponder(id = R.id.to_fragment)
    public void toFragment(View v){

        startActivity(new Intent(this,TestViewInjectInFragmentActivity.class));


    }

    @ClickResponder(id = {R.id.test_view_inject_one})
    public void onClickButtonOne(View v) {
        Toast.makeText(TestViewInjectActivity.this, "test_view_inject_one", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.setData(Uri.parse("app://launch/user_detail?id=123&name=abc&double=1.23&float=3.14&long=1234567890&boolean=true"));
        startActivity(intent);


    }

    @ClickResponder(idStr = {"test_view_inject_two"})
    public void onClickButtonTwo(View v) {
        Toast.makeText(TestViewInjectActivity.this, "test_view_inject_two", Toast.LENGTH_SHORT).show();
        Intent intent = Util.buildFullIntent(this,TestViewInjectActivityTwo.class);
        startActivity(intent);
    }

    @LongClickResponder(idStr = {"test_view_inject_two"})
    public void onLongClickButtonTwo(View v){
        Toast.makeText(TestViewInjectActivity.this, "long click button two", Toast.LENGTH_SHORT).show();
    }



    @LongClickResponder(id = R.id.test_long_click)
    public void onLongClick(View v){
        Toast.makeText(TestViewInjectActivity.this, "test_long_click", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiverHashMap!=null){
            if(broadcastReceiverHashMap.get(BroadcastResponder.GLOBAL_BROADCAST) !=null){

                unregisterReceiver(broadcastReceiverHashMap.get(BroadcastResponder.GLOBAL_BROADCAST));
            }

            if(broadcastReceiverHashMap.get(BroadcastResponder.LOCAL_BROADCAST) !=null){
                LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiverHashMap.get(BroadcastResponder.LOCAL_BROADCAST));
            }

        }
    }
}
