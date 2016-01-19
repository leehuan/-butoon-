package cn.wuyun.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LostFindSetup1Activity extends BaseLastFindSetup {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup);
	}
	
	
	@Override
	public boolean nextEvent() {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, LostFindSetup2Activity.class);
		startActivity(it);
		return false;
		
	}


	@Override
	public boolean preEvent() {
		return false;
		// TODO Auto-generated method stub
		
	}

	
	
}
