package com.animal.scale.hodoo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.animal.scale.hodoo.domain.SearchHistory;

import java.util.ArrayList;
import java.util.List;

public class DBHandler {

    public static final String TABLE_NAME = "search_history";

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBHandler(Context context) {
        this.dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public boolean insertFeed(SearchHistory searchHistory) {
        Log.e("HJLEE", "searchHistory : " + searchHistory.toString());
        ContentValues values = new ContentValues();
        values.put("feed_name", searchHistory.getFeedName());
        values.put("userIdx", searchHistory.getUserIdx());
        values.put("feedIdx", searchHistory.getFeedIdx());
        values.put("create_date", searchHistory.getCreateDate());
        int result = (int) db.insert(TABLE_NAME, null, values);
        Log.e("HJLEE", "sql result : " + result);
        return result > 0;
    }

    public int allDelete() {
        return db.delete(TABLE_NAME, null, null);
    }

    public List<SearchHistory> select(){
        List<SearchHistory> searchHistories = new ArrayList<SearchHistory>() ;
        this.db = dbHelper.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, null,null,null,null,null,"_id desc");
        while (c.moveToNext()){
            SearchHistory searchHistory = new SearchHistory();
            int _id = c.getInt(c.getColumnIndex("_id"));
            searchHistory.setId(_id);
            String  feedName = c.getString(c.getColumnIndex("feed_name"));
            searchHistory.setFeedName(feedName);
            int feedIdx = c.getInt(c.getColumnIndex("feedIdx"));
            searchHistory.setFeedIdx(feedIdx);
            int userIdx = c.getInt(c.getColumnIndex("userIdx"));
            searchHistory.setUserIdx(userIdx);
            String  createDate = c.getString(c.getColumnIndex("create_date"));
            searchHistory.setCreateDate(createDate);
            searchHistories.add(searchHistory);
        }
        return searchHistories;
    }

    public int delete(Integer id) {
        return db.delete(TABLE_NAME, "_id = ?", new String[]{Integer.toString(id)});
    }
}
