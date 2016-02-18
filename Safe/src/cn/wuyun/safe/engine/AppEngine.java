package cn.wuyun.safe.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.wuyun.safe.bean.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class AppEngine {

	private static PackageManager packageManager;

	public static List<AppInfo> getAppmessage(Context context) {
		packageManager = context.getPackageManager();
		// 获取所有的安装的软件
		List<AppInfo> list = new ArrayList<AppInfo>();
		List<PackageInfo> installedPackages = packageManager
				.getInstalledPackages(0);

		for (PackageInfo packageInfo : installedPackages) {
			// 获取包名
			String packageName = packageInfo.packageName;
			// 获取软件信息
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;

			// 通过软件信息获取名称
			String name = applicationInfo.loadLabel(packageManager).toString();
			// 获取图标
			Drawable loadIcon = applicationInfo.loadIcon(packageManager);
			// 通过路径获取文件大小
			String sourceDir = applicationInfo.sourceDir;
			long length = new File(sourceDir).length();
			// 获取标签信息，查看是否是系统软件
			int flags = applicationInfo.flags;
			boolean isSystem;
			if ((flags & applicationInfo.FLAG_SYSTEM) == applicationInfo.FLAG_SYSTEM) {
				isSystem = true;
			} else {
				isSystem = false;
			}

			boolean isSd;

			if ((flags & applicationInfo.FLAG_EXTERNAL_STORAGE) == applicationInfo.FLAG_EXTERNAL_STORAGE) {
				isSd = true;
			} else {
				isSd = false;
			}
			int uid = applicationInfo.uid;
			AppInfo appinfo = new AppInfo(name, packageName, loadIcon, isSd, length, isSystem, uid);

			list.add(appinfo);
		}
		return list;
	}
}
