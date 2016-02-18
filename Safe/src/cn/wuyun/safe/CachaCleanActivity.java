package cn.wuyun.safe;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.wuyun.safe.bean.AppInfo;
import cn.wuyun.safe.bean.CacheBean;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CachaCleanActivity extends Activity {
	private ImageView line;
	private ImageView icon;
	private ProgressBar progressbar;
	private PackageManager pm;
	private TextView title;
	private TextView prosum;
	private ListView listview;
	private List<CacheBean> list;
	private MyAdapter myadapter;
	private List<PackageInfo> installedPackages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtity_cacha);
		initView();
	}

	private void initView() {

		line = (ImageView) findViewById(R.id.iv_cache_line);
		icon = (ImageView) findViewById(R.id.iv_cache_icon);
		progressbar = (ProgressBar) findViewById(R.id.pb_clearcache_progressbar);
		title = (TextView) findViewById(R.id.tv_cache_title);
		prosum = (TextView) findViewById(R.id.tv_pro_sum);
		listview = (ListView) findViewById(R.id.lv_cacha_listview);
		changesacn = (RelativeLayout) findViewById(R.id.rel_clearcache_scan);
		sacncache = (TextView) findViewById(R.id.tv_clearcache_scancachetext);
		btclear = (Button) findViewById(R.id.btn_clearcache_clear);
		box = (ImageView) findViewById(R.id.iv_cache_box);
		clear = (Button) findViewById(R.id.bt_cache_clear);

		scan();

	}

	private void scan() {
		clear.setEnabled(false);
				list = new ArrayList<CacheBean>();
		setAnimationLine();
		pm = getPackageManager();
		new Thread() {

			public void run() {
				installedPackages = pm.getInstalledPackages(0);
				progressbar.setMax(installedPackages.size());
				int progeress = 0;
				for (PackageInfo packageInfo : installedPackages) {
					SystemClock.sleep(100);

					progeress++;
					progressbar.setProgress(progeress);

					setpackagename(packageInfo.packageName);
					getCacheSize(packageInfo.packageName);
				}
			};
		}.start();

	}

	protected void getCacheSize(String packageName) {
		// mPm.getPackageSizeInfo(mCurComputingSizePkg, mStatsObserver);
		try {
			Method method = pm.getClass().getDeclaredMethod(
					"getPackageSizeInfo", String.class,
					IPackageStatsObserver.class);
			// 方法所在类的对象，如果方法是静态的方法，设置为null,不是，设置方法所在类的对象
			method.invoke(pm, packageName, mStatsObserver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取缓存大小aidl
	 */
	IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {

		public void onGetStatsCompleted(PackageStats stats, boolean succeeded) {
			// 获取缓存大小
			final long cachsize = stats.cacheSize;
			final String packageName = stats.packageName;

			// System.out.println(packageName+"   :"+Formatter.formatFileSize(getApplicationContext(),
			// cachsize));

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					prosum.setText("缓存大小:"
							+ Formatter.formatFileSize(getApplicationContext(),
									cachsize));
				}
			});
			try {
				ApplicationInfo applicationinfo = pm.getApplicationInfo(
						packageName, 0);
				Drawable icon = applicationinfo.loadIcon(pm);
				String name = applicationinfo.loadLabel(pm).toString();
				CacheBean cache = new CacheBean(name, packageName, icon,
						cachsize);
				if (cachsize > 0) {

					list.add(0, cache);
				} else {

					list.add(cache);
				}

			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (myadapter == null) {
						myadapter = new MyAdapter();
						listview.setAdapter(myadapter);
					} else {
						myadapter.notifyDataSetChanged();
					}
					listview.smoothScrollToPosition(myadapter.getCount() - 1);
				}
			});
			runOnUiThread(new Runnable() {
				public void run() {
					if (list.size() == installedPackages.size()) {
						clear.setEnabled(true);
						listview.smoothScrollToPosition(0);
						line.clearAnimation();
						line.setVisibility(View.GONE);
						progressbar.setVisibility(View.GONE);
						icon.setVisibility(View.GONE);
						box.setVisibility(View.GONE);
						changesacn.setVisibility(View.VISIBLE);
						sacncache.setText("一共扫描了"
								+ installedPackages.size()
								+ "个软件"
								+ ",一共有"
								+ Formatter.formatFileSize(
										getApplicationContext(), cachsize)
								+ "MB");
						btclear.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								scan();
								line.setVisibility(View.VISIBLE);
								progressbar.setVisibility(View.VISIBLE);
								icon.setVisibility(View.VISIBLE);
								box.setVisibility(View.VISIBLE);
								changesacn.setVisibility(View.GONE);
							}
						});
						clear.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								try {
									Method method = pm.getClass()
											.getDeclaredMethod(
													"freeStorageAndNotify",
													Long.TYPE,
													IPackageDataObserver.class);
									// 方法所在类的对象，如果方法是静态的方法，设置为null,不是，设置方法所在类的对象
									method.invoke(pm, Long.MAX_VALUE,
											new MyIPackageDataObserver());

								} catch (Exception e) {
									e.printStackTrace();
								}
								// 重新扫描操作
								line.setVisibility(View.VISIBLE);
								progressbar.setVisibility(View.VISIBLE);
								icon.setVisibility(View.VISIBLE);
								box.setVisibility(View.VISIBLE);
								changesacn.setVisibility(View.GONE);
								scan();
							}
						});

					}
				}
			});
		}
	};
	private RelativeLayout changesacn;
	private TextView sacncache;
	private Button btclear;
	private ImageView box;
	protected static final int REQUEST_INFO_CODE = 20;
	private Button clear;

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.cache_listview, null);
				viewHolder = new ViewHolder();
				viewHolder.iv_cache_img = (ImageView) convertView
						.findViewById(R.id.iv_cache_img);

				viewHolder.tv_cache_sd = (TextView) convertView
						.findViewById(R.id.tv_cache_sd);
				viewHolder.tv_cache_title = (TextView) convertView
						.findViewById(R.id.tv_cache_title);

				viewHolder.iv_cache_clear_bt = (ImageView) convertView
						.findViewById(R.id.iv_cache_clear_bt);

				convertView.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) convertView.getTag();

			}

			final CacheBean cacheBean = list.get(position);

			viewHolder.iv_cache_img.setImageDrawable(cacheBean.icon);
			viewHolder.tv_cache_title.setText(cacheBean.name);
			viewHolder.tv_cache_sd.setText("缓存大小:"
					+ Formatter.formatFileSize(getApplicationContext(),
							cacheBean.cachsize));
			if (cacheBean.cachsize > 0) {
				viewHolder.iv_cache_clear_bt.setVisibility(View.VISIBLE);
			} else {
				viewHolder.iv_cache_clear_bt.setVisibility(View.GONE);
			}
			viewHolder.iv_cache_clear_bt
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
							intent.addCategory("android.intent.category.DEFAULT");
							intent.setData(Uri.parse("package:"
									+ cacheBean.packageName));
							// startActivity(intent);
							startActivityForResult(intent, REQUEST_INFO_CODE);
						}
					});

			return convertView;
		}

	}

	class ViewHolder {
		ImageView iv_cache_img, iv_cache_clear_bt;
		TextView tv_cache_title, tv_cache_sd;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		scan();
	}

	protected void setpackagename(final String packageName) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ApplicationInfo applicationInfo = pm.getApplicationInfo(
							packageName, 0);
					String string = applicationInfo.loadLabel(pm).toString();
					Drawable loadIcon = applicationInfo.loadIcon(pm);
					icon.setImageDrawable(loadIcon);
					title.setText(string);

				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	private void setAnimationLine() {
		// TODO Auto-generated method stub
		TranslateAnimation translateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0, Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 1);
		translateAnimation.setDuration(500);
		translateAnimation.setRepeatMode(Animation.REVERSE);
		translateAnimation.setRepeatCount(Animation.INFINITE);
		line.startAnimation(translateAnimation);
	}

	private class MyIPackageDataObserver extends IPackageDataObserver.Stub {

		@Override
		public void onRemoveCompleted(String packageName, boolean succeeded)
				throws RemoteException {
			// TODO Auto-generated method stub

		}

	}
}
