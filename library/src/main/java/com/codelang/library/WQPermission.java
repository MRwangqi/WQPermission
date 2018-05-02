package com.codelang.library;

import android.app.Activity;
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


    public void request(Fragment fragment, String[] permission, CallBack callBack) {
        WQFragment wqFragment = find(fragment);
        if (wqFragment != null)
            wqFragment.request(callBack, permission);

    }


    private WQFragment find(Object object) {
        WQFragment wqFragment;
        FragmentManager manager = null;
        if (object instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) object;
            manager = activity.getSupportFragmentManager();
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            manager = fragment.getChildFragmentManager();
        }
        wqFragment = (WQFragment) manager.findFragmentByTag(WQ_PERMISSION);
        if (wqFragment == null) {
            wqFragment = WQFragment.newInstance();
            manager.beginTransaction()
                    .add(wqFragment, WQ_PERMISSION)
                    .commitAllowingStateLoss();
        }

        return wqFragment;

    }
}
