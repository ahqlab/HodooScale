package com.animal.scale.hodoo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "square_hodoo.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("HJLEE", "CREATE DATABASE");
        db.execSQL("CREATE TABLE search_history (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userIdx INTEGER NOT NULL, " +
                "feedIdx INTEGER NOT NULL, " +
                "feed_name TEXT NOT NULL, " +
                "create_date TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
