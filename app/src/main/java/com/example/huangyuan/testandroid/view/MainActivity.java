package com.example.huangyuan.testandroid.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.huangyuan.testandroid.R;
import com.example.huangyuan.testandroid.annotation.BindView;
import com.example.huangyuan.testandroid.annotation.OnClick;
import com.example.huangyuan.testandroid.annotation.OnLongClick;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class MainActivity extends Activity {
    @BindView(value = R.id.test_runtime_annotation)
    Button testRuntimeAnnotation;


    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAnnotation();

        findViewById(R.id.test_router).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test_router(v);

            }
        });


        Uri uri = Uri.parse("App://main/toMain");
        Log.e("URI",uri.getScheme() +"\t" + uri.getHost() +"\t" + uri.getPath());

        uri = Uri.parse("App://main/toMain/pay");
        Log.e("URI",uri.getScheme() +"\t" + uri.getHost() +"\t" + uri.getPath());

        uri = Uri.parse("App://main/toMain/pay/alipay");
        Log.e("URI",uri.getScheme() +"\t" + uri.getHost() +"\t" + uri.getPath());


    }

    @OnLongClick(id = R.id.test_runtime_annotation)
    void testRuntimeAnnotationOnLongClick(View v) {
        Toast.makeText(this, "测试运行时注解", Toast.LENGTH_LONG).show();
    }

    @OnClick(id = R.id.test_runtime_annotation)
    void setTestRuntimeAnnotationOnClick(View v) {
        if (toast == null) {
            toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }

        toast.setText(String.valueOf(System.currentTimeMillis()));
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    @OnClick(id = R.id.test_runtime_permission)
    void testRuntimePermission(View v) {
        startActivity(new Intent(this, TestPermissionsDispatcherActivity.class));
    }

    @OnClick(id = R.id.test_view_inject)
    void testViewInject(View v) {
        startActivity(new Intent(this, TestViewInjectActivity.class));
    }


//    @OnClick(id=R.id.test_router)
    void test_router(View v){
        String routerName = "com.huangyuanlove.router.Appmain$$Router";
        try{
            Class routerClass = Class.forName(routerName);

            Method invoke = routerClass.getDeclaredMethod("invoke", Context.class,String.class,Bundle.class);


            Constructor constructor = routerClass.getConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();
            invoke.invoke(instance,this,"path",new Bundle());

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void initAnnotation() {
        Field fields[] = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                try {
                    field.set(this, findViewById(bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


        Method methods[] = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            OnLongClick onLongClick = method.getAnnotation(OnLongClick.class);
            if (onLongClick != null) {
                findViewById(onLongClick.id()).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        try {
                            method.invoke(v.getContext(), v);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return onLongClick.result();

                    }
                });
            }
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                findViewById(onClick.id()).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            method.invoke(v.getContext(), v);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }


}


