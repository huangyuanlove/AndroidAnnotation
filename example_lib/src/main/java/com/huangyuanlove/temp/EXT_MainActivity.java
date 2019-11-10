package com.huangyuanlove.temp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.huangyuanlove.view_inject_annotation.IntentValue;

public class EXT_MainActivity extends AppCompatActivity {

    @IntentValue(key = "ABC")
    protected String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ext__main);
    }
}
