package com.huchcode.train.android.sample.chap2.provider;

import com.huchcode.train.android.sample.chap2.provider.helper.BusinessCardAppTables;
import com.huchcode.train.android.sample.chap2.provider.helper.BusinessCardSQLiteHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class BusinessCardContentProvider extends ContentProvider {

    private static final int CARDS = 0;
    private static final int CARD_ID = 1;
    private static UriMatcher matcher;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(BusinessCardAppTables.AUTHORITY, "businesscard", CARDS);
        matcher.addURI(BusinessCardAppTables.AUTHORITY, "businesscard/#", CARD_ID);
    }

    // instance area
    private Context context;

    private BusinessCardSQLiteHelper helper;

    @Override
    public boolean onCreate() {
        this.context = getContext();
        
        helper = new BusinessCardSQLiteHelper(this.context);
        if (helper != null) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
        case CARDS: {
            return "vnd.com.huchcode.android.businesscard.cursor.dir/vnd.com.huchcode.android.businesscard";
        }
        case CARD_ID: {
            return "vnd.com.huchcode.android.businesscard.cursor.item/vnd.com.huchcode.android.businesscard";
        }
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = null;
        Uri contentUri = null;
        long id = 0;

        switch (matcher.match(uri)) {
        case CARDS: {
            tableName = BusinessCardAppTables.BusinessCardTable.TABLE_NAME;
            contentUri = BusinessCardAppTables.BusinessCardTable.CONTENT_URI;
            break;
        }
        default: {
            throw new IllegalArgumentException();
        }
        }

        SQLiteDatabase db = helper.getWritableDatabase();
        id = db.insert(tableName, null, values);
        db.close();

        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(contentUri, id);
            getContext().getContentResolver().notifyChange(itemUri, null);
            return itemUri;
        }
        else {
            throw new SQLException();
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = null;
        String id = null;
        String whereClause = null;

        switch (matcher.match(uri)) {
        case CARD_ID: {
            tableName = BusinessCardAppTables.BusinessCardTable.TABLE_NAME;
            id = uri.getPathSegments().get(1);
            whereClause = "_id = " + id + (!TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "");
            break;
        }
        default: {
            throw new IllegalArgumentException();
        }
        }

        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.update(tableName, values, whereClause, selectionArgs);
        db.close();

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName = null;
        String id = null;
        String whereClause = null;

        switch (matcher.match(uri)) {
        case CARD_ID: {
            tableName = BusinessCardAppTables.BusinessCardTable.TABLE_NAME;
            id = uri.getPathSegments().get(1);
            whereClause = "_id = " + id + (!TextUtils.isEmpty(selection) ? " and (" + selection + ")" : "");
            break;
        }
        default: {
            throw new IllegalArgumentException();
        }
        }

        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete(tableName, whereClause, selectionArgs);
        db.close();

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (matcher.match(uri)) {
        case CARDS: {
            qb.setTables(BusinessCardAppTables.BusinessCardTable.TABLE_NAME);
            break;
        }
        case CARD_ID: {
            qb.setTables(BusinessCardAppTables.BusinessCardTable.TABLE_NAME);
            qb.appendWhere("_id = " + uri.getPathSegments().get(1));
            break;
        }
        default: {
            throw new IllegalArgumentException();
        }
        }

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

}
