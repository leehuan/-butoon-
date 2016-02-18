package cn.wuyun.safe.Utils;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;

public class ServiceUtil {

	public static boolean getService(Context context, String classService) {
		// 获取Activity管理器
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取运行的管理器
		List<RunningServiceInfo> runningServices = activityManager
				.getRunningServices(1000);
		for (RunningServiceInfo runningServiceInfo : runningServices) {
			ComponentName service = runningServiceInfo.service;
			String className = service.getClassName();
			if (classService.equals(className)) {
				return true;//寻找到了服务，说明服务是开启状态
			}

		}
		return false;

	}
}
