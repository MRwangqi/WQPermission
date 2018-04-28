package com.codelang.library;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * @author wangqi
 * @since 2018/4/28 14:50
 * <p>
 * 简单的权限请求库
 */


public class WQPermission {

    private final String WQ_PERMISSION = WQPermission.class.getSimpleName();

    public static WQPermission getInstance() {
        return InnerClass.wqPermission;
    }

    private static class InnerClass {
        static WQPermission wqPermission = new WQPermission();
    }

    public void request(FragmentActivity activity, String[] permission, CallBack callBack) {
        WQFragment wqFragment = find(activity);
        if (wqFragment != null)
            wqFragment.request(callBack, permission);

    }

    private WQFragment find(FragmentActivity activity) {
        WQFragment wqFragment = null;
        if (activity != null && !activity.isFinishing()) {
            FragmentManager manager = activity.getSupportFragmentManager();
            wqFragment = (WQFragment) manager.findFragmentByTag(WQ_PERMISSION);
            if (wqFragment == null) {
                wqFragment = WQFragment.newInstance();
                manager.beginTransaction()
                        .add(wqFragment, WQ_PERMISSION)
                        .commitAllowingStateLoss();
            }
        }
        return wqFragment;
    }
}
