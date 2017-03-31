package com.bishe.renbinbin1.secret_master.module.memo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by renbinbin1 on 2017/3/15.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_MEMO="create table memo (id integer primary key " +
            "autoincrement,content text,time text)";
    public DataBaseHelper(Context context){
        super(context, "memo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MEMO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
