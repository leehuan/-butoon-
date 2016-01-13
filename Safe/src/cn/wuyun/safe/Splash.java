package cn.wuyun.safe;

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
		//注意在使用settext的时候一定要给其传递一个值，直接获取会报错
		tv_splash_version.setText("版本:"+PackUtils.versionCode(this));
		
		
		update();
	}

	private void update() {
		// TODO Auto-generated method stub
		
	}

}
