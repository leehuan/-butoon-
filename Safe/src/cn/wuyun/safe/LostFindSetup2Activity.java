package cn.wuyun.safe;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LostFindSetup2Activity extends BaseLastFindSetup {

	private ImageView changeimg;
	private RelativeLayout bindsim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);

		initView();

	}

	private void initView() {
		bindsim = (RelativeLayout) findViewById(R.id.rl_setup_bindsim);
		changeimg = (ImageView) findViewById(R.id.iv_setup2_changeimg);

		String initbind = SharedPreferencesUtil.getString(this,
				Contants.BINDSIM, "");
		if (TextUtils.isEmpty(initbind)) {
			changeimg.setImageResource(R.drawable.unlock);

		} else {
			changeimg.setImageResource(R.drawable.lock);
		}

		bindsim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String sim = SharedPreferencesUtil.getString(
						LostFindSetup2Activity.this, Contants.BINDSIM, "");
				if (TextUtils.isEmpty(sim)) {
					/**
					 * 绑定SIM
					 */
					TelephonyManager telephone = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					String simNumber = telephone.getSimSerialNumber();
					SharedPreferencesUtil.saveString(
							LostFindSetup2Activity.this, Contants.BINDSIM,
							simNumber);
					changeimg.setImageResource(R.drawable.lock);

				} else {
					/**
					 * 解绑SIM
					 */
					SharedPreferencesUtil.saveString(
							LostFindSetup2Activity.this, Contants.BINDSIM, "");
					changeimg.setImageResource(R.drawable.unlock);
				}

			}
		});
	}

	@Override
	public boolean nextEvent() {
		// TODO Auto-generated method stub
		String bind = SharedPreferencesUtil.getString(this, Contants.BINDSIM,
				"");
		if (TextUtils.isEmpty(bind)) {
			Toast.makeText(this, "请先绑定SIM卡", 0).show();
			return true;
		}
		Intent it = new Intent(this, LostFindSetup3Activity.class);
		startActivity(it);
		return false;
	}

	@Override
	public boolean preEvent() {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, LostFindSetup1Activity.class);
		startActivity(it);
		return false;
	}
}
