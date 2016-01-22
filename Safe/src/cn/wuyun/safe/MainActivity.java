package cn.wuyun.safe;

import java.util.ArrayList;
import java.util.List;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.MD5Utils;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import cn.wuyun.safe.bean.HomeGaryViewBean;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		 OnItemClickListener {

	private ImageView home_iv_log;
	private ImageView home_iv_setting;
	private TextView home_tv_msg;
	private GridView home_gv;
	private static final String[] TITLE = new String[] { "手机防盗", "骚扰拦截",
			"软件管家", "进程管理", "流量统计", "手机杀毒", "缓存清理", "常用软件" };
	private static final String[] MSG = new String[] { "远程定位手机", "全面拦截骚扰",
			"管理您的软件", "管理正在运行", "流量一目了然", "病毒无法藏身", "系统快如火箭", "常用工具大全" };
	private static final int[] IMG = new int[] { R.drawable.sjfd,
			R.drawable.srlj, R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj,
			R.drawable.sjsd, R.drawable.hcql, R.drawable.szzx };
	private List<HomeGaryViewBean> list;
	private Button bt_lastfind_aff;
	private Button bt_lastfind_cen;
	private AlertDialog dialog;
	private EditText ed_lastfind_initpassowrd;
	private EditText ed_lastfind_aginitpassowrd;
	private AlertDialog alert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		home_iv_log = (ImageView) findViewById(R.id.home_iv_log);
		home_iv_setting = (ImageView) findViewById(R.id.main_iv_settings);
		home_tv_msg = (TextView) findViewById(R.id.home_tv_msg);
		home_gv = (GridView) findViewById(R.id.home_gv);

		setLogCartoon();
		dataInput();

		home_gv.setAdapter(new MyAdapter());

		home_iv_setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goSetring();
			}
		});
		home_gv.setOnItemClickListener(this);

	}

	protected void goSetring() {
		// TODO Auto-generated method stub
		Intent it = new Intent(MainActivity.this,SettingActivity.class);
		startActivity(it);
	}

	/**
	 * 
	 * 数据填充器
	 * 
	 */
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		// 根据条目的位置获取相对于的数据
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		/**
		 * 对GripView设置，
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView.inflate(MainActivity.this,
					R.layout.activity_gridview, null);
			ImageView home_msg_log = (ImageView) view
					.findViewById(R.id.home_msg_log);
			TextView home_msg_title = (TextView) view
					.findViewById(R.id.home_msg_title);
			TextView home_msg_context = (TextView) view
					.findViewById(R.id.home_msg_context);

			HomeGaryViewBean homeGaryViewBean = list.get(position);

			home_msg_log.setImageResource(homeGaryViewBean.img);
			home_msg_title.setText(homeGaryViewBean.title);
			home_msg_context.setText(homeGaryViewBean.msg);

			return view;
		}

	}

	/**
	 * 将bean中的数据添加到集合当中
	 */
	private void dataInput() {
		// TODO Auto-generated method stub

		list = new ArrayList<HomeGaryViewBean>();
		for (int i = 0; i < IMG.length; i++) {
			HomeGaryViewBean hv = new HomeGaryViewBean();
			hv.title = TITLE[i];
			hv.msg = MSG[i];
			hv.img = IMG[i];
			list.add(hv);
		}
	}

	/**
	 * 设置属性动画
	 */
	private void setLogCartoon() {
		// TODO Auto-generated method stub
		// 对图片进行旋转的初始化
		ObjectAnimator ofFloat = ObjectAnimator.ofFloat(home_iv_log,
				"rotationY", 0f, 90f, 190f, 260f, 360f);
		// 旋转时长
		ofFloat.setDuration(3000);
		// 旋转的次数，系统默认为3次
		ofFloat.setRepeatCount(ObjectAnimator.INFINITE);
		// 旋转的方式
		ofFloat.setRepeatMode(ObjectAnimator.RESTART);
		// 开始旋转
		ofFloat.start();

	}


	/**
	 * position根据这个值可以确定是点击的那个位置 根据id判断准确的位置
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String password = SharedPreferencesUtil.getString(this,
				Contants.PASSWORD, "");
		switch (position) {
		case 0:
			if (TextUtils.isEmpty(password)) {
				onClickShowDigLog();
			} else {
				onClickInputPassword();
			}
			break;
		case 1:
			goBlackNumber();

			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:

			break;
		default:
			break;
		}

	}

	private void goBlackNumber() {
		// TODO Auto-generated method stub
		Intent it = new Intent(MainActivity.this,BlackNumberActivity.class);
		startActivity(it);
	}

	private void onClickInputPassword() {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.view_inputpassowrd, null);
		bt_lastfind_aff = (Button) view.findViewById(R.id.bt_lastfind_aff);
		bt_lastfind_cen = (Button) view.findViewById(R.id.bt_lastfind_cen);

		ed_lastfind_initpassowrd = (EditText) view
				.findViewById(R.id.ed_lastfind_initpassowrd);

		bt_lastfind_aff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String inputpassword = ed_lastfind_initpassowrd.getText().toString()
						.trim();
				String password = SharedPreferencesUtil.getString(
						getApplicationContext(), Contants.PASSWORD, "");
				if (MD5Utils.MD5UtilsPassword(inputpassword).equals(password)) {
					Toast.makeText(getApplicationContext(), "密码正确", 0).show();
					goLastFind();
					alert.dismiss();

				} else {

					Toast.makeText(getApplicationContext(), "密码错误，请重新输入", 0)
							.show();
				}
			}
		});

		bt_lastfind_cen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alert.dismiss();
			}
		});
		builder.setView(view);
		alert = builder.create();
		alert.show();

	}

	protected void goLastFind() {
		// TODO Auto-generated method stub
		boolean getboolean = SharedPreferencesUtil.getboolean(this, Contants.FIRSTENTER, true);
		if(getboolean){
		Intent it = new Intent(MainActivity.this, LostFindSetup1Activity.class);
		startActivity(it);
		}else{
			Intent it = new Intent(MainActivity.this, LostFindActivity.class);
			startActivity(it);
		}
	}

	private void onClickShowDigLog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.view_lastfindview, null);
		ed_lastfind_initpassowrd = (EditText) view
				.findViewById(R.id.ed_lastfind_initpassowrd);
		ed_lastfind_aginitpassowrd = (EditText) view
				.findViewById(R.id.ed_lastfind_aginitpassowrd);
		bt_lastfind_aff = (Button) view.findViewById(R.id.bt_lastfind_aff);
		bt_lastfind_cen = (Button) view.findViewById(R.id.bt_lastfind_cen);

		bt_lastfind_aff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String password = ed_lastfind_initpassowrd.getText().toString()
						.trim();
				String agpassword = ed_lastfind_aginitpassowrd.getText()
						.toString().trim();
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(MainActivity.this, "密码不能为空！", 0).show();
					return;
				}

				if (password.equals(agpassword)) {
					Toast.makeText(getApplicationContext(), "初始密码设置成功!", 0)
							.show();
					SharedPreferencesUtil.saveString(MainActivity.this,
							Contants.PASSWORD,
							MD5Utils.MD5UtilsPassword(password));
					dialog.dismiss();
				} else {
					Toast.makeText(getApplicationContext(), "两次输入密码不一致！", 0)
							.show();
				}

			}
		});
		bt_lastfind_cen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.setView(view);
		dialog = builder.create();
		dialog.show();
	}

}
