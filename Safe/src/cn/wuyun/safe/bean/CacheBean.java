package cn.wuyun.safe.bean;

import android.graphics.drawable.Drawable;

public class CacheBean {

	public String name;
	public String packageName;
	public Drawable icon;
	public long cachsize;

	public CacheBean(String name, String packageName, Drawable icon,
			long cachsize) {
		super();
		this.name = name;
		this.packageName = packageName;
		this.icon = icon;
		this.cachsize = cachsize;
	}

}
