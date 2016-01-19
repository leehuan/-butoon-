package cn.wuyun.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LostFindSetup3Activity extends BaseLastFindSetup implements
		OnClickListener {
	private TextView select;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		initView();
	}

	private void initView() {
		select = (TextView) findViewById(R.id.tv_setup3_selectsafenumber);

		select.setOnClickListener(this);
	}

	@Override
	public boolean nextEvent() {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, LostFindSetup4Activity.class);
		startActivity(it);
		return false;
	}

	@Override
	public boolean preEvent() {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, LostFindSetup2Activity.class);
		startActivity(it);
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.tv_setup3_selectsafenumber:

			Intent it = new Intent(this, SelectSafeNumberActivity.class);
			startActivity(it);

			break;

		default:
			break;
		}
	}

}
