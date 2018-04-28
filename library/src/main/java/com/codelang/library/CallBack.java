package com.codelang.library;

import java.util.List;

/**
 * @author wangqi
 * @since 2018/4/28 14:52
 */

public interface CallBack {

    void onGranted();

    void onDenied(List<String> list);
}
