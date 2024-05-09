package com.example.diplomchik.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HomeworkDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "homework.db";
    private static final int DATABASE_VERSION = 1;

    // Таблица домашних заданий
    private static final String SQL_CREATE_HOMEWORK_TABLE =
            "CREATE TABLE " + HomeworkContract.HomeworkEntry.TABLE_NAME + " (" +
                    HomeworkContract.HomeworkEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    HomeworkContract.HomeworkEntry.COLUMN_SUBJECT + " TEXT NOT NULL," +
                    HomeworkContract.HomeworkEntry.COLUMN_DESCRIPTION + " TEXT," +
                    HomeworkContract.HomeworkEntry.COLUMN_DUE_DATE + " TEXT NOT NULL," +
                    HomeworkContract.HomeworkEntry.COLUMN_COMPLETED + " INTEGER DEFAULT 0 )";


    public HomeworkDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HOMEWORK_TABLE);
    }
    // Обновление таблицы
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HomeworkContract.HomeworkEntry.TABLE_NAME);
        onCreate(db);
    }
}
