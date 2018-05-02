# WQPermission
简单的动态权限申请框架

### 简单的使用

```
   String[] manifests = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE};

        WQPermission.getInstance().request(this, manifests, new CallBack() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "全部授予权限", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(List<String> list) {
                for (int i = 0; i < list.size(); i++) {
                    Log.i("未授权列表", list.get(i));
                }
            }
        });
    }
```

### 原理介绍

通过给当前的FragmentActivity或是Fragment添加一个隐藏的Fragment

```
//Activity中拿到manager
 manager = activity.getSupportFragmentManager();
//Fragment中拿到manager
 manager = fragment.getChildFragmentManager();
 

 manager.beginTransaction()
        .add(wqFragment, WQ_PERMISSION)
        .commitAllowingStateLoss();
```

在Fragment中请求permission，这个地方需要注意的是，如果直接拿到Fragment去调用request请求权限的话，会报Fragment not attached to Activity 错误，原因是在调用权限申请的方法的时候，该Fragment的onAttach方法还没初始化，所以，需要在onAttach方法初始化后再去调用，我是延迟到onCreate初始化的时候调用

Fragment的requestPermissions直接使用就行，无需getActivity().requestPermissions去操作，这样的话，权限结果处理会被Activity拿到

然后在Fragment的onRequestPermissionsResult接收权限的处理操作