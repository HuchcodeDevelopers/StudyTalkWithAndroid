package com.huchcode.train.android.sample.chap2.provider.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BusinessCardSQLiteHelper {
	private static final String DB_NAME = "businesscard.db";
	private static final int DB_VERSION = 1;

	// static
	private static BusinessCardSQLiteHelper instance;
	public static BusinessCardSQLiteHelper getInstance(Context context) {
		if (instance == null) {
			instance = new BusinessCardSQLiteHelper(context);
		}
		return instance;
	}

	
	// instance
	private BusinessCardSQLiteOpenHelper openHelper;

	public BusinessCardSQLiteHelper(Context context) {
		openHelper = new BusinessCardSQLiteOpenHelper(context, DB_NAME, null,
				DB_VERSION);
	}

	/**
	 * @return
	 */
	public SQLiteDatabase getReadableDatabase() {
		return openHelper.getReadableDatabase();
	}

	/**
	 * @return
	 */
	public SQLiteDatabase getWritableDatabase() {
		return openHelper.getWritableDatabase();
	}

	/**
	 * Database open helper
	 */
	class BusinessCardSQLiteOpenHelper extends SQLiteOpenHelper {
		public BusinessCardSQLiteOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(BusinessCardAppTables.BusinessCardTable.TABLE_CREATE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(BusinessCardAppTables.BusinessCardTable.TABLE_DROP_SQL);
			onCreate(db);
		}
	}

}
