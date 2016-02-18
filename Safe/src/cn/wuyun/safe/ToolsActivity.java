package cn.wuyun.safe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.wuyun.safe.engine.SmsEngine;
import cn.wuyun.safe.engine.SmsEngine.SmsBean;
import cn.wuyun.safe.view.SettingView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ToolsActivity extends Activity implements OnClickListener {

	private SettingView location;
	private SettingView commonnuber;
	private SettingView smssave;
	private SettingView getsms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toolsview);
		initView();
	}

	private void initView() {
		location = (SettingView) findViewById(R.id.sv_tools_location);
		commonnuber = (SettingView) findViewById(R.id.sv_tools_setting_commonnumber);
		smssave = (SettingView) findViewById(R.id.tools_sms_save);
		getsms = (SettingView) findViewById(R.id.tools_getsms);

		location.setOnClickListener(this);
		commonnuber.setOnClickListener(this);
		smssave.setOnClickListener(this);
		getsms.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.sv_tools_location:
			Intent it = new Intent(ToolsActivity.this, ToolsView1Activity.class);
			startActivity(it);
			break;
		case R.id.sv_tools_setting_commonnumber:
			Intent ittools = new Intent(ToolsActivity.this,
					ToolsView2Activity.class);
			startActivity(ittools);
			break;
		case R.id.tools_sms_save:
			final ProgressDialog dialog = new ProgressDialog(ToolsActivity.this);
			dialog.setCancelable(false);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.show();
			new Thread() {

				public void run() {

					SmsEngine sms = new SmsEngine();
					sms.getAllSms(getApplicationContext(), dialog);
					dialog.dismiss();
				};
			}.start();

			break;
		case R.id.tools_getsms:
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(
						"/mnt/sdcard/sms.txt")));
				String readLine = br.readLine();
				Gson gson = new Gson();
				List<SmsBean> fromJson = gson.fromJson(readLine,
						new TypeToken<List<SmsBean>>() {
						}.getType());
				for (SmsBean smsBean : fromJson) {
					ContentResolver contentResolver1 = getContentResolver();
					Uri uri = Uri.parse("content://sms");
					ContentValues values = new ContentValues();
					values.put("address", smsBean.address);
					values.put("date", smsBean.date);
					values.put("type", smsBean.type);
					values.put("body", smsBean.body);
					contentResolver1.insert(uri, values);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		default:
			break;
		}
	}

}
