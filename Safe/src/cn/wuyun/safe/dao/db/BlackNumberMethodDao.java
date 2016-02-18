package cn.wuyun.safe.dao.db;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import cn.wuyun.safe.bean.BlackNumberBean;
import cn.wuyun.safe.dao.BlackNumberContants;
import cn.wuyun.safe.dao.BlackNumberCreateDB;

public class BlackNumberMethodDao {

	private BlackNumberCreateDB blacknumberDB;

	public BlackNumberMethodDao(Context context) {
		blacknumberDB = new BlackNumberCreateDB(context);
	}

	/**
	 * 想数据库中增加参数
	 * 
	 * @param blacknumber
	 * @param mode
	 * @return
	 */
	public boolean addBlackNumber(String blacknumber, int mode) {
		// 链接数据库
		SQLiteDatabase writableDatabase = blacknumberDB.getWritableDatabase();

		// 创建Value
		ContentValues value = new ContentValues();
		value.put(BlackNumberContants.BLACKNUMBER_BLACKNUMBER, blacknumber);
		value.put(BlackNumberContants.BLACKNUMBER_MODE, mode);
		// 添加语句
		int insert = (int) writableDatabase.insert(
				BlackNumberContants.BLACKNUMBER_TABLE, null, value);
		// 关闭数据库
		writableDatabase.close();
		return insert != -1;
	}

	/**
	 * 删除数据库中参数
	 * 
	 * @param balcknumber
	 * @param mode
	 * @return
	 */
	public boolean delectNumber(String blacknumber) {
		SQLiteDatabase writableDatabase = blacknumberDB.getWritableDatabase();
		int delete = writableDatabase.delete(
				BlackNumberContants.BLACKNUMBER_TABLE, "blacknumber=?",
				new String[] { blacknumber });
		writableDatabase.close();
		return delete != 0;

	}

	/**
	 * 更新
	 */
	public int updateNumber(String blacknumber, int mode) {
		SQLiteDatabase writableDatabase = blacknumberDB.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BlackNumberContants.BLACKNUMBER_MODE, mode);

		int update = writableDatabase.update(
				BlackNumberContants.BLACKNUMBER_TABLE, values,
				BlackNumberContants.BLACKNUMBER_BLACKNUMBER + "=?",
				new String[] { blacknumber });
		writableDatabase.close();
		return update;

	}

	public int selectNumber(String blacknumber) {
		int mode = -1;
		SQLiteDatabase readableDatabase = blacknumberDB.getReadableDatabase();
		Cursor query = readableDatabase.query(
				BlackNumberContants.BLACKNUMBER_TABLE,
				new String[] { BlackNumberContants.BLACKNUMBER_MODE },
				BlackNumberContants.BLACKNUMBER_BLACKNUMBER + "=?",
				new String[] { blacknumber }, null, null, null);
		if (query != null) {
			if (query.moveToNext()) {
				// TODO xxx
				mode = query.getInt(0);
				return mode;
			}

		}
		readableDatabase.close();
		query.close();
		return mode;

	}

	@SuppressLint("NewApi")
	public List<BlackNumberBean> selectAllNumber(int maxnumber, int startnumber) {
		SystemClock.sleep(2000);
		List<BlackNumberBean> list = new ArrayList<BlackNumberBean>();
		SQLiteDatabase readableDatabase = blacknumberDB.getReadableDatabase();

		// Cursor query = readableDatabase.query(
		// BlackNumberContants.BLACKNUMBER_TABLE, new String[] {
		// BlackNumberContants.BLACKNUMBER_BLACKNUMBER,
		// BlackNumberContants.BLACKNUMBER_MODE }, null, null,
		// null, null, null);

		Cursor query = readableDatabase
				.rawQuery(
						"select blacknumber,mode from black order by _id desc limit ? offset ?",
						new String[] { maxnumber + "", startnumber + "" });

		if (query != null) {
			while (query.moveToNext()) {
				String number = query.getString(0);
				int mode = query.getInt(1);
				BlackNumberBean black = new BlackNumberBean(number, mode);
				list.add(black);
			}

			readableDatabase.close();
			query.close();
		}
		return list;
	}
}
