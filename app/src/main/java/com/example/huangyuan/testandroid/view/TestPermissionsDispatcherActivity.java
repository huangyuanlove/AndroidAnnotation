package com.example.huangyuan.testandroid.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.huangyuan.testandroid.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class TestPermissionsDispatcherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_permissions_dispatcher);
        findViewById(R.id.test_runtime_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestPermissionsDispatcherActivityPermissionsDispatcher

                .testRuntimePermissionOnClickWithPermissionCheck(TestPermissionsDispatcherActivity.this,v);
            }
        });
    }
    @NeedsPermission(Manifest.permission.CAMERA)
    void testRuntimePermissionOnClick(View v){
        startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        Toast.makeText(this,"打开相机",Toast.LENGTH_LONG).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onShowRationaleOnCamera(PermissionRequest request){
        showRationaleDialog("打开相机",request);
    }
    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onDeniedCamera(){
        Toast.makeText(this,"拒绝了相机权限，无法进行拍照",Toast.LENGTH_LONG).show();
    }
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onNeverAskAgain(){
        showGuidDialog();
    }

    private void showGuidDialog() {
        new AlertDialog.Builder(this)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false)
                .setMessage("需要相机权限").show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TestPermissionsDispatcherActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    private void showRationaleDialog(String message, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(message)
                .show();
    }


}
