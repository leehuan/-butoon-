package cn.wuyun.safe.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNumberCreateDB extends SQLiteOpenHelper {

	public BlackNumberCreateDB(Context context) {
		super(context, BlackNumberContants.BLACKNUMBER_DATEBASE, null, BlackNumberContants.BLACKNUMBER_VEISON);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(BlackNumberContants.BLACKNUMBER_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
