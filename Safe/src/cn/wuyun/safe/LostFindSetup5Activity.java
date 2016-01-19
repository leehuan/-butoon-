package cn.wuyun.safe;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;

public class LostFindSetup5Activity extends BaseLastFindSetup {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup5);
	}

	@Override
	public boolean  nextEvent() {
		// TODO Auto-generated method stub
		SharedPreferencesUtil.saveboolean(this, Contants.FIRSTENTER, false);
		Intent it = new Intent(this, LostFindActivity.class);
		startActivity(it);
		return false;
	}

	@Override
	public boolean  preEvent() {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, LostFindSetup4Activity.class);
		startActivity(it);
		return false;
	}
}
