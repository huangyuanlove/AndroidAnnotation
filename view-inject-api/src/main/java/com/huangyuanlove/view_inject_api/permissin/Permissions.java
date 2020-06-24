package com.huangyuanlove.view_inject_api.permissin;

import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * Description:
 * Create on: 2020/4/28
 */
public class Permissions {


    private static final String TAG = Permissions.class.getSimpleName();

    @VisibleForTesting
    Permissions.Lazy<PermissionFragment> permissionFragment;

    public Permissions(@androidx.annotation.NonNull final FragmentActivity activity) {
        permissionFragment = getLazySingleton(activity.getSupportFragmentManager());
    }

    public Permissions(@androidx.annotation.NonNull final Fragment fragment) {
        permissionFragment = getLazySingleton(fragment.getChildFragmentManager());
    }


    public void requestPermissions(int requestCode, String... permissions) {
        permissionFragment.get().requestPermissions(requestCode, permissions);
    }

    public void requestPermissions(int requestCode, PermissionFragment.OnPermissionResult onPermissionResult, String... permissions) {
        permissionFragment.get().setOnPermissionResult(onPermissionResult);
        permissionFragment.get().requestPermissions(requestCode, permissions);
    }


    @androidx.annotation.NonNull
    private Permissions.Lazy<PermissionFragment> getLazySingleton(@androidx.annotation.NonNull final FragmentManager fragmentManager) {
        return new Permissions.Lazy<PermissionFragment>() {

            private PermissionFragment permissionFragment;

            @Override
            public synchronized PermissionFragment get() {
                if (permissionFragment == null) {
                    permissionFragment = getPermissionFragment(fragmentManager);
                }
                return permissionFragment;
            }

        };
    }

    private PermissionFragment getPermissionFragment(@androidx.annotation.NonNull final FragmentManager fragmentManager) {
        PermissionFragment permissionFragment = findPermissionsFragment(fragmentManager);
        boolean isNewInstance = permissionFragment == null;
        if (isNewInstance) {
            permissionFragment = new PermissionFragment();
            fragmentManager
                    .beginTransaction()
                    .add(permissionFragment, TAG)
                    .commitNow();
        }
        return permissionFragment;
    }

    private PermissionFragment findPermissionsFragment(@androidx.annotation.NonNull final FragmentManager fragmentManager) {
        return (PermissionFragment) fragmentManager.findFragmentByTag(TAG);
    }

    @FunctionalInterface
    public interface Lazy<V> {
        V get();
    }


}
