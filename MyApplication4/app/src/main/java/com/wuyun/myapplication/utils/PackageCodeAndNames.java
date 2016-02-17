package com.wuyun.myapplication.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by LEE on 2016-02-05.
 */
public class PackageCodeAndNames {
    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo =
                null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     */
    public static int getPackageCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo =
                null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
