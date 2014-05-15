package com.huchcode.train.android.sample.chap2.provider.helper;

import android.net.Uri;
import android.provider.BaseColumns;

public class BusinessCardAppTables {

    // the package value which is for ContentProvider configuration
    public static final String AUTHORITY = "com.huchcode.train.android.sample.businesscard";
    
    public static class BusinessCardTable implements BaseColumns {
        
        //Unique Address ContentProvider Configuration
        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/businesscard");
        
        public final static String NAME = "name";
        public final static String COMPANY = "company";
        public final static String PHONE_NO = "phone_no";
        public final static String EMAIL = "email";
        public final static String PROFILE_IMAGE = "img_url";

        public final static String TABLE_NAME = "businesscard";

        public final static String TABLE_CREATE_SQL = "create table " //
                + TABLE_NAME + "(" + _ID + " integer primary key autoincrement, "//
                + NAME + " text not null, "//
                + COMPANY + " text, "//
                + EMAIL + " text, " //
                + PHONE_NO + " text, "//
                + PROFILE_IMAGE + " text)";//

        public static final String TABLE_DROP_SQL = "drop table if exists " + TABLE_NAME;
    }

}
