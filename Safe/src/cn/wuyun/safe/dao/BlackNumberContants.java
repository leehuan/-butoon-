package cn.wuyun.safe.dao;

public interface BlackNumberContants {

	public static final String BLACKNUMBER_DATEBASE = "black.db";
	public static final int BLACKNUMBER_VEISON = 1;
	public static final String BLACKNUMBER_TABLE = "black";
	public static final String BLACKNUMBER_ID = "_id";
	public static final String BLACKNUMBER_BLACKNUMBER = "blacknumber";
	public static final String BLACKNUMBER_MODE = "mode";
	public static final String BLACKNUMBER_SQL = "create table "
			+ BLACKNUMBER_TABLE + "(" + BLACKNUMBER_ID
			+ " integer primary key autoincrement," + BLACKNUMBER_BLACKNUMBER
			+ " varchar(20)," + BLACKNUMBER_MODE + " varchar(10))";

	public static final int BLACKNUMBER_MODE_SMS = 1;
	public static final int BLACKNUMBER_MODE_ALL = 2;
	public static final int BLACKNUMBER_MODE_PHONE =0;
}
