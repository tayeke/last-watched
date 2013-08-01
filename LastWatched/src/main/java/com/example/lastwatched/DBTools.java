package com.example.lastwatched;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by tayloreke on 7/31/13.
 */
public class DBTools extends SQLiteOpenHelper {

  public DBTools(Context applicationContext) {

    super(applicationContext, "lastwatched.db", null, 1);

  }

  @Override
  public void onCreate(SQLiteDatabase db) {

    String query = "CREATE TABLE shows (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, "+
      "season INTEGER DEFAULT 0, episode INTEGER DEFAULT 0, "+
      "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, "+
      "updated_At DATETIME DEFAULT CURRENT_TIMESTAMP)";

    db.execSQL(query);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i2) {

    String query = "DROP TABLE IF EXISTS shows";

    db.execSQL(query);
    onCreate(db);

  }

  public void insertShow(HashMap<String, String> newValues) {

    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();

    values.put("title", newValues.get("title"));

    db.insert("shows", null, values);

    db.close();

  }

  public int updateShow(HashMap<String, String> newValues) {

    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();

    values.put("title", newValues.get("title"));
    values.put("season", newValues.get("season"));
    values.put("episode", newValues.get("episode"));

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    values.put("updated_at", dateFormat.format(date));

    return db.update("shows", values, "id"+" = ?", new String[] {newValues.get("id")});

  }

  public void deleteShow(String id) {

    SQLiteDatabase db = this.getWritableDatabase();

    String query = "DELETE FROM shows WHERE id='"+ id +"'";

    db.execSQL(query);

  }

  public ArrayList<HashMap<String, String>> getAllShows() {

    ArrayList<HashMap<String, String>> showsArrayList = new ArrayList<HashMap<String, String>>();

    String query = "SELECT * FROM shows ORDER BY updated_at DESC";

    SQLiteDatabase db = this.getWritableDatabase();

    Cursor cursor = db.rawQuery(query, null);

    if(cursor.moveToFirst()) {

      do {

        HashMap<String, String> showMap = new HashMap<String, String>();

        showMap.put("id", cursor.getString(0));
        showMap.put("title", cursor.getString(1));
        showMap.put("season", cursor.getString(2));
        showMap.put("episode", cursor.getString(3));

        showsArrayList.add(showMap);

      } while(cursor.moveToNext());

    }

    return showsArrayList;

  }

  public HashMap<String, String> getShow(String id) {

    HashMap<String, String> showMap = new HashMap<String, String>();

    SQLiteDatabase db = this.getReadableDatabase();

    String query = "SELECT * FROM shows WHERE id='"+ id +"'";

    Cursor cursor = db.rawQuery(query, null);

    if(cursor.moveToFirst()) {

      do {

        showMap.put("id", cursor.getString(0));
        showMap.put("title", cursor.getString(1));
        showMap.put("season", cursor.getString(2));
        showMap.put("episode", cursor.getString(3));

      } while(cursor.moveToNext());

    }

    return showMap;

  }

}
