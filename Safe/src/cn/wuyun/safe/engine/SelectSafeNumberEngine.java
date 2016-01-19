package cn.wuyun.safe.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.wuyun.safe.Utils.Contants;
import cn.wuyun.safe.bean.SelectSafeAddListInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

public class SelectSafeNumberEngine {

	public static List<SelectSafeAddListInfo> getAllContans(Context context) {

		List<SelectSafeAddListInfo> list = new ArrayList<SelectSafeAddListInfo>();
		// 获取内容解析者
		ContentResolver contentResolver = context.getContentResolver();
		// 获取URI 双C+P
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		// 创建一个String类型数组，并且获取联系人数据表中的列名
		String[] project = new String[] {
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.NUMBER,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,

		};
		// 使用查询方法
		Cursor query = contentResolver.query(uri, project, null, null, null);
		System.out.println(query);
		if (query != null) {
			// 遍历Query
			while (query.moveToNext()) {
				String name = query.getString(0);
				String number = query.getString(1);
				String id = query.getString(2);
				// 将查询到的值循环的传入到JAVABEAN中
				SelectSafeAddListInfo selectinfo = new SelectSafeAddListInfo(
						name, number, id);
				// 添加到集合中
				list.add(selectinfo);
			}
			query.close();
		}
		// 关闭遍历，返回集合
		return list;

	}

	public static Bitmap getContansIcon(Context context, String id) {
		// 获取内容解析者
		ContentResolver contentResolver = context.getContentResolver();
		// 获取URI 根据ID来获取图片的位置
		Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI,
				id);
		// 获取图片的流对象
		InputStream is = ContactsContract.Contacts.openContactPhotoInputStream(
				contentResolver, uri);
		// 将流对象转换成为BITMAP对象
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		if (is != null) {

			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return bitmap;

	}

}
