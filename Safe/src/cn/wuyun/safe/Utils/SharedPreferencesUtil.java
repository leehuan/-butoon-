package cn.wuyun.safe.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View.OnClickListener;

public class SharedPreferencesUtil {

	private static SharedPreferences shared;

	public static void saveboolean(Context context, String key, boolean value) {

		if (shared == null) {

			shared = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);

		}

		shared.edit().putBoolean(key, value).commit();

	}

	public static boolean getboolean(Context context, String key,
			boolean defvalue) {

		if (shared == null) {

			shared = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);

		}

		return shared.getBoolean(key, defvalue);

	}

	public static void saveString(Context context, String key, String value) {

		if (shared == null) {

			shared = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}

		shared.edit().putString(key, value).commit();
	}

	public static String getString(Context context, String key, String dfvalue) {
		if (shared == null) {

			shared = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}
		return shared.getString(key, dfvalue);
	}

	public static void saveInt(Context context, String key, int value) {

		if (shared == null) {

			shared = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}

		shared.edit().putInt(key, value).commit();
	}

	public static int getInt(Context context, String key, int dfvalue) {
		if (shared == null) {

			shared = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}
		return shared.getInt(key, dfvalue);
	}

}
