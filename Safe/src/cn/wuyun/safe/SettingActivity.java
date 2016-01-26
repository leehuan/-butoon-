package cn.wuyun.safe;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.ServiceUtil;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import cn.wuyun.safe.service.AddressService;
import cn.wuyun.safe.service.BlackNumberService;
import cn.wuyun.safe.view.SettingView;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingActivity extends Activity {

	private SettingView toggle;
	private SettingView blacknumber;
	private SettingView address;

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
		update();
		blackNumber();
		addressshow();
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

}
