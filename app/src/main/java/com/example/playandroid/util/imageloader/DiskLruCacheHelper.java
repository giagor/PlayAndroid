package com.example.playandroid.util.imageloader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 磁盘缓存的帮助类.
 */
public class DiskLruCacheHelper {
    /**
     * @param uniqueName 缓存的子项文件夹名字
     * @return 缓存的文件夹
     * */
    static File getDiskCacheDir(Context context, String uniqueName){
        String cachePath;
        //sd卡处于正常的使用状态或者没有被移除
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) 
                || !Environment.isExternalStorageRemovable()){
            cachePath = Objects.requireNonNull(context.getExternalCacheDir()).getPath();
        }else{
            //手机无sd卡或者被移除
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    } 
    
    /**
     * 获取应用程序的版本号.
     * */
    static int getAppVersion(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return (int) info.getLongVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    /**
     * 将传入的字符串做MD5编码.
     * <b>
     *     例如传入图片的url，编码后只会包含0-F这样的字符，符合文件的命名规则，可以用来命名文件.
     * </b>
     * */
    static String hashKeyForDisk(String key){
        String cacheKey;
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(key.getBytes());
            cacheKey = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }
    
    private static String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
