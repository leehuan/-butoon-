package cn.wuyun.safe.receiver;

import cn.wuyun.safe.R;
import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);

		ComponentName componentName = new ComponentName(context,
				AdminReceiver.class);
		Object[] object = (Object[]) intent.getExtras().get("pdus");

		for (Object ob : object) {
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) ob);
			String body = sms.getMessageBody();
			String address = sms.getOriginatingAddress();

			String safanumber = SharedPreferencesUtil.getString(context,
					Contants.SAFENUMBER, "5556");
			if (address.equals(safanumber)) {
				if ("#*location*#".equals(body)) {

					abortBroadcast();

				} else if ("#*wipedate*#".equals(body)) {
					if (devicePolicyManager.isAdminActive(componentName)) {
						devicePolicyManager.wipeData(0);

					}
					abortBroadcast();
				} else if ("#*alarm*#".equals(body)) {

					MediaPlayer md = MediaPlayer.create(context, R.raw.ylzs);

					md.setVolume(1.0f, 1.0f);
					md.setLooping(true);
					md.start();
					abortBroadcast();
				} else if ("#*lockscreen*#".equals(body)) {
					if (devicePolicyManager.isAdminActive(componentName)) {

						devicePolicyManager.resetPassword("123", 0);
						devicePolicyManager.lockNow();

					}
					abortBroadcast();
				}

			} else {
				abortBroadcast();
			}
		}
	}
}
