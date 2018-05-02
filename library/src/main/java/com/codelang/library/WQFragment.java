package com.codelang.library;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangqi
 * @since 2018/4/28 15:10
 */

public class WQFragment extends Fragment {

    private static final int REQUEST_PERMISSION = 0x1129;

    String[] permission;

    CallBack callBack;

    List<String> deniedList = new ArrayList<>();


    public static WQFragment newInstance() {
        return new WQFragment();
    }

    /**
     * 没在request进行requestPermissions操作，因为request的调用时机超过Fragment的onAttach的时候
     * 会报Fragment not attached to Activity 错误，所以只能将实例保存，然后在onAttach方法之后操作，
     * onAttach方法之后的生命周期都可以，也包括onAttach，我在onCreate做了
     *
     * @param callBack
     * @param permission
     */
    public void request(CallBack callBack, String[] permission) {
        this.permission = permission;
        this.callBack = callBack;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WQFragment.this.requestPermissions(permission, REQUEST_PERMISSION);
    }

    /**
     * 当用户选择下次不再提示，点击拒绝，grantResults[i]返回的是-1，而且下次不会再跳出权限申请框
     * 这个处理可以在 onDenied 的回调中处理，onDenied会将没过的申请权限返回，在这个回调中打开应用的权限设置界面
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION) {
            for (int i = 0; i < permission.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[i]);
                }
            }
            if (deniedList.size() == 0) {
                //全部都授予了权限
                callBack.onGranted();
            } else {
                //将未授予的权限集合返回
                callBack.onDenied(deniedList);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
