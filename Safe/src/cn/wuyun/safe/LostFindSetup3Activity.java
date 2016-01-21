package cn.wuyun.safe;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LostFindSetup3Activity extends BaseLastFindSetup implements
		OnClickListener {
	private static final int GETRETURNPHONE = 100;
	private TextView select;
	private EditText safenumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		initView();
	}

	private void initView() {
		select = (TextView) findViewById(R.id.tv_setup3_selectsafenumber);
		safenumber = (EditText) findViewById(R.id.ET_setup3_safenumber);

		String number = SharedPreferencesUtil.getString(this,
				Contants.SAFENUMBER, "");

		if (!TextUtils.isEmpty(number)) {
			safenumber.setText(number);
		}

		select.setOnClickListener(this);
	}

	@Override
	public boolean nextEvent() {
		// TODO Auto-generated method stub
		
		String number = safenumber.getText().toString().trim();
		
		if (TextUtils.isEmpty(number)) {

			Toast.makeText(this, "安全号码不能为空，请选择", 0).show();
			return true;
		}

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
			startActivityForResult(it, GETRETURNPHONE);

			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {

			String number = data.getStringExtra("number");
			safenumber.setText(number);
			SharedPreferencesUtil.saveString(this, Contants.SAFENUMBER, number);
		}
	}

}
