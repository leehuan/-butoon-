package cn.wuyun.safe;

import java.util.ArrayList;
import java.util.List;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.ServiceUtil;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import cn.wuyun.safe.service.AddressService;
import cn.wuyun.safe.service.BlackNumberService;
import cn.wuyun.safe.view.ChangeBgDialog;
import cn.wuyun.safe.view.SettingView;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SettingActivity extends Activity {

	private SettingView toggle;
	private SettingView blacknumber;
	private SettingView address;
	private SettingView addressbg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		InitView();
	}

	private void InitView() {
		// 相当于创建一个settingView对象
		toggle = (SettingView) findViewById(R.id.sv_setting_update);
		blacknumber = (SettingView) findViewById(R.id.sv_setting_blacknumber);
		address = (SettingView) findViewById(R.id.setting_address);
		addressbg = (SettingView) findViewById(R.id.setting_address_bg);
		update();
		blackNumber();
		addressshow();
		changaddressbg();
	}

	private void changaddressbg() {
		// TODO Auto-generated method stub
		addressbg.setOnClickListener(new OnClickListener() {

			private ChangeBgDialog dialog;

			@Override
			public void onClick(View v) {
				dialog = new ChangeBgDialog(SettingActivity.this);

				dialog.show();
				dialog.setadapter(new MyAdapterAddressBg());
				dialog.onItemChlickOne(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub

						dialog.dismiss();
						SharedPreferencesUtil.saveInt(getApplicationContext(),
								Contants.ENSURE, colorimg[position]);

					}
				});
			}
		});
	}

	private void addressshow() {
		// TODO Auto-generated method stub
		address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(SettingActivity.this,
						AddressService.class);
				if (ServiceUtil.getService(getApplicationContext(),
						"cn.wuyun.safe.service.AddressService")) {
					startService(it);

				} else {
					stopService(it);
				}
				address.toToggle();
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		if (ServiceUtil.getService(getApplicationContext(),
				"cn.wuyun.safe.service.BlackNumberService")) {
			blacknumber.setToggle(true);
		} else {
			blacknumber.setToggle(false);
		}
		if (ServiceUtil.getService(getApplicationContext(),
				"cn.wuyun.safe.service.BlackNumberService")) {
			blacknumber.setToggle(true);
		} else {
			blacknumber.setToggle(false);
		}
		super.onStart();
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void blackNumber() {
		// TODO Auto-generated method stub
		blacknumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(SettingActivity.this,
						BlackNumberActivity.class);
				if (ServiceUtil.getService(getApplicationContext(),
						"cn.wuyun.safe.service.BlackNumberService")) {
					stopService(it);
				} else {
					startService(it);
				}
				blacknumber.toToggle();
			}
		});

	}

	private void update() {
		// TODO Auto-generated method stub

		boolean getboolean = SharedPreferencesUtil.getboolean(
				SettingActivity.this, Contants.UPDATE, true);
		if (getboolean) {
			toggle.setToggle(true);
		} else {
			toggle.setToggle(false);
		}

		toggle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if(toggle.getToggle()){
				// toggle.setToggle(false);
				//
				// }else{
				// toggle.setToggle(true);
				// }

				toggle.toToggle();
				SharedPreferencesUtil.saveboolean(SettingActivity.this,
						Contants.UPDATE, toggle.getToggle());
			}
		});
	}

	private String[] colorname = new String[] { "半透明", "活力橙", "卫士蓝", "金属灰",
			"苹果绿" };
	private int[] colorimg = new int[] {
			R.drawable.shape_customdialog_bg_normal,
			R.drawable.shape_customdialog_bg_blue,
			R.drawable.shape_customdialog_bg_gray,
			R.drawable.shape_customdialog_bg_green,
			R.drawable.shape_customdialog_bg_orange };

	private class MyAdapterAddressBg extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return colorname.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return colorname[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(getApplicationContext(),
					R.layout.address_listview, null);

			ImageView img = (ImageView) view
					.findViewById(R.id.iv_addressbg_listview_img);
			ImageView ensure = (ImageView) view
					.findViewById(R.id.iv_addressbg_ensure);
			TextView colorname = (TextView) view
					.findViewById(R.id.tv_addressbg_colorname);

			img.setImageResource(colorimg[position]);
			colorname.setText(SettingActivity.this.colorname[position]);

			int int1 = SharedPreferencesUtil.getInt(getApplicationContext(),
					Contants.ENSURE, R.drawable.shape_customdialog_bg_normal);
			if (int1 == colorimg[position]) {
				ensure.setVisibility(view.VISIBLE);
			} else {
				ensure.setVisibility(view.GONE);
			}
			return view;
		}
	}

}
