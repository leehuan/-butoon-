package cn.wuyun.safe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.PageVersionUtils;
import cn.wuyun.safe.Utils.ServiceUtil;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import cn.wuyun.safe.service.ProtectedService;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

	private String UPDATEURL = "http://192.168.3.115:8080/update.html";
	private static final int INSTALLREQUESTCODE = 100;
	private TextView tv_splash_version;
	private int code;
	private String apkurl;
	private String msg;
	private String json;
	private HttpUtils http;
	private ProgressDialog diglog;
	private InputStream open;
	private FileOutputStream fileOutputStream;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
		copyDB("address.db");
		copyDB("antivirus.db");
		if (!ServiceUtil.getService(getApplicationContext(),
				"cn.wuyun.safe.service.ProtectedService")) {
			startService(new Intent(getApplicationContext(),
					ProtectedService.class));

		}

	}

	private void copyDB(String name) {
		// TODO Auto-generated method stub
		File file = new File(getFilesDir(), name);
		if (!file.exists()) {
			AssetManager assets = getAssets();
			try {
				open = assets.open(name);
				fileOutputStream = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int len = -1;
				if ((len = open.read(bytes)) != -1) {

					fileOutputStream.write(bytes, 0, len);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (open != null) {
					try {
						open.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}
	}

	/**
	 * 初始化控件
	 */
	private void initView() {

		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("版本:" + PageVersionUtils.VersionCode(this));
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean getboolean = SharedPreferencesUtil.getboolean(
						SplashActivity.this, Contants.UPDATE, true);
				if (getboolean) {
					upDate();
				} else {
					skipMain();
				}
			}
		}, 3000);

	}

	/**
	 * 链接服务器
	 */
	private void upDate() {
		http = new HttpUtils(3000);

		http.send(HttpMethod.GET, UPDATEURL, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("hahaha");
				skipMain();
			}

			@Override
			public void onSuccess(ResponseInfo<String> result) {
				// TODO Auto-generated method stub
				System.out.println("diididid");
				String json = result.result;
				prossejson(json);
			}
		});
	}

	/**
	 * 解析服务器端的json对象
	 * 
	 * @param json
	 */
	protected void prossejson(String json) {
		// TODO Auto-generated method stub
		try {
			JSONObject js = new JSONObject(json);
			code = js.getInt("code");
			apkurl = js.getString("apkurl");
			msg = js.getString("msg");
			if (code == PageVersionUtils.VersionCode(this)) {
				skipMain();
			} else {

				Alertbiglog();
				// TODO xxie zje
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 现式意图跳转
	 */
	private void skipMain() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 设置对话框
	 */
	private void Alertbiglog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new Builder(SplashActivity.this);

		alert.setTitle("更新版本为:" + code);
		alert.setIcon(R.drawable.ic_launcher);
		alert.setMessage(msg);

		alert.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				skipMain();
			}
		});

		alert.setNegativeButton("更新", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				updateDownload();
			}
		});
		alert.setPositiveButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				skipMain();
			}
		});
		alert.show();
	}

	/**
	 * 通过链接服务器下载新版本
	 */
	protected void updateDownload() {
		// TODO Auto-generated method stub
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			diglog = new ProgressDialog(this);
			diglog.setCancelable(false);
			diglog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			diglog.show();

			http.download(apkurl, "/mnt/sdcard/safe.apk",
					new RequestCallBack() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub
							skipMain();
						}

						@Override
						public void onSuccess(ResponseInfo arg0) {
							// TODO Auto-generated method stub
							diglog.dismiss();
							installAPK();
						}

						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
							// TODO Auto-generated method stub
							super.onLoading(total, current, isUploading);
							diglog.setMax((int) total);
							diglog.setProgress((int) current);

						}

					});
		} else {

			Toast.makeText(this, "SD卡未挂载", 0).show();
		}

	}

	/**
	 * 安装下载的心APK
	 */
	protected void installAPK() {
		// TODO Auto-generated method stub
		/**
		 * <intent-filter> <action android:name="android.intent.action.VIEW" />
		 * <category android:name="android.intent.category.DEFAULT" /> <data
		 * android:scheme="content" /> <data android:scheme="file" /> <data
		 * android:mimeType="application/vnd.android.package-archive" />
		 * </intent-filter>
		 */
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/safe.apk")),
				"application/vnd.android.package-archive");

		startActivityForResult(intent, INSTALLREQUESTCODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);
		skipMain();
	}
}
