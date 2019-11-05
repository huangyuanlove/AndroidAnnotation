package com.example.huangyuan.testandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_annotation.IntentValue;
import com.huangyuanlove.view_inject_api.BundleInjector;
import com.huangyuanlove.view_inject_api.ViewInjector;

import java.util.ArrayList;

public class TestViewInjectActivityTwo extends AppCompatActivity {


    @BindView(id = R.id.test_view_inject_one)
    protected Button buttonOne;
    @BindView(idStr = "test_view_inject_two")
    protected Button buttonTwo;


    @IntentValue(key = "boolean")
    boolean aBoolean;
    @IntentValue(key = "booleans")
    boolean[] booleans;

    @IntentValue(key = "short")
    short aShort;
    @IntentValue(key = "shorts")
    short[] shorts;

    @IntentValue(key = "byte")
    byte aByte;
    @IntentValue(key = "bytes")
    byte[] bytes;

    @IntentValue(key = "int")
    int anInt;
    @IntentValue(key = "ints")
    int[] ints;
    @IntentValue(key = "integerArrayList")
    ArrayList<Integer> integerArrayList;

    @IntentValue(key = "long")
    long aLong;
    @IntentValue(key = "longs")
    long[] longs;

    @IntentValue(key = "char")
    char aChar;
    @IntentValue(key = "chars")
    char[] chars;
    @IntentValue(key = "charSequence")
    CharSequence charSequence;
    @IntentValue(key = "charSequences")
    CharSequence[] charSequences;
    @IntentValue(key = "charSequenceArrayList")
    ArrayList<CharSequence>  charSequenceArrayList;

    @IntentValue(key = "float")
    float aFloat;
    @IntentValue(key = "floats")
    float[] floats;

    @IntentValue(key = "double")
    double aDouble;
    @IntentValue(key = "doubles")
    double[] doubles;

    @IntentValue(key = "String")
    String aString;
    @IntentValue(key = "Strings")
    String[] strings;
    @IntentValue(key = "StringArray")
    ArrayList<String> stringArrayList;

    @IntentValue(key = "parcelableObject")
    ParcelableObject parcelableObject;
    @IntentValue(key = "parcelableObjects")
    ParcelableObject[] parcelableObjects;
    @IntentValue(key = "parcelableObjectArrayList")
    ArrayList<ParcelableObject>  parcelableObjectArrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_inject);
        ViewInjector.bind(this);
        BundleInjector.parseBundle(this);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestViewInjectActivityTwo.this, "点击", Toast.LENGTH_SHORT).show();
            }
        });
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestViewInjectActivityTwo.this, "点击", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
