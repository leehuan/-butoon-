package cn.wuyun.safe;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LostFindActivity extends Activity {
	private TextView tv_lostfind_initall;
	private TextView safenumber;
	private ImageView ischeck;
	private RelativeLayout lostfindcheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lastfind);
		initView();
	}

	private void initView() {
		tv_lostfind_initall = (TextView) findViewById(R.id.tv_lostfind_initall);
		ischeck = (ImageView) findViewById(R.id.iv_lastfind_ischeck);
		lostfindcheck = (RelativeLayout) findViewById(R.id.rl_lostfind_ischeck);
		safenumber = (TextView) findViewById(R.id.tv_lostfind_safenumber);

		tv_lostfind_initall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				enterFirstSetup();
			}
		});
		initSafeNumber();
		isCheck();
		lostFindCheck();

	}

	private void lostFindCheck() {
		// TODO Auto-generated method stub
		lostfindcheck.setOnClickListener(new OnClickListener() {

			private boolean checkboolean;

			@Override
			public void onClick(View v) {
				checkboolean = SharedPreferencesUtil.getboolean(
						getApplicationContext(), Contants.ISCHECK, false);
				if (checkboolean) {
					SharedPreferencesUtil.saveboolean(getApplicationContext(),
							Contants.ISCHECK, false);
					ischeck.setImageResource(R.drawable.unlock);
				} else {
					SharedPreferencesUtil.saveboolean(getApplicationContext(),
							Contants.ISCHECK, true);
					ischeck.setImageResource(R.drawable.lock);
				}
			}
		});
	}

	private void isCheck() {
		// TODO Auto-generated method stub
		boolean getboolean = SharedPreferencesUtil.getboolean(this,
				Contants.ISCHECK, false);
		if (getboolean) {
			ischeck.setImageResource(R.drawable.lock);
		} else {
			ischeck.setImageResource(R.drawable.unlock);
		}
	}

	private void initSafeNumber() {
		// TODO Auto-generated method stub
		String number = SharedPreferencesUtil.getString(this,
				Contants.SAFENUMBER, "");
		safenumber.setText(number);
	}

	protected void enterFirstSetup() {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, LostFindSetup1Activity.class);
		startActivity(it);
		finish();
	}
}
