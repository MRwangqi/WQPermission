package com.codelang.wqpermission;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codelang.library.CallBack;
import com.codelang.library.JumpPermissionUtils;
import com.codelang.library.WQPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send(View view) {

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
                //此处可以显示对话框，跳出让用户选择前往应用设置打开权限设置
//                JumpPermissionUtils.GoToSetting(MainActivity.this);
            }
        });
    }
}
