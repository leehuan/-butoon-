package cn.wuyun.safe;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import cn.wuyun.safe.view.SettingView;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class SettingActivity extends Activity {
	
	private SettingView toggle;

	
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
		update();
	}

	private void update() {
		// TODO Auto-generated method stub
		
		boolean getboolean = SharedPreferencesUtil.getboolean(SettingActivity.this, Contants.UPDATE, true);
		if(getboolean){
			toggle.setToggle(true);
		}else{
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
