package com.huchcode.train.android.sample.chap2.provider;

import java.util.ArrayList;
import java.util.List;

import com.huchcode.train.android.sample.chap2.domain.BusinessCard;
import com.huchcode.train.android.sample.chap2.provider.helper.BusinessCardAppTables;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class ContentProviderBusinessCardProvider {

    private Context context;
    private ContentResolver contentResolver;

    public ContentProviderBusinessCardProvider(Context context) {
        this.context = context;
        this.contentResolver = this.context.getContentResolver();
    }

    public long registerBusinessCard(BusinessCard card) {
        ContentValues values = new ContentValues();
        values.put(BusinessCardAppTables.BusinessCardTable.NAME, card.getName());
        values.put(BusinessCardAppTables.BusinessCardTable.COMPANY, card.getCompany());
        values.put(BusinessCardAppTables.BusinessCardTable.PHONE_NO, card.getPhoneNo());
        values.put(BusinessCardAppTables.BusinessCardTable.EMAIL, card.getEmail());

        Uri uri = this.contentResolver.insert(BusinessCardAppTables.BusinessCardTable.CONTENT_URI, values);

        String idString = uri.getPathSegments().get(1);
        return Long.parseLong(idString);
    }

    public int modifyBusinessCard(BusinessCard card) {
        ContentValues values = new ContentValues();
        values.put(BusinessCardAppTables.BusinessCardTable.NAME, card.getName());
        values.put(BusinessCardAppTables.BusinessCardTable.COMPANY, card.getCompany());
        values.put(BusinessCardAppTables.BusinessCardTable.PHONE_NO, card.getPhoneNo());
        values.put(BusinessCardAppTables.BusinessCardTable.EMAIL, card.getEmail());

        Uri uri = ContentUris.withAppendedId(BusinessCardAppTables.BusinessCardTable.CONTENT_URI, card.getId());

        int count = this.contentResolver.update(uri, values, null, null);

        return count;
    }

    public int removeBusinessCard(long id) {
        Uri uri = ContentUris.withAppendedId(BusinessCardAppTables.BusinessCardTable.CONTENT_URI, id);

        int count = this.contentResolver.delete(uri, null, null);
        return count;
    }

    public BusinessCard getBusinessCard(long id) {
        String selection = "_id=?";
        String[] selectionArgs = new String[] { String.valueOf(id) };

        Cursor cursor = this.contentResolver.query(BusinessCardAppTables.BusinessCardTable.CONTENT_URI, null, selection, selectionArgs, null);
        cursor.moveToFirst();

        BusinessCard card = new BusinessCard();
        card.setId(cursor.getInt(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable._ID)));

        card.setName(cursor.getString(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable.NAME)));
        card.setCompany(cursor.getString(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable.COMPANY)));
        card.setPhoneNo(cursor.getString(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable.PHONE_NO)));
        card.setEmail(cursor.getString(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable.EMAIL)));
        card.setImageUrl(cursor.getString(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable.PROFILE_IMAGE)));

        cursor.close();
        return card;
    }

    public List<BusinessCard> findAllBusinessCard() {
        Cursor cursor = this.contentResolver.query(BusinessCardAppTables.BusinessCardTable.CONTENT_URI, null, null, null, null);
        
        cursor.moveToFirst();
        List<BusinessCard> cards = new ArrayList<BusinessCard>();
        BusinessCard card = null;

        int size = cursor.getCount();

        for (int i = 0; i < size; i++) {
            card = new BusinessCard();
            card.setId(cursor.getInt(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable._ID)));

            card.setName(cursor.getString(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable.NAME)));
            card.setCompany(cursor.getString(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable.COMPANY)));
            card.setPhoneNo(cursor.getString(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable.PHONE_NO)));
            card.setEmail(cursor.getString(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable.EMAIL)));
            card.setImageUrl(cursor.getString(cursor.getColumnIndex(BusinessCardAppTables.BusinessCardTable.PROFILE_IMAGE)));

            cards.add(card);

            cursor.moveToNext();
        }

        cursor.close();
        return cards;
    }
}
