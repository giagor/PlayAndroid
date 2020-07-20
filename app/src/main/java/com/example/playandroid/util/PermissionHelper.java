package com.example.playandroid.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 运行时权限的请求.
 * */
public class PermissionHelper {
    
    public static void requestPermissions(Activity activity,String [] permissions,final int requestCode){
        List<String> permissionList = new ArrayList<>();

        //筛选出需要处理的权限
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
       
        //权限请求
        if(permissionList.size() != 0){
            String [] requests = (String[]) permissionList.toArray();
            ActivityCompat.requestPermissions(activity,requests,requestCode);
        }
    }
}
