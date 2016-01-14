package cn.wuyun.safe;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import cn.wuyun.Utils.PageVersionUtils;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Splash extends Activity {

	private String UPDATEURL = "http://192.168.3.115:8080/updateinfo.html";
	private TextView tv_splash_version;
	private int code;
	private String apkurl;
	private String msg;
	private String json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
	}

	// ���ð汾
	private void initView() {
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("�汾:" + PageVersionUtils.VersionCode(this));

		upDate();
	}

	private void upDate() {
		// TODO Auto-generated method stub
		HttpUtils http = new HttpUtils(3000);

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
			}
			System.out.println(code + apkurl + msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void skipMain() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	private void Alertbiglog() {
		// TODO Auto-generated method stub

	}

}
