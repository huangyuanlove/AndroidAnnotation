package com.example.huangyuan.testandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_annotation.ClickResponder;
import com.huangyuanlove.view_inject_annotation.IntentValue;
import com.huangyuanlove.view_inject_api.ViewInjector;

import java.util.ArrayList;
import java.util.List;

public class TestViewInjectActivity extends AppCompatActivity {

    @BindView(id = R.id.test_view_inject_one)
    protected Button buttonOne;
    @BindView(idStr = "test_view_inject_two")
    protected Button buttonTwo;
    @BindView(id = R.id.list_view)
    protected ListView listView;

    @IntentValue(key = "a1")
    protected String a1;
    @IntentValue(key = "a2")
    protected String a2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_inject);
        ViewInjector.bind(this);
//
//        buttonOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(TestViewInjectActivity.this, "点击", Toast.LENGTH_SHORT).show();
//            }
//        });
//        buttonTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(TestViewInjectActivity.this, "点击", Toast.LENGTH_SHORT).show();
//            }
//        });


        ((Button) findViewById(getResources().getIdentifier("test_view_inject_one", "id", getPackageName()))).setText("asdf");

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("item-->" + i);
        }

        ListViewAdapter adapter = new ListViewAdapter(data, this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @ClickResponder(id = {R.id.test_view_inject_one})
    public void onClickButtonOne(View v) {
        Toast.makeText(TestViewInjectActivity.this, "test_view_inject_one", Toast.LENGTH_SHORT).show();
    }

    @ClickResponder(idStr = {"test_view_inject_two"})
    public void onClickButtonTwo(View v) {
        Toast.makeText(TestViewInjectActivity.this, "test_view_inject_two", Toast.LENGTH_SHORT).show();
    }


}
