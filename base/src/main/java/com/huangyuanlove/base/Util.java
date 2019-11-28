package com.huangyuanlove.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class Util {


    public static Intent buildFullIntent(Context context, Class<? extends Activity> clazz){
        Intent intent = new Intent(context,clazz);

        intent.putExtra("boolean",true);
        intent.putExtra("booleans",new boolean[]{true,false,true,false});

        intent.putExtra("short",(short)1);
        intent.putExtra("shorts",new short[]{(short)2,(short) 3,(short) 4});

        intent.putExtra("byte",(byte)5);
        intent.putExtra("bytes",new byte[]{6,7,8});

        intent.putExtra("int",1);
        intent.putExtra("ints",new int[]{9,10,11});

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add(Integer.valueOf(10010));
        integerArrayList.add(Integer.valueOf(10086));
        intent.putIntegerArrayListExtra("integerArrayList",integerArrayList);

        intent.putExtra("long",System.currentTimeMillis());
        intent.putExtra("longs",new long[]{System.currentTimeMillis(),System.currentTimeMillis(),System.currentTimeMillis()});


        intent.putExtra("char",'A');
        intent.putExtra("chars",new char[]{'B','C','D'});

        CharSequence charSequence = "EFG";
        intent.putExtra("charSequence",charSequence);
        intent.putExtra("charSequences",new CharSequence[]{charSequence,charSequence,charSequence});

        ArrayList<CharSequence> charSequenceArrayList = new ArrayList<>();
        charSequenceArrayList.add(charSequence);
        charSequenceArrayList.add(charSequence);
        charSequenceArrayList.add(charSequence);
        intent.putCharSequenceArrayListExtra("charSequenceArrayList",charSequenceArrayList);

        intent.putExtra("float",0.1f);
        intent.putExtra("floats",new float[]{0.2f,0.3f,0.4f});

        intent.putExtra("double",1.1d);
        intent.putExtra("doubles",new double[]{1.2d,1.3d,1.4d});

        intent.putExtra("String","String");
        intent.putExtra("Strings",new String[]{"abc","def","ghi"});
        ArrayList<String> StringArray = new ArrayList<>();
        StringArray.add("jkl");
        StringArray.add("mno");
        StringArray.add("zyx");
        intent.putStringArrayListExtra("StringArray",StringArray);
        intent.putExtra("StringArray",StringArray);

        ParcelableObject parcelableObject = new ParcelableObject();
        parcelableObject.id = -1;
        parcelableObject.name = "-1";
        intent.putExtra("parcelableObject",parcelableObject);
        intent.putExtra("parcelableObjects",new ParcelableObject[]{parcelableObject,parcelableObject,parcelableObject});



        ArrayList<ParcelableObject> parcelableObjectArrayList = new ArrayList<>();
        parcelableObjectArrayList.add(parcelableObject);
        parcelableObjectArrayList.add(parcelableObject);
        parcelableObjectArrayList.add(parcelableObject);
        intent.putParcelableArrayListExtra("parcelableObjectArrayList",parcelableObjectArrayList);

        UnParcelableObject unParcelableObject = new UnParcelableObject();
        unParcelableObject.id = 1;
        unParcelableObject.name = "1";

        intent.putExtra("unParcelableObject",unParcelableObject);
        return intent;

    }

}
