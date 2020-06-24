package com.huangyuanlove.view_inject_api.permissin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

/**
 * 1. 先调用hasSelfPermissions，有权限则直接执行
 *
 * 2. 再调用shouldShowRequestPermissionRationale;false则调用ActivityCompat.requestPermissions，true则展示提示框
 *
 * 3. 调用onRequestPermissionsResult
 */
public class PermissionUtil {

    private static final SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS;

    static {
        MIN_SDK_PERMISSIONS = new SimpleArrayMap<>(8);
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14);
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", 20);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", 9);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", 23);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", 23);
    }


    public interface RequestPermissionResult{
        void onGrant(int requestCode);
        void onDenied(int requestCode);
        void onNeverAskAgain(int requestCode);
    }




    public static boolean hasSelfPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (permissionExists(permission) && !hasSelfPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }


    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }


    public  static void onRequestPermissionsResult(Activity activity,int requestCode, String permissions[], int[] grantResults ,RequestPermissionResult requestPermissionResult){
        if(verifyPermissions(grantResults)){
            requestPermissionResult.onGrant(requestCode);
        }else {
            if (!shouldShowRequestPermissionRationale(activity, permissions)) {
                requestPermissionResult.onNeverAskAgain(requestCode);
            } else {
                requestPermissionResult.onDenied(requestCode);
            }
        }
    }


    public static boolean verifyPermissions(int... grantResults) {
        if (grantResults.length == 0) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    private static boolean permissionExists(String permission) {
        Integer minVersion = MIN_SDK_PERMISSIONS.get(permission);
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion;
    }

    private static boolean hasSelfPermission(Context context, String permission) {
        try {
            return PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED;
        } catch (RuntimeException t) {
            return false;
        }
    }





}
