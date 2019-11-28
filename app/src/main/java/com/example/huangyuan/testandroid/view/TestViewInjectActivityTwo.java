package com.example.huangyuan.testandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.base.ParcelableObject;
import com.huangyuanlove.base.UnParcelableObject;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_annotation.IntentValue;
import com.huangyuanlove.view_inject_api.ViewInjector;

import java.util.ArrayList;

public class TestViewInjectActivityTwo extends AppCompatActivity {


    @BindView(id = R.id.test_view_inject_one)
    protected Button buttonOne;
    @BindView(idStr = "test_view_inject_two")
    protected Button buttonTwo;


    @IntentValue(key = "boolean")
    boolean aBoolean;
    @BindView(id = R.id.show_boolean)
    TextView showBoolean;

    @IntentValue(key = "booleans")
    boolean[] booleans;
    @BindView(id = R.id.show_booleans)
    TextView showBooleans;

    @IntentValue(key = "short")
    short aShort;
    @BindView(id = R.id.show_short)
    TextView show_short;

    @IntentValue(key = "shorts")
    short[] shorts;
    @BindView(id = R.id.show_shorts)
    TextView show_shorts;

    @IntentValue(key = "byte")
    byte aByte;
    @BindView(id = R.id.show_byte)
    TextView show_byte;
    @IntentValue(key = "bytes")
    byte[] bytes;
    @BindView(id = R.id.show_bytes)
    TextView show_bytes;

    @IntentValue(key = "int")
    int anInt;
    @BindView(id = R.id.show_int)
    TextView show_int;
    @IntentValue(key = "ints")
    int[] ints;
    @BindView(id = R.id.show_ints)
    TextView show_ints;
    @IntentValue(key = "integerArrayList")
    ArrayList<Integer> integerArrayList;
    @BindView(id = R.id.show_integerArrayList)
    TextView show_integerArrayList;

    @IntentValue(key = "long")
    long aLong;
    @BindView(id = R.id.show_long)
    TextView show_long;
    @IntentValue(key = "longs")
    long[] longs;
    @BindView(id = R.id.show_longs)
    TextView show_longs;

    @IntentValue(key = "char")
    char aChar;
    @BindView(id = R.id.show_char)
    TextView show_char;
    @IntentValue(key = "chars")
    char[] chars;
    @BindView(id = R.id.show_chars)
    TextView show_chars;
    @IntentValue(key = "charSequence")
    CharSequence charSequence;
    @BindView(id = R.id.show_charSequence)
    TextView show_charSequence;
    @IntentValue(key = "charSequences")
    CharSequence[] charSequences;
    @BindView(id = R.id.show_charSequences)
    TextView show_charSequences;
    @IntentValue(key = "charSequenceArrayList")
    ArrayList<CharSequence>  charSequenceArrayList;
    @BindView(id = R.id.show_charSequenceArrayList)
    TextView show_charSequenceArrayList;

    @IntentValue(key = "float")
    float aFloat;
    @BindView(id = R.id.show_float)
    TextView show_float;
    @IntentValue(key = "floats")
    float[] floats;
    @BindView(id = R.id.show_floats)
    TextView show_floats;

    @IntentValue(key = "double")
    double aDouble;
    @BindView(id = R.id.show_double)
    TextView show_double;
    @IntentValue(key = "doubles")
    double[] doubles;
    @BindView(id = R.id.show_doubles)
    TextView show_doubles;

    @IntentValue(key = "String")
    String aString;
    @BindView(id = R.id.show_String)
    TextView show_String;
    @IntentValue(key = "Strings")
    String[] strings;
    @BindView(id = R.id.show_Strings)
    TextView show_Strings;
    @IntentValue(key = "StringArray")
    ArrayList<String> stringArrayList;
    @BindView(id = R.id.show_StringArray)
    TextView show_StringArray;

    @IntentValue(key = "parcelableObject",type = IntentValue.PARCELABLE_OBJECT)
    ParcelableObject parcelableObject;
    @BindView(id = R.id.show_parcelableObject)
    TextView show_parcelableObject;

    @IntentValue(key = "parcelableObjects" ,type = IntentValue.PARCELABLE_ARRAY_OBJECT)
    ParcelableObject[] parcelableObjects;
    @BindView(id = R.id.show_parcelableObjects)
    TextView show_parcelableObjects;
    @IntentValue(key = "parcelableObjectArrayList",type = IntentValue.PARCELABLE_ARRAYLIST_OBJECT)
    ArrayList<ParcelableObject>  parcelableObjectArrayList;
    @BindView(id = R.id.show_parcelableObjectArrayList)
    TextView show_parcelableObjectArrayList;

    @IntentValue(key = "unParcelableObject",type = IntentValue.SERIALIZABLE_OBJECT)
    UnParcelableObject unParcelableObject;
    @BindView(id = R.id.show_unParcelableObject)
    TextView show_unParcelableObject;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_inject);
        ViewInjector.bind(this);
        ViewInjector.parseBundle(this);





        showBoolean.setText(aBoolean+"");
        showBooleans.setText(booleans+"");
        show_short.setText(String.valueOf(aShort));
        show_shorts.setText(shorts+"");
        show_byte.setText(aByte+"");
        show_bytes.setText(bytes+"");
        show_int.setText(anInt+"");
        show_ints.setText(ints+"");
        show_integerArrayList.setText(integerArrayList==null?"null":integerArrayList.toString());
        show_long.setText(aLong+"");
        show_longs.setText(longs+"");
        show_char.setText(aChar+"");
        show_chars.setText(chars+"");
        show_charSequence.setText(charSequence+"");
        show_charSequences.setText(charSequences+"");
        show_charSequenceArrayList.setText(charSequenceArrayList==null?"null":charSequenceArrayList.toString());

        show_float.setText(aFloat+"");
        show_floats.setText(floats+"");
        show_double.setText(aDouble +"");
        show_doubles.setText(doubles+"");
        show_String.setText(aString);
        show_Strings.setText(strings+"");
        show_StringArray.setText(stringArrayList==null?"null":stringArrayList.toString());

        show_parcelableObject.setText(parcelableObject.toString());
        show_parcelableObjects.setText(parcelableObjects+"");
        show_parcelableObjectArrayList.setText(parcelableObjectArrayList==null?"null":parcelableObjectArrayList.toString());

        show_unParcelableObject.setText(unParcelableObject+"");


        buttonOne.setText("本地广播");
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastManager.getInstance(TestViewInjectActivityTwo.this).sendBroadcast(new Intent("com.huangyuanblog"));

            }
        });

        buttonTwo.setText("全局广播");
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  sendBroadcast(new Intent("com.huangyuanlove"));
            }
        });


    }
}
