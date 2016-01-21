package cn.wuyun.safe;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import cn.wuyun.safe.receiver.AdminReceiver;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LostFindSetup4Activity extends BaseLastFindSetup {
	protected static final int REQUEST_CODE_ENABLE_ADMIN = 20;
	private RelativeLayout getAdm;
	private DevicePolicyManager devicePolicyManager;
	private ComponentName componentName;
	private ImageView adminImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		initView();
	}
	
	
	private void initView() {
		getAdm = (RelativeLayout) findViewById(R.id.rl_setup4_getAdm);
		adminImg = (ImageView) findViewById(R.id.iv_setup4_adminimg);
		devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(getApplicationContext(),
				AdminReceiver.class);

		if(devicePolicyManager.isAdminActive(componentName)){
			adminImg.setImageResource(R.drawable.admin_activated);
		}else{
			adminImg.setImageResource(R.drawable.admin_inactivated);
		}
				
		
		
		getAdm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (devicePolicyManager.isAdminActive(componentName)) {

					devicePolicyManager.resetPassword("", 0);
					devicePolicyManager.removeActiveAdmin(componentName);
					adminImg.setImageResource(R.drawable.admin_inactivated);
					

				} else {
					Intent it = new Intent(
							DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
					it.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
							componentName);
					it.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
							"德莱安全卫士");
					startActivityForResult(it, REQUEST_CODE_ENABLE_ADMIN);
				}
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_ENABLE_ADMIN) {
			if (devicePolicyManager.isAdminActive(componentName)) {
				adminImg.setImageResource(R.drawable.admin_activated);
			} else {
				adminImg.setImageResource(R.drawable.admin_inactivated);
			}

		}
	}

	@Override
	public boolean nextEvent() {
		// TODO Auto-generated method stub
		if(!devicePolicyManager.isAdminActive(componentName)){
			Toast.makeText(getApplicationContext(), "请获取管理权限", 0).show();
			return true;
		}
		Intent it = new Intent(this, LostFindSetup5Activity.class);
		startActivity(it);
		return false;
	}

	@Override
	public boolean preEvent() {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, LostFindSetup3Activity.class);
		startActivity(it);
		return false;
	}
}
