package cn.wuyun.safe.dao.db;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class VirusDao {

	public static boolean isVirus(Context context, String md5) {

		boolean isAntivirus = false;
		File file = new File(context.getFilesDir(), "antivirus.db");
		SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(
				file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = openDatabase.query("datable", new String[] { "md5" },
				"md5=?", new String[] { md5 }, null, null, null);
		if (cursor != null) {
			if (cursor.moveToNext()) {
				// 在数据库中
				isAntivirus = true;
			}
			cursor.close();
		}
		openDatabase.close();

		return isAntivirus;

	}
}
