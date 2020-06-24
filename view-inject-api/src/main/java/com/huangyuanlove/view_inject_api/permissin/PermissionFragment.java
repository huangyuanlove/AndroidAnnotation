package com.huangyuanlove.view_inject_api.permissin;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class PermissionFragment extends Fragment {
    OnPermissionResult onPermissionResult;

    public void setOnPermissionResult(OnPermissionResult onPermissionResult) {
        this.onPermissionResult = onPermissionResult;
    }

    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @TargetApi(Build.VERSION_CODES.M)
    void requestPermissions(int requestCode, @androidx.annotation.NonNull String[] permissions) {

        ArrayList<String> grantPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (PermissionUtil.hasSelfPermissions(getContext(), permission)) {
                grantPermissions.add(permission);
            }
        }

        if (grantPermissions.size() == permissions.length) {
            onPermissionResult.onPermissionResult(requestCode, grantPermissions, null, null);
        } else {
            requestPermissions(permissions, requestCode);
        }

    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        ArrayList<String> grantResultList = new ArrayList<>();
        ArrayList<String> shouldShowRationaleList = new ArrayList<>();
        ArrayList<String> neverAskAgainList = new ArrayList<>();

        for (String permission : permissions) {
            if (PermissionUtil.hasSelfPermissions(getContext(), permission)) {
                grantResultList.add(permission);

            } else if (PermissionUtil.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                shouldShowRationaleList.add(permission);
            } else {
                neverAskAgainList.add(permission);
            }

        }

        if (onPermissionResult != null) {
            onPermissionResult.onPermissionResult(requestCode, grantResultList, shouldShowRationaleList, neverAskAgainList);
        }
    }


    public interface OnPermissionResult {
        void onPermissionResult(int requestCode, ArrayList<String> grantPermission, ArrayList<String> shouldShowRationalePermission, ArrayList<String> neverAskAgainPermission);
    }


}
