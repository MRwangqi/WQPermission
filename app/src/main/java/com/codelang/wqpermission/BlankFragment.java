package com.codelang.wqpermission;


import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codelang.library.CallBack;
import com.codelang.library.WQPermission;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
        return view;
    }


    public void send() {
        Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();

        String[] manifests = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE};

        WQPermission.getInstance().request(this, manifests, new CallBack() {
            @Override
            public void onGranted() {
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
