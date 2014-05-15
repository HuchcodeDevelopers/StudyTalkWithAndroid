package com.huchcode.train.android.sample.chap2.provider;

import java.util.ArrayList;
import java.util.List;

import com.huchcode.train.android.sample.chap2.domain.BusinessCard;
import com.huchcode.train.android.sample.chap2.provider.helper.BusinessCardAppTables;
import com.huchcode.train.android.sample.chap2.provider.helper.BusinessCardSQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * the provider which is using local database
 * 
 */
public class LocalDbBusinessCardProvider {

	private Context context;

	private BusinessCardSQLiteHelper helper;

	public LocalDbBusinessCardProvider(Context context) {
		this.context = context;
		helper = BusinessCardSQLiteHelper.getInstance(this.context);
	}

	public long registerBusinessCard(BusinessCard card) {
		// card -> ContentValues
		ContentValues values = new ContentValues();
		values.put(BusinessCardAppTables.BusinessCardTable.NAME, card.getName());
		values.put(BusinessCardAppTables.BusinessCardTable.COMPANY,
				card.getCompany());
		values.put(BusinessCardAppTables.BusinessCardTable.PHONE_NO,
				card.getPhoneNo());
		values.put(BusinessCardAppTables.BusinessCardTable.EMAIL,
				card.getEmail());

		// db에 등록
		SQLiteDatabase db = helper.getWritableDatabase();

		long id = 0;
		try {
			db.beginTransaction();
			id = db.insert(BusinessCardAppTables.BusinessCardTable.TABLE_NAME,
					null, values);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

		db.close();
		return id;
	}

	public int modifyBusinessCard(BusinessCard card) {
		// card -> ContentValues
		ContentValues values = new ContentValues();
		values.put(BusinessCardAppTables.BusinessCardTable.NAME, card.getName());
		values.put(BusinessCardAppTables.BusinessCardTable.COMPANY,
				card.getCompany());
		values.put(BusinessCardAppTables.BusinessCardTable.PHONE_NO,
				card.getPhoneNo());
		values.put(BusinessCardAppTables.BusinessCardTable.EMAIL,
				card.getEmail());

		SQLiteDatabase db = helper.getWritableDatabase();
		int count = 0;
		try {
			db.beginTransaction();

			count = db.update(
					BusinessCardAppTables.BusinessCardTable.TABLE_NAME, values,
					"_id=?", new String[] { String.valueOf(card.getId()) });
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		db.close();

		return count;
	}

	public int removeBusinessCard(long id) {

		SQLiteDatabase db = helper.getWritableDatabase();
		int count = 0;
		try {
			db.beginTransaction();
			
			count = db.delete(
					BusinessCardAppTables.BusinessCardTable.TABLE_NAME,
					"_id=?", new String[] { String.valueOf(id) });
			
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return count;
	}

	public List<BusinessCard> findAllBusinessCard() {
		// db 조회
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(
				BusinessCardAppTables.BusinessCardTable.TABLE_NAME, null, null,
				null, null, null, null);
		cursor.moveToFirst();

		ArrayList<BusinessCard> cards = new ArrayList<BusinessCard>();
		// cursor -> cards
		int size = cursor.getCount();
		for (int i = 0; i < size; i++) {
			BusinessCard card = new BusinessCard();
			card.setId(cursor.getLong(cursor
					.getColumnIndex(BusinessCardAppTables.BusinessCardTable._ID)));
			card.setName(cursor.getString(cursor
					.getColumnIndex(BusinessCardAppTables.BusinessCardTable.NAME)));
			card.setCompany(cursor.getString(cursor
					.getColumnIndex(BusinessCardAppTables.BusinessCardTable.COMPANY)));
			card.setPhoneNo(cursor.getString(cursor
					.getColumnIndex(BusinessCardAppTables.BusinessCardTable.PHONE_NO)));
			card.setEmail(cursor.getString(cursor
					.getColumnIndex(BusinessCardAppTables.BusinessCardTable.EMAIL)));

			cards.add(card);

			cursor.moveToNext();
		}

		db.close();
		return cards;
	}

	public BusinessCard getBusinessCard(long id) {
		// db 조회
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(
				BusinessCardAppTables.BusinessCardTable.TABLE_NAME, null,
				"_id=?", new String[] { String.valueOf(id) }, null, null, null);
		cursor.moveToFirst();

		// cursor -> card
		BusinessCard card = new BusinessCard();
		card.setId(cursor.getLong(cursor
				.getColumnIndex(BusinessCardAppTables.BusinessCardTable._ID)));
		card.setName(cursor.getString(cursor
				.getColumnIndex(BusinessCardAppTables.BusinessCardTable.NAME)));
		card.setCompany(cursor.getString(cursor
				.getColumnIndex(BusinessCardAppTables.BusinessCardTable.COMPANY)));
		card.setPhoneNo(cursor.getString(cursor
				.getColumnIndex(BusinessCardAppTables.BusinessCardTable.PHONE_NO)));
		card.setEmail(cursor.getString(cursor
				.getColumnIndex(BusinessCardAppTables.BusinessCardTable.EMAIL)));

		return card;
	}

}
