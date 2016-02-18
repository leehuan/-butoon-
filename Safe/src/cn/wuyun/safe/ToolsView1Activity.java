package cn.wuyun.safe;

import cn.wuyun.safe.dao.db.AddressDao;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ToolsView1Activity extends Activity {
	private EditText address;
	private Button toolsview1;
	private TextView location;
	private EditText tools;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tools_view1);
		initView();

	}

	private void initView() {
		toolsview1 = (Button) findViewById(R.id.bt_toolsview1);
		address = (EditText) findViewById(R.id.et_Tools);
		location = (TextView) findViewById(R.id.tv_tools_location);
		tools = (EditText) findViewById(R.id.et_Tools);

		tools.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				String address = s.toString();
				String address2 = AddressDao.getAddress(getApplicationContext(), address);
				location.setText(address2);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		toolsview1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String addressText = address.getText().toString().trim();
				if (!TextUtils.isEmpty(addressText)) {
					String address2 = AddressDao.getAddress(
							ToolsView1Activity.this, addressText);
					location.setText(address2);

				} else {
					Toast.makeText(getApplicationContext(), "请输入您需要查询的地址", 0)
							.show();
					Animation shake = AnimationUtils.loadAnimation(
							ToolsView1Activity.this, R.anim.shake);
					findViewById(R.id.et_Tools).startAnimation(shake);
				}
			}
		});
	}
}
