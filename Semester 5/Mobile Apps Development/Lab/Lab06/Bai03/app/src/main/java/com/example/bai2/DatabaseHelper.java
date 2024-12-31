package com.example.bai2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PLACE = "place";
    private static final String COLUMN_DATETIME = "dateTime";
    private static final String COLUMN_CHECK = "checkStatus"; // Cột kiểm tra sự kiện

    // SQL tạo bảng
    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENTS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_PLACE + " TEXT, " +
            COLUMN_DATETIME + " TEXT, " +
            COLUMN_CHECK + " INTEGER DEFAULT 0);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    // Thêm sự kiện vào cơ sở dữ liệu
    public void addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, event.getName());
        values.put(COLUMN_PLACE, event.getPlace());
        values.put(COLUMN_DATETIME, event.getDateTime());
        values.put(COLUMN_CHECK, event.isCheck() ? 1 : 0);

        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    // Lấy tất cả sự kiện từ cơ sở dữ liệu
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String place = cursor.getString(cursor.getColumnIndex(COLUMN_PLACE));
                @SuppressLint("Range") String dateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATETIME));
                @SuppressLint("Range") boolean checkStatus = cursor.getInt(cursor.getColumnIndex(COLUMN_CHECK)) == 1;

                Event event = new Event(name, place, dateTime);
                event.setId(id);
                event.setCheck(checkStatus);

                eventList.add(event);
            }
            cursor.close();
        }

        db.close();
        return eventList;
    }

    // Cập nhật sự kiện
    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, event.getName());
        values.put(COLUMN_PLACE, event.getPlace());
        values.put(COLUMN_DATETIME, event.getDateTime());
        values.put(COLUMN_CHECK, event.isCheck() ? 1 : 0);

        return db.update(TABLE_EVENTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(event.getId())});
    }

    // Xóa tất cả sự kiện
    public void deleteAllEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, null, null);
        db.close();
    }
}

