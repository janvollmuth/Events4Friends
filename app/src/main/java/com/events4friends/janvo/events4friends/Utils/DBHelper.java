package com.events4friends.janvo.events4friends.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //Database
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "Database";

    //Table
    private static final String TABLE_NAME = "Events";
    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "Name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE "  + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addEvent(Event event) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, event.getName());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public int updateEvent(Event event) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, event.getName());
        return db.update(TABLE_NAME, values, KEY_ID + " =?", new String[]{String.valueOf(event.getId())});
    }

    public void deleteEvent(Event event) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " =?", new String[]{String.valueOf(event.getId())});
        db.close();
    }

    public Event getEvent(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        return new Event(cursor.getInt(0), cursor.getString(1));
    }

    public List<Event> getAllEvents() {

        List<Event> lstEvents = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do{
                Event event = new Event();
                event.setId(cursor.getInt(0));
                event.setName(cursor.getString(1));

                lstEvents.add(event);
            }
            while(cursor.moveToNext());
        }
        return lstEvents;
    }
}
