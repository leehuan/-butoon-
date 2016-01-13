package cn.wuyun.safe;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import cn.wuyun.Utils.PackUtils;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Splash extends Activity {

	private TextView tv_splash_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initview();

	}

	private void initview() {
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		// 注意在使用settext的时候一定要给其传递一个值，直接获取会报错
		tv_splash_version.setText("版本:" + PackUtils.versionCode(this));

		update();
	}

	private void update() {
		// TODO Auto-generated method stub
		HttpUtils http = new HttpUtils(3000);
		http.send(HttpMethod.GET, "xxx", new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

}
