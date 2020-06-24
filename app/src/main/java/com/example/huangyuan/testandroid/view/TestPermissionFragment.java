package com.example.huangyuan.testandroid.view;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.huangyuanlove.view_inject_annotation.ClickResponder;
import com.example.huangyuan.testandroid.R;
import com.huangyuanlove.view_inject_api.ViewInjector;
import com.huangyuanlove.view_inject_api.permissin.PermissionFragment;
import com.huangyuanlove.view_inject_api.permissin.Permissions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

/**
 * Description:
 * Create on: 2020/6/24
 */
public class TestPermissionFragment extends Fragment {

    private final  int  AUDIO_PERMISSION= 42;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_permission,container,false);
        ViewInjector.bind(this,view);


        return view;
    }

    @ClickResponder(id = R.id.audio_permission)
    public void onClickAudioPermission(View v){
        new Permissions(this).requestPermissions(AUDIO_PERMISSION, new PermissionFragment.OnPermissionResult() {
            @Override
            public void onPermissionResult(int requestCode, ArrayList<String> grantPermission, ArrayList<String> shouldShowRationalePermission, ArrayList<String> neverAskAgainPermission) {

                Log.e("huangyuan","grantPermission:"+grantPermission);
                Log.e("huangyuan","shouldShowRationalePermission:"+shouldShowRationalePermission);
                Log.e("huangyuan","neverAskAgainPermission:"+neverAskAgainPermission);
            }
        }, Manifest.permission.RECORD_AUDIO);


    }

}
