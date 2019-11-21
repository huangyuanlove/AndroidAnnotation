package com.example.huangyuan.testandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.view_inject_annotation.UriValue;
import com.huangyuanlove.view_inject_api.ViewInjector;

public class TestUriValueActivity extends AppCompatActivity {
    @UriValue(key = "name")
    String name;
    @UriValue(key = "id")
    int id;
    @UriValue(key = "double")
    double aDouble;

    @UriValue(key = "float")
    float aFloat;

    @UriValue(key = "long")
    long aLong;

    @UriValue(key = "boolean")
    boolean aBoolean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_uri_value);
        ViewInjector.parseUri(this);
        TextView textView = findViewById(R.id.show_message);
        StringBuilder sb = new StringBuilder(getIntent().getData().toString());
        sb.append("\n");

        sb.append("name:");
        sb.append(name);
        sb.append("\n");

        sb.append("id:");
        sb.append(id);
        sb.append("\n");

        sb.append("aFloat:");
        sb.append(aFloat);
        sb.append("\n");

        sb.append("aDouble:");
        sb.append(aDouble);
        sb.append("\n");

        sb.append("aLong:");
        sb.append(aLong);
        sb.append("\n");

        sb.append("aBoolean:");
        sb.append(aBoolean);
        sb.append("\n");

        textView.setText(sb.toString());

    }
}
