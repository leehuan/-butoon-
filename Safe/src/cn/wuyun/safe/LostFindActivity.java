package cn.wuyun.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LostFindActivity extends Activity {
	private TextView tv_lostfind_initall;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lastfind);
		initView();
	}

	private void initView() {
			tv_lostfind_initall = (TextView) findViewById(R.id.tv_lostfind_initall);
			tv_lostfind_initall.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					enterFirstSetup();
				}
			});
		}

	protected void enterFirstSetup() {
		// TODO Auto-generated method stub
		Intent it = new Intent(this,LostFindSetup1Activity.class);
		startActivity(it);
		finish();
	}
}
