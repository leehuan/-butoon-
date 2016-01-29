package cn.wuyun.safe.receiver;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.Utils.SharedPreferencesUtil;
import cn.wuyun.safe.service.ProtectedService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class StartPhoneChangSim extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("手机重起了");

		TelephonyManager telephone = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String simnumber = telephone.getSimSerialNumber();
		String startSim = SharedPreferencesUtil.getString(context,
				Contants.BINDSIM, "");
		if (!TextUtils.isEmpty(startSim) && !TextUtils.isEmpty(simnumber)) {
			if (!simnumber.equals(startSim)) {

				SmsManager sms = SmsManager.getDefault();

				sms.sendTextMessage(SharedPreferencesUtil.getString(context,
						Contants.SAFENUMBER, "5556"), null, "wo bei tou", null,
						null);

			}

		}
		context.startService(new Intent(context,ProtectedService.class));
	}

}
