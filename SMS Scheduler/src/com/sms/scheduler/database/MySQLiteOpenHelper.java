package com.sms.scheduler.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "messages";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_BODY = "body";
	public static final String COLUMN_RECEPIENTS = "recepients";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_TYPE = "type";
	
	
	private static final String DATABASE_NAME = "smsscheduler.db";
	private static final int DATABASE_VERSION = 1;
	  
	private static final String DATABASE_CREATE = "create table "
		      + TABLE_NAME + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_RECEPIENTS
		      + " text not null, " + COLUMN_BODY
		      + " text not null, " + COLUMN_TIME
		      + " text not null, " + COLUMN_TYPE
		      + " text not null);";
	
	
	public MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);	
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteOpenHelper.class.getName(),
	            "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	    onCreate(db);

	}

}
