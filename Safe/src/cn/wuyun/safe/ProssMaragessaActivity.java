package cn.wuyun.safe;

import java.io.File;
import java.util.List;

import cn.wuyun.safe.bean.AppInfo;
import cn.wuyun.safe.engine.AppEngine;
import cn.wuyun.safe.view.Appmarger;
import cn.wuyun.safe.view.CustomProgressbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProssMaragessaActivity extends Activity {

	private Appmarger memory;
	private Appmarger sdcard;
	private ListView applications;
	private AppEngine app;
	private List<AppInfo> list;
	private TextView count;
	private ListView applications2;

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
	}

	private void popclick() {
		// TODO Auto-generated method stub
		applications2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}

		});
	}

	private void fillDate() {
		app = new AppEngine();
		new Thread() {

			public void run() {
				list = app.getAppmessage(getApplicationContext());
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						MyAdapter myadapter = new MyAdapter();
						applications.setAdapter(myadapter);
						count.setText("用户程序(" + list.size() + ")个");
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

		private ImageView img;
		private TextView title;
		private TextView message;
		private TextView sd;

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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.message_listview, null);
				img = (ImageView) convertView.findViewById(R.id.iv_message_img);
				title = (TextView) convertView
						.findViewById(R.id.tv_message_title);
				message = (TextView) convertView
						.findViewById(R.id.tv_message_message);
				sd = (TextView) convertView.findViewById(R.id.tv_message_sd);
			}

			AppInfo appInfo = list.get(position);

			img.setImageDrawable(appInfo.icon);
			title.setText(appInfo.packageName);
			message.setText(appInfo.name);
			sd.setText(appInfo.isSD ? "SD卡" : "手机内存");

			return convertView;
		}

	}
}
