package cn.wuyun.safe;

import cn.wuyun.safe.dao.BlackNumberContants;
import cn.wuyun.safe.dao.BlackNumberCreateDB;
import cn.wuyun.safe.dao.db.BlackNumberMethodDao;

import com.lidroid.xutils.db.sqlite.CursorUtils.FindCacheSequence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddBlackNumberActitvity extends Activity implements
		OnClickListener {
	private Button cen;
	private Button save;
	private EditText getnumber;
	private RadioGroup all;
	private BlackNumberMethodDao blackdao;
	private Intent intent;
	private TextView titlebar;
	private String action;
	private String position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blacknumber_addblack);
		initView();
	}

	/**
	 * 控件初始化
	 */
	private void initView() {
		getnumber = (EditText) findViewById(R.id.et_blacknumber_getnumber);
		all = (RadioGroup) findViewById(R.id.rg_all);
		cen = (Button) findViewById(R.id.bt_blacknumber_cen);
		save = (Button) findViewById(R.id.bt_blacknumber_save);
		titlebar = (TextView) findViewById(R.id.tv_titlebar);
		blackdao = new BlackNumberMethodDao(getApplicationContext());

		intent = getIntent();
		action = intent.getAction();
		if ("update".equals(action)) {
			update();
		} else {

		}

		save.setOnClickListener(this);
		cen.setOnClickListener(this);

	}

	/**
	 * 获取更新意图
	 */

	/**
	 * 更新操作，获取意图，设置更新界面
	 */
	private void update() {
		// TODO Auto-generated method stub
		String number = intent.getStringExtra("number");
		int mode = intent.getIntExtra("mode", 0);
		position = intent.getStringExtra("position");
		getnumber.setText(number);
		getnumber.setEnabled(false);
		titlebar.setText("更新黑名单");
		save.setText("更新");

		int check = -1;
		switch (mode) {
		case BlackNumberContants.BLACKNUMBER_MODE_PHONE:
			check = R.id.rb_blacknumber_phone;

			break;
		case BlackNumberContants.BLACKNUMBER_MODE_SMS:
			check = R.id.rb_blacknumber_sms;

			break;
		case BlackNumberContants.BLACKNUMBER_MODE_ALL:
			check = R.id.rb_blacknumber_all;

			break;

		default:

			check = R.id.rb_blacknumber_phone;

			break;
		}
		all.check(check);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.bt_blacknumber_cen:
			finish();
			break;
		case R.id.bt_blacknumber_save:
			String number = getnumber.getText().toString().trim();
			if (TextUtils.isEmpty(number)) {
				Toast.makeText(getApplicationContext(), "输入不能为空", 0).show();
				return;
			}
			int mode = -1;
			int checkedRadioButtonId = all.getCheckedRadioButtonId();
			switch (checkedRadioButtonId) {
			case R.id.rb_blacknumber_phone:
				mode = BlackNumberContants.BLACKNUMBER_MODE_PHONE;
				break;
			case R.id.rb_blacknumber_sms:
				mode = BlackNumberContants.BLACKNUMBER_MODE_SMS;
				break;
			case R.id.rb_blacknumber_all:
				mode = BlackNumberContants.BLACKNUMBER_MODE_ALL;
				break;

			default:
				Toast.makeText(getApplicationContext(), "请选择拦截模式", 0).show();
				return;
			}
			if ("update".equals(action)) {

				int update = blackdao.updateNumber(number, mode);
				if (update != 0) {

					Intent it = new Intent();
					it.putExtra("number", number);
					it.putExtra("position", position);
					setResult(Activity.RESULT_OK, it);
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "sry,我挂了，要不您重新点一下", 0).show();
				}

			} else {
				// 向数据库中添加看是否已经包含黑名单人员，
				if (blackdao.selectNumber(number) == -1) {
					boolean addboolean = blackdao.addBlackNumber(number, mode);
					if (addboolean) {
						Intent it = new Intent();
						it.putExtra("number", number);
						it.putExtra("mode", mode);
						setResult(Activity.RESULT_OK, it);
						finish();

					} else {
						Toast.makeText(getApplicationContext(), "服务器繁忙", 0)
								.show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "用户已添加", 0).show();
				}
			}
			break;
		default:
			break;
		}

	}
}
