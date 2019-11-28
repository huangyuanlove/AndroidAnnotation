package com.example.huangyuan.testandroid.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_annotation.ClickResponder;
import com.huangyuanlove.view_inject_annotation.LongClickResponder;
import com.huangyuanlove.view_inject_api.ViewInjector;

public class TestViewInjectFragment extends Fragment {

    @BindView(id = R.id.button_one)
    protected Button buttonOne;
    @BindView(idStr = "button_two")
    protected Button buttonTwo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_inject,container,false);
        ViewInjector.bind(this,view);
        ViewInjector.parseBundle(this);

        buttonTwo.setText("set text by code");

        return view;
    }


    @ClickResponder(id = R.id.button_one)
    public void onClickButtonOne(View v){
        Toast.makeText(getContext(),"click button one",Toast.LENGTH_SHORT).show();
    }
    @LongClickResponder(id = R.id.button_one)
    public void onLongClickButtonOne(View v){
        Toast.makeText(getContext(),"long click button one",Toast.LENGTH_SHORT).show();
    }
}
