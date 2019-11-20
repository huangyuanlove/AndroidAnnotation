package com.example.huangyuan.testandroid.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_annotation.IntentValue;
import com.huangyuanlove.view_inject_api.BundleInjector;
import com.huangyuanlove.view_inject_api.ViewInjector;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-30
 * Email: huangyuan@chunyu.me
 */
public class TestViewInjectFragment extends Fragment {

    @BindView(id = R.id.test_view_inject_one)
    protected Button buttonOne;
    @BindView(idStr = "test_view_inject_two")
    protected Button buttonTwo;

    @IntentValue(key = "a1")
    protected String a1;
    @IntentValue(key = "a2")
    protected String a2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test_view_inject,container,false);
        ViewInjector.bind(this,view);
        BundleInjector.parseBundle(this);


        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
