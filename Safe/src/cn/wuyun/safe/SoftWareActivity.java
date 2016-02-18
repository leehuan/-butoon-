package cn.wuyun.safe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.wuyun.safe.bean.AppInfo;
import cn.wuyun.safe.engine.AppEngine;
import cn.wuyun.safe.view.Appmarger;
import cn.wuyun.safe.view.CustomProgressbar;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SoftWareActivity extends Activity implements
		android.view.View.OnClickListener {

	private static final int UNINSTALL_CODE = 0;
	private Appmarger memory;
	private Appmarger sdcard;
	private ListView applications;
	private AppEngine app;
	private List<AppInfo> list;
	private TextView count;
	private ListView applications2;
	private PopupWindow popupWindow;
	private List<AppInfo> system;
	private List<AppInfo> userd;
	private AppInfo appInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appmanager);
		initView();
		setMemory();

	}

	private void initView() {
		// TODO Auto-generated method stub

		memory = (Appmarger) findViewById(R.id.cp_appmanager_memory);
		sdcard = (Appmarger) findViewById(R.id.cp_appmanager_sd);
		applications = (ListView) findViewById(R.id.lv_appmanager_applications);
		count = (TextView) findViewById(R.id.tv_appmanger_count);
		applications2 = (ListView) findViewById(R.id.lv_appmanager_applications);

		fillDate();
		popclick();
		setlistviewscorll();
	}

	private void setlistviewscorll() {
		// TODO Auto-generated method stub
		applications.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				hidePopupWindow();
				if (system != null && userd != null) {
					if (firstVisibleItem >= userd.size() + 1) {
						count.setText("系统应用个数" + system.size() + "个");
					} else {
						count.setText("用户应用个数" + userd.size() + "个");
					}
				}

			}
		});
	}

	private void popclick() {
		// TODO Auto-generated method stub
		applications2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0 || position == userd.size() + 1) {
					return;
				}
				if (position <= userd.size()) {
					appInfo = userd.get(position - 1);
				} else {

					appInfo = system.get(position - userd.size() - 2);
				}

				hidePopupWindow();
				View contentView = View.inflate(getApplicationContext(),
						R.layout.item_sofawareclick, null);

				contentView.findViewById(R.id.ii_sofaware_uninstall)
						.setOnClickListener(SoftWareActivity.this);
				contentView.findViewById(R.id.ii_sofaware_open)
						.setOnClickListener(SoftWareActivity.this);
				contentView.findViewById(R.id.ii_sofaware_share)
						.setOnClickListener(SoftWareActivity.this);
				contentView.findViewById(R.id.ii_sofaware_uninstall)
						.setOnClickListener(SoftWareActivity.this);

				popupWindow = new PopupWindow(contentView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				popupWindow.showAsDropDown(view, 60, -view.getHeight());

			}

		});
	}

	protected void hidePopupWindow() {
		// TODO Auto-generated method stub
		if (popupWindow != null) {
			popupWindow.dismiss();
			popupWindow = null;
		}
	}

	private void fillDate() {
		app = new AppEngine();
		new Thread() {

			public void run() {
				list = app.getAppmessage(getApplicationContext());
				system = new ArrayList<AppInfo>();
				userd = new ArrayList<AppInfo>();

				for (AppInfo appInfo : list) {
					if (appInfo.isSystem) {
						system.add(appInfo);
					} else {
						userd.add(appInfo);
					}
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						MyAdapter myadapter = new MyAdapter();
						applications.setAdapter(myadapter);
						count.setText("用户程序(" + userd.size() + ")个");

					}
				});
			};

		}.start();
	}

	private void setMemory() {
		// TODO Auto-generated method stub
		// 获取内存
		File dataDirectory = Environment.getDataDirectory();
		// 查询剩余内存
		long freeSpace = dataDirectory.getFreeSpace();
		// 查看所有内存
		long totalSpace = dataDirectory.getTotalSpace();
		// 得到已用内存
		long userSpace = totalSpace - freeSpace;
		// 设置标题

		memory.title("内存:  ");
		// 将b转换成成mb
		String formatFileSize = Formatter.formatFileSize(
				getApplicationContext(), userSpace);
		memory.userd(formatFileSize + "已用");
		String formatFileSize2 = Formatter.formatFileSize(
				getApplicationContext(), freeSpace);
		memory.totaling(formatFileSize2 + "可用");
		// 获得进度
		int mpro = (int) (userSpace * 100f / totalSpace + 0.5f);
		memory.prototal(mpro);

		File externalStorageDirectory = Environment
				.getExternalStorageDirectory();
		long freeSpace2 = externalStorageDirectory.getFreeSpace();
		long totalSpace2 = externalStorageDirectory.getTotalSpace();
		long userSpance2 = totalSpace2 - freeSpace2;

		sdcard.title("sd:    ");

		String formatFileSize3 = Formatter.formatFileSize(
				getApplicationContext(), userSpance2);
		sdcard.userd(formatFileSize3 + "已用");
		String formatFileSize4 = Formatter.formatFileSize(
				getApplicationContext(), totalSpace2);
		sdcard.totaling(formatFileSize4 + "可用");
		int sdpro = (int) (userSpance2 * 100f / totalSpace2 + 0.5f);
		sdcard.prototal(sdpro);

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return system.size() + userd.size() + 2;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (position == 0) {
				// 用户程序
				TextView textView = new TextView(getApplicationContext());
				textView.setText("用户程序(" + userd.size() + "个)");
				textView.setTextSize(15);
				// color.black,在android内部十六进制实现方式展示不一样
				textView.setTextColor(Color.BLACK);
				textView.setBackgroundColor(Color.parseColor("#D6D3CE"));
				textView.setPadding(8, 8, 8, 8);
				return textView;
			} else if (position == userd.size() + 1) {
				TextView textView = new TextView(getApplicationContext());
				textView.setText("系统程序(" + system.size() + "个)");
				textView.setTextSize(15);
				textView.setTextColor(Color.BLACK);
				textView.setBackgroundColor(Color.parseColor("#D6D3CE"));
				textView.setPadding(8, 8, 8, 8);
				return textView;
			}
			ViewHolder viewholder;
			if (convertView == null || convertView instanceof TextView) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.message_listview, null);
				viewholder = new ViewHolder();
				viewholder.img = (ImageView) convertView
						.findViewById(R.id.iv_message_img);
				viewholder.title = (TextView) convertView
						.findViewById(R.id.tv_message_title);
				viewholder.message = (TextView) convertView
						.findViewById(R.id.tv_message_message);
				viewholder.sd = (TextView) convertView
						.findViewById(R.id.tv_message_sd);
				convertView.setTag(viewholder);
			} else {
				viewholder = (ViewHolder) convertView.getTag();
			}

			AppInfo appInfo;
			if (position <= userd.size()) {
				appInfo = userd.get(position - 1);
			} else {
				appInfo = system.get(position - userd.size() - 2);
			}

			viewholder.img.setImageDrawable(appInfo.icon);
			viewholder.title.setText(appInfo.packageName);
			viewholder.message.setText(appInfo.name);
			viewholder.sd.setText(appInfo.isSD ? "SD卡" : "手机内存");

			return convertView;
		}

	}

	class ViewHolder {

		ImageView img;
		TextView title, message, sd;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.ii_sofaware_info:
			sofawareInfo();
			break;
		case R.id.ii_sofaware_open:
			sofawareOpen();
			break;
		case R.id.ii_sofaware_share:
			sofawareShare();
			break;
		case R.id.ii_sofaware_uninstall:
			sofawareUninstall();
			break;

		default:
			break;
		}
	}

	private void sofawareShare() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction("android.intent.action.SEND");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "发现一个牛逼的软件：" + appInfo.name
				+ ",下载路径：www.baidu.com,自己去搜...");
		startActivity(intent);
	
	}

	private void sofawareOpen() {
		// TODO Auto-generated method stub
		PackageManager pm = getPackageManager();
		Intent launchIntentForPackage = pm
				.getLaunchIntentForPackage(appInfo.packageName);
		if (launchIntentForPackage != null) {
			startActivity(launchIntentForPackage);
		}
	}

	private void sofawareUninstall() {
		// TODO Auto-generated method stub
		/**
		 * <intent-filter> <action android:name="android.intent.action.VIEW" />
		 * <action android:name="android.intent.action.DELETE" /> <category
		 * android:name="android.intent.category.DEFAULT" /> <data
		 * android:scheme="package" /> </intent-filter>
		 */
		if (!appInfo.packageName.equals(getPackageName())) {
			if (!appInfo.isSystem) {
				Intent it = new Intent();
				it.setAction("android.intent.action.DELETE");
				it.addCategory("android.intent.category.DEFAULT");
				it.setData(Uri.parse("package:" + appInfo.packageName));
				startActivityForResult(it, UNINSTALL_CODE);
			} else {
				Toast.makeText(getApplicationContext(), "系统应用，删除请获取ROOT权限", 0)
						.show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "杜绝自杀！", 0).show();

		}
	}

	private void sofawareInfo() {
		// TODO Auto-generated method stub
		
		
		Intent intent = new Intent();
		intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:" + appInfo.packageName));
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		fillDate();
	}

}
