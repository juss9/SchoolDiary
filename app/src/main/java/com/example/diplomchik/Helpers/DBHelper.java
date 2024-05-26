package com.example.diplomchik.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Diplom.db";
    private static final int DATABASE_VERSION = 3;

    // Таблица домашних заданий
    private static final String SQL_CREATE_HOMEWORK_TABLE =
            "CREATE TABLE " + DBContract.HomeworkEntry.TABLE_NAME + " (" +
                    DBContract.HomeworkEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBContract.HomeworkEntry.COLUMN_SUBJECT + " TEXT NOT NULL," +
                    DBContract.HomeworkEntry.COLUMN_DESCRIPTION + " TEXT," +
                    DBContract.HomeworkEntry.COLUMN_DUE_DATE + " TEXT NOT NULL," +
                    DBContract.HomeworkEntry.COLUMN_COMPLETED + " INTEGER DEFAULT 0 )";

    // Таблица с расписанием
    private static final String SQL_CREATE_WEEKLY_SCHEDULE_TABLE =
            "CREATE TABLE " + DBContract.WeeklyScheduleEntry.TABLE_NAME + " (" +
                    DBContract.WeeklyScheduleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBContract.WeeklyScheduleEntry.COLUMN_DAY + " TEXT NOT NULL," +
                    DBContract.WeeklyScheduleEntry.COLUMN_SUBJECT + " TEXT NOT NULL," +
                    DBContract.WeeklyScheduleEntry.COLUMN_TIME + " TEXT NOT NULL," +
                    DBContract.WeeklyScheduleEntry.COLUMN_SEMESTER + " INTEGER NOT NULL" +
                    ");";

    // Таблица студентов
    private static final String SQL_CREATE_STUDENT_TABLE =
            "CREATE TABLE " + DBContract.StudentEntry.TABLE_NAME + " (" +
                    DBContract.StudentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBContract.StudentEntry.COLUMN_LOGIN + " TEXT NOT NULL," +
                    DBContract.StudentEntry.COLUMN_PASSWORD + " TEXT NOT NULL," +
                    DBContract.StudentEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    DBContract.StudentEntry.COLUMN_CLASS + " TEXT NOT NULL," +
                    DBContract.StudentEntry.COLUMN_BIRTHDATE + " TEXT NOT NULL" +
                    ");";

    private static final String SQL_CREATE_RATING_TABLE =
            "CREATE TABLE " + DBContract.RatingsEntry.TABLE_NAME + " (" +
                    DBContract.RatingsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBContract.RatingsEntry.COLUMN_SUBJECT + " TEXT," +
                    DBContract.RatingsEntry.COLUMN_SEMESTER + " INTEGER," +
                    DBContract.RatingsEntry.COLUMN_RATING + " INTEGER)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HOMEWORK_TABLE);
        db.execSQL(SQL_CREATE_WEEKLY_SCHEDULE_TABLE);
        db.execSQL(SQL_CREATE_STUDENT_TABLE);
        db.execSQL(SQL_CREATE_RATING_TABLE);
        insertStudentData(db);
        addRatingsForFirstSemester();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.HomeworkEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.WeeklyScheduleEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.StudentEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.RatingsEntry.TABLE_NAME);
        onCreate(db);
    }

    private void insertStudentData(SQLiteDatabase db) {
        ContentValues values1 = new ContentValues();
        values1.put(DBContract.StudentEntry.COLUMN_LOGIN, "I");
        values1.put(DBContract.StudentEntry.COLUMN_PASSWORD, "12");
        values1.put(DBContract.StudentEntry.COLUMN_NAME, "Иванов Иван Иванович");
        values1.put(DBContract.StudentEntry.COLUMN_CLASS, "10A");
        values1.put(DBContract.StudentEntry.COLUMN_BIRTHDATE, "01.01.2005");

        db.insert(DBContract.StudentEntry.TABLE_NAME, null, values1);

        ContentValues values2 = new ContentValues();
        values2.put(DBContract.StudentEntry.COLUMN_LOGIN, "P");
        values2.put(DBContract.StudentEntry.COLUMN_PASSWORD, "2");
        values2.put(DBContract.StudentEntry.COLUMN_NAME, "Петров Иван Петрович");
        values2.put(DBContract.StudentEntry.COLUMN_CLASS, "10A");
        values2.put(DBContract.StudentEntry.COLUMN_BIRTHDATE, "01.01.2005");

        db.insert(DBContract.StudentEntry.TABLE_NAME, null, values2);
    }


    public void addRating(String subject, int semester, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.RatingsEntry.COLUMN_SUBJECT, subject);
        values.put(DBContract.RatingsEntry.COLUMN_SEMESTER, semester);
        values.put(DBContract.RatingsEntry.COLUMN_RATING, rating);
        db.insert(DBContract.RatingsEntry.TABLE_NAME, null, values);
        db.close();
    }


    public void addRatingsForFirstSemester() {
        addRating("Math", 1, 4);
        addRating("Math", 1, 5);
        addRating("Math", 1, 3);
        addRating("Math", 1, 4);
        addRating("Math", 1, 5);

        addRating("Physics", 1, 5);
        addRating("Physics", 1, 4);
        addRating("Physics", 1, 3);
        addRating("Physics", 1, 5);
        addRating("Physics", 1, 4);

        addRating("Chemistry", 1, 3);
        addRating("Chemistry", 1, 4);
        addRating("Chemistry", 1, 5);
        addRating("Chemistry", 1, 3);
        addRating("Chemistry", 1, 4);

        addRating("History", 1, 5);
        addRating("History", 1, 3);
        addRating("History", 1, 4);
        addRating("History", 1, 5);
        addRating("History", 1, 3);

        addRating("Biology", 1, 3);
        addRating("Biology", 1, 5);
        addRating("Biology", 1, 4);
        addRating("Biology", 1, 3);
        addRating("Biology", 1, 5);

        addRating("English", 1, 4);
        addRating("English", 1, 3);
        addRating("English", 1, 5);
        addRating("English", 1, 4);
        addRating("English", 1, 3);

        addRating("Geography", 1, 5);
        addRating("Geography", 1, 3);
        addRating("Geography", 1, 4);
        addRating("Geography", 1, 5);
        addRating("Geography", 1, 3);

        addRating("Computer Science", 1, 3);
        addRating("Computer Science", 1, 4);
        addRating("Computer Science", 1, 5);
        addRating("Computer Science", 1, 3);
        addRating("Computer Science", 1, 4);

        addRating("Physical Education", 1, 4);
        addRating("Physical Education", 1, 5);
        addRating("Physical Education", 1, 3);
        addRating("Physical Education", 1, 4);
        addRating("Physical Education", 1, 5);

        addRating("Art", 1, 3);
        addRating("Art", 1, 4);
        addRating("Art", 1, 5);
        addRating("Art", 1, 3);
        addRating("Art", 1, 4);

        addRating("Music", 1, 5);
        addRating("Music", 1, 3);
        addRating("Music", 1, 4);
        addRating("Music", 1, 5);
        addRating("Music", 1, 3);

        addRating("Drama", 1, 4);
        addRating("Drama", 1, 5);
        addRating("Drama", 1, 3);
        addRating("Drama", 1, 4);
        addRating("Drama", 1, 5);
    }

}
