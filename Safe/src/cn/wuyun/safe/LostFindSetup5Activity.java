package cn.wuyun.safe;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class LostFindSetup5Activity extends BaseLastFindSetup implements
		OnCheckedChangeListener {
	private CheckBox ischeck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup5);
		initView();
	}

	private void initView() {
		ischeck = (CheckBox) findViewById(R.id.ch_setup5_ischeck);

		ischeck.setOnCheckedChangeListener(this);

		boolean getboolean = SharedPreferencesUtil.getboolean(this,
				Contants.ISCHECK, false);

		ischeck.setChecked(getboolean);

	}

	@Override
	public boolean nextEvent() {
		// TODO Auto-generated method stub
		boolean getboolean = SharedPreferencesUtil.getboolean(this,Contants.ISCHECK, false);
		if(!getboolean){
			Toast.makeText(this, "点击选择开启安全服务", 0).show();
			return true;
		}
		Intent it = new Intent(this, LostFindActivity.class);
		startActivity(it);
		return false;
	}

	@Override
	public boolean preEvent() {
		// TODO Auto-generated method stub
		
		Intent it = new Intent(this, LostFindSetup4Activity.class);
		startActivity(it);
		return false;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub\
		SharedPreferencesUtil.saveboolean(this, Contants.ISCHECK, isChecked);

	}
}
