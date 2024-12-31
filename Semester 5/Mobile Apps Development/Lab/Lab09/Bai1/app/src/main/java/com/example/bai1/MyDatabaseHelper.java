package com.example.bai1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bai1.recycler.DownloadFile;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lab09_bai1.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "download_files";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCAL_PATH = "local_path";
    private static final String COLUMN_SIZE = "size";
    private static final String COLUMN_DOWNLOAD_URL = "download_url";
    private static final String COLUMN_DOWNLOAD_PROGRESS = "download_progress";
    private static final String COLUMN_ICON = "icon";
    private final Context context;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s INTEGER, %s INTEGER)", TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_LOCAL_PATH, COLUMN_SIZE, COLUMN_DOWNLOAD_URL, COLUMN_DOWNLOAD_PROGRESS, COLUMN_ICON);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        onCreate(db);
    }

    public ArrayList<DownloadFile> getAllDownloadFiles() {
        ArrayList<DownloadFile> downloadFiles = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            downloadFiles.add(new DownloadFile(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6)));
            cursor.moveToNext();
        } return downloadFiles;
    }

    public void addDownloadFile(DownloadFile downloadFile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, downloadFile.getId());
        cv.put(COLUMN_NAME, downloadFile.getName());
        cv.put(COLUMN_LOCAL_PATH, downloadFile.getLocalPath());
        cv.put(COLUMN_SIZE, downloadFile.getSize());
        cv.put(COLUMN_DOWNLOAD_URL, downloadFile.getDownloadUrl());
        cv.put(COLUMN_DOWNLOAD_PROGRESS, downloadFile.getProgress());
        cv.put(COLUMN_ICON, downloadFile.getIcon());

        db.insert(TABLE_NAME, null, cv);
    }

    public void removeDownloadFile(String downloadFileId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{downloadFileId});
    }

    public void updateDownloadFile(DownloadFile downloadFile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(downloadFile.getId())});
        db.close();
    }
}