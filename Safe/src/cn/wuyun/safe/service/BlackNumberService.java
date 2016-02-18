package cn.wuyun.safe.service;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import cn.wuyun.safe.dao.BlackNumberContants;
import cn.wuyun.safe.dao.db.BlackNumberMethodDao;
import cn.wuyun.safe.receiver.SmsReceiver;

public class BlackNumberService extends Service {

	private BlackNumberMethodDao blackdao;
	private SmsReceiver smsReceiver;
	private TelephonyManager telephonyManager;
	private MyPhoneStateListener myPhoneStateListener;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 创建服务器的同时，注册短信监听事件和手机监听
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		blackdao = new BlackNumberMethodDao(getApplicationContext());
		smsReceiver = new SmsReceiver();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.setPriority(1000);
		intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

		registerReceiver(smsReceiver, intentFilter);

		telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		myPhoneStateListener = new MyPhoneStateListener();
		telephonyManager.listen(myPhoneStateListener,
				PhoneStateListener.LISTEN_CALL_STATE);

	}
	/**
	 * 手机状态监听
	 * @author LEE
	 *
	 */
	private class MyPhoneStateListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, final String incomingNumber) {
			// TODO Auto-generated method stub
			switch (state) {
			//空闲状态
			case TelephonyManager.CALL_STATE_IDLE:

				break;
				//响铃状态
			case TelephonyManager.CALL_STATE_RINGING:
				//通过数据库查询来获取数据库中的号码
				int selectNumber = blackdao.selectNumber(incomingNumber);
				if (selectNumber == BlackNumberContants.BLACKNUMBER_MODE_PHONE
						|| selectNumber == BlackNumberContants.BLACKNUMBER_MODE_ALL) {
					// 挂断电话方法
					endCall();
						final ContentResolver contentResolver = getContentResolver();
						final Uri uri =Uri.parse("contan://call_log/");
						
						contentResolver.registerContentObserver(uri, true,new ContentObserver(new Handler()) {

							@Override
							public void onChange(boolean selfChange) {
								// TODO Auto-generated method stub
								
								contentResolver.delete(uri, "number=?", new String[]{incomingNumber});
								
								contentResolver.unregisterContentObserver(this);
								super.onChange(selfChange);
							}
							
							
						});
				}
				break;
				//通话状态
			case TelephonyManager.CALL_STATE_OFFHOOK:

				break;

			default:
				break;
			}

			super.onCallStateChanged(state, incomingNumber);
		}

	}

	/**
	 * 短信拦截
	 * 
	 * @author LEE
	 * 
	 */
	private class SMsReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Object[] object = (Object[]) intent.getExtras().get("pdus");//获取短信对象
			for (Object obj : object) {
				SmsMessage createFromPdu = SmsMessage
						.createFromPdu((byte[]) obj);

				String address = createFromPdu.getOriginatingAddress();//获取短信地址

				int selectNumber = blackdao.selectNumber(address);
				if (selectNumber == BlackNumberContants.BLACKNUMBER_MODE_PHONE
						|| selectNumber == BlackNumberContants.BLACKNUMBER_MODE_ALL) {
					abortBroadcast();

				}
			}

		}

	}
	/**
	 *注销操作
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(smsReceiver);
		telephonyManager.listen(myPhoneStateListener,
				PhoneStateListener.LISTEN_NONE);
	}
	/**
	 * 反射调用电话挂断
	 */
	public void endCall() {
		try {
			// 1.5版本之前
			// 因为ServiceManager隐藏不让使用，所以通过反射去执行ServiceManager中方法
			// 1.获取字节码文件
			// Class.forName("android.os.ServiceManager");
			Class<?> loadClass = BlackNumberService.class.getClassLoader()
					.loadClass("android.os.ServiceManager");
			// 2.获取执行的方法
			// name:方法名
			// parameterTypes : 参数类型
			Method method = loadClass.getDeclaredMethod("getService",
					String.class);
			// 3.执行方法
			// method : 方法所在的类的对象，如果方法不是静态方法，必须设置方法所在类的对象，如果是静态方法，设置为null
			// args : 方法的参数
			IBinder invoke = (IBinder) method.invoke(null,
					Context.TELEPHONY_SERVICE);
			ITelephony iTelephony = ITelephony.Stub.asInterface(invoke);
			iTelephony.endCall();// 挂断电话操作
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
