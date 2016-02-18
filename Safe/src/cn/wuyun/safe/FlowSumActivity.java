package cn.wuyun.safe;

import java.util.List;

import cn.wuyun.safe.bean.AppInfo;
import cn.wuyun.safe.engine.AppEngine;
import android.app.Activity;
import android.net.TrafficStats;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FlowSumActivity extends Activity {

	private ListView flowsum;
	private List<AppInfo> appmessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flowsum);
		initView();
	}

	private void initView() {
		flowsum = (ListView) findViewById(R.id.lv_flowsum_list);
		filldate();
	}

	private void filldate() {
		// TODO Auto-generated method stub
		new Thread() {

			public void run() {
				appmessage = AppEngine.getAppmessage(getApplicationContext());
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						flowsum.setAdapter(new MyAdapter());
					}
				});
			};

		}.start();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appmessage.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return appmessage.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewholder;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.listview_flowsum, null);
				viewholder = new ViewHolder();
				viewholder.img = (ImageView) convertView
						.findViewById(R.id.iv_flowsum_img);
				viewholder.title = (TextView) convertView
						.findViewById(R.id.tv_flowsum_titile);
				viewholder.upload = (TextView) convertView
						.findViewById(R.id.tv_flowsum_upload);
				viewholder.download = (TextView) convertView
						.findViewById(R.id.tv_flowsum_download);

				convertView.setTag(viewholder);

			} else {

				viewholder = (ViewHolder) convertView.getTag();
			}

			AppInfo appInfo = appmessage.get(position);

			viewholder.img.setImageDrawable(appInfo.icon);
			viewholder.title.setText(appInfo.packageName);
			long uidRxBytes = TrafficStats.getUidRxBytes(appInfo.uid);// 下载
			long uidTxBytes = TrafficStats.getUidTxBytes(appInfo.uid);// 上传
			viewholder.upload.setText("上传流量:"
					+ Formatter.formatFileSize(getApplicationContext(),
							uidTxBytes));
			viewholder.download.setText("下载流量:"
					+ Formatter.formatFileSize(getApplicationContext(),
							uidRxBytes));

			return convertView;
		}
	}

	class ViewHolder {
		ImageView img;
		TextView title, upload, download;
	}
}
