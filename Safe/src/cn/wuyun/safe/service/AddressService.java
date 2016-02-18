package cn.wuyun.safe.service;

import cn.wuyun.safe.R;
import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import cn.wuyun.safe.dao.db.AddressDao;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class AddressService extends Service {

	private TelephonyManager telephonyManager;
	private WindowManager windowManager;
	private View view;
	private LayoutParams layoutParams;
	private OutGoingPhone outgoingphone;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		outgoingphone = new OutGoingPhone();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.NEW_OUTGOING_CALL");

		registerReceiver(outgoingphone, filter);

		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		telephonyManager.listen(new myPhoneStateListener(),
				PhoneStateListener.LISTEN_CALL_STATE);

	}

	/**
	 * 外拨电话监听事件
	 */

	public class OutGoingPhone extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			// 通过调用Addressdao来获取数据库中对应的地理位置
			String address = AddressDao.getAddress(getApplicationContext(),
					number);
			// 进行判断，如果部位空就调用土司
			if (!TextUtils.isEmpty(address)) {
				Toastshow(address);
			}
			
		}
	}

	/**
	 * 手机状态监听事件
	 * 
	 * @author LEE
	 * 
	 */
	private class myPhoneStateListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			// 空闲状态
			case TelephonyManager.CALL_STATE_IDLE:
				Dismisstoast();
				break;
			// 响铃状态
			case TelephonyManager.CALL_STATE_RINGING:
				String address = AddressDao.getAddress(getApplicationContext(),
						incomingNumber);
				if (!TextUtils.isEmpty(address)) {

					Toastshow(address);
				}
				break;
			// 通话状态
			case TelephonyManager.CALL_STATE_OFFHOOK:
				
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 注销广播
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		telephonyManager.listen(new myPhoneStateListener(),
				PhoneStateListener.LISTEN_NONE);
		unregisterReceiver(outgoingphone);
	}

	/**
	 * 自定义土司
	 * 
	 * @param address
	 */
	public void Toastshow(String address) {
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		view = View.inflate(getApplicationContext(), R.layout.address_view,
				null);
		TextView addressShow = (TextView) view
				.findViewById(R.id.tv_address_view);
		int int1 = SharedPreferencesUtil.getInt(getApplicationContext(), Contants.ENSURE, R.drawable.shape_customdialog_bg_normal);
		
		addressShow.setBackgroundResource(int1);
		layoutParams = new WindowManager.LayoutParams();
		layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;// 布局文件中高度包裹内容一致
		layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;// 宽度包裹内容
		layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE// 没有焦点
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE // 禁止触摸
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON; // 保持屏幕常亮
		layoutParams.format = PixelFormat.TRANSLUCENT; // 设置透明背景
		layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST; // 设置控件的类型是toast类型

		layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
		layoutParams.x = 100;
		layoutParams.y = 100;
		setTouch();
		windowManager.addView(view, layoutParams);
	}

	private void setTouch() {
		// TODO Auto-generated method stub
		view.setOnTouchListener(new OnTouchListener() {

			private int startx;
			private int starty;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startx = (int) event.getRawX();
					starty = (int) event.getRawY();

					break;
				case MotionEvent.ACTION_MOVE:
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();
					// 3.计算偏移量
					int dX = newX - startx;
					int dY = newY - starty;
					// 4.将控件移动相应的距离，更改控件的位置
					layoutParams.x += dX;
					layoutParams.y += dY;
					// 更新控件的位置
					// updateViewLayout : 更新控件的操作，params：控件设置的最新属性
					windowManager.updateViewLayout(view, layoutParams);
					// 5.更新开始的位置
					startx = newX;
					starty = newY;
					
					break;
				case MotionEvent.ACTION_UP:

					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	public void Dismisstoast() {
		if (windowManager != null && view != null) {
			windowManager.removeView(view);

			// 为下一次显示做准备
			windowManager = null;
			view = null;
		}
	}

}
