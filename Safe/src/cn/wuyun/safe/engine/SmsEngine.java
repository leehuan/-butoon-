package cn.wuyun.safe.engine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class SmsEngine {
	private List<SmsBean> list;

	public void getAllSms(Context context, ProgressDialog progress) {

		list = new ArrayList<SmsBean>();
		ContentResolver contentResolver = context.getContentResolver();
		Uri uri = Uri.parse("content://sms");

		Cursor query = contentResolver.query(uri, new String[] { "address",
				"date", "type", "body" }, null, null, null);
		progress.setMax(query.getCount());
		int pgress=0;
		
		if (query != null) {
			while (query.moveToNext()) {
				String address = query.getString(0);
				String date = query.getString(1);
				String type = query.getString(2);
				String body = query.getString(3);
				SmsBean smssave = new SmsBean(address, date, type, body);
				list.add(smssave);
				pgress++;
				progress.setProgress(pgress);
			}

		}
		
		Gson gson = new Gson();
		String json = gson.toJson(list);
		try {
			FileWriter filewaWriter = new FileWriter("mnt/sdcard/sms.txt");
			filewaWriter.write(json);
			filewaWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static class SmsBean {

		public String address;
		public String date;
		public String type;
		public String body;

		public SmsBean(String address, String date, String type, String body) {
			super();
			this.address = address;
			this.date = date;
			this.type = type;
			this.body = body;
		}

	}
	
}
