package com.example.huangyuan.testandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.view_inject_annotation.ViewInject;
import com.huangyuanlove.view_inject_api.ViewInjector;

public class TestViewInjectActivity extends AppCompatActivity {

    @ViewInject(id = R.id.test_view_inject_one)
    protected Button buttonOne;
    @ViewInject(idStr = "test_view_inject_two")
    protected Button buttonTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_inject);
        ViewInjector.injectView(this);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestViewInjectActivity.this, "点击", Toast.LENGTH_SHORT).show();
            }
        });
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestViewInjectActivity.this, "点击", Toast.LENGTH_SHORT).show();
            }
        });


        ((Button) findViewById(getResources().getIdentifier("test_view_inject_one", "id", getPackageName()))).setText("asdf");
    }
}
