package cn.wuyun.safe.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	public static String MD5UtilsPassword(String password) {

		StringBuffer sb = new StringBuffer();

		try {
			MessageDigest message = MessageDigest.getInstance("MD5");
			byte[] digest = message.digest(password.getBytes());
			for (int i = 0; i < digest.length; i++) {
				int result = digest[i] & 0xff;

				String hexString = Integer.toHexString(result);
				if (hexString.length() < 2) {
					sb.append("0");
				}

				sb.append(hexString);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}
