package com.example.huangyuan.testandroid.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import com.huangyuanlove.view_inject_api.PermissionUtil;

public class TestPermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_permission);

        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PermissionUtil.hasSelfPermissions(TestPermissionActivity.this, Manifest.permission.CAMERA)){
                    takePhoto();
                }else{
                    if(PermissionUtil.shouldShowRequestPermissionRationale(TestPermissionActivity.this,Manifest.permission.CAMERA)){
                        AlertDialog alertDialog = new AlertDialog.Builder(TestPermissionActivity.this)
                                .setMessage("需要相机权限进行拍照")
                                .setTitle("权限提醒")
                                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        ActivityCompat.requestPermissions(TestPermissionActivity.this,new String[]{ Manifest.permission.CAMERA},10010);
                                    }
                                })
                                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        onPermissionDenied();

                                    }
                                })
                                .create();
                        alertDialog.show();
                    }else{
                        ActivityCompat.requestPermissions(TestPermissionActivity.this,new String[]{ Manifest.permission.CAMERA},10010);
                    }

                }
            }
        });

    }


    private  void onPermissionDenied(){
        Toast.makeText(this,"拒绝了权限，无法进行下一步操作",Toast.LENGTH_SHORT).show();
    }
    private void takePhoto(){



        startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(this,permissions,grantResults,new PermissionUtil.RequestPermissionResult(){

            @Override
            public void onGrant() {
                takePhoto();
            }

            @Override
            public void onDenied() {
                onPermissionDenied();
            }

            @Override
            public void onNeverAskAgain() {

                AlertDialog alertDialog = new AlertDialog.Builder(TestPermissionActivity.this)
                        .setTitle("权限提示")
                        .setMessage("拒绝了权限，并永不询问，请在设置中打开权限，否则无法进行下一步操作")
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)

                        .create();



                alertDialog.show();


                Toast.makeText(TestPermissionActivity.this,"拒绝了权限，并永不询问，请在设置中打开权限，否则无法进行下一步操作",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
