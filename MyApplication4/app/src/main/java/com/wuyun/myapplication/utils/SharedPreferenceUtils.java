package com.wuyun.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LEE on 2016-02-12.
 */
public class SharedPreferenceUtils {
    public static SharedPreferences sp;

    public static void saveString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, value);
    }

}
