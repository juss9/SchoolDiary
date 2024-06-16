package com.example.diplomchik.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Diplomchick.db";
    private static final int DATABASE_VERSION = 4;
    private Context context;

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
//        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HOMEWORK_TABLE);
        db.execSQL(SQL_CREATE_WEEKLY_SCHEDULE_TABLE);
        db.execSQL(SQL_CREATE_STUDENT_TABLE);
        db.execSQL(SQL_CREATE_RATING_TABLE);
        insertStudentData(db);

        addRatingsForFirstSemester(db);
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
        values1.put(DBContract.StudentEntry.COLUMN_LOGIN, "i");
        values1.put(DBContract.StudentEntry.COLUMN_PASSWORD, "12");
        values1.put(DBContract.StudentEntry.COLUMN_NAME, "Иванов Иван Иванович");
        values1.put(DBContract.StudentEntry.COLUMN_CLASS, "10A");
        values1.put(DBContract.StudentEntry.COLUMN_BIRTHDATE, "01.01.2005");

        db.insert(DBContract.StudentEntry.TABLE_NAME, null, values1);
    }

    public void addRating(SQLiteDatabase db, String subject, int semester, int rating) {
        ContentValues values = new ContentValues();
        values.put(DBContract.RatingsEntry.COLUMN_SUBJECT, subject);
        values.put(DBContract.RatingsEntry.COLUMN_SEMESTER, semester);
        values.put(DBContract.RatingsEntry.COLUMN_RATING, rating);
        db.insert(DBContract.RatingsEntry.TABLE_NAME, null, values);
    }

    public void addRatingsForFirstSemester(SQLiteDatabase db) {
        addRating(db, "Математика", 1, 4);
        addRating(db, "Математика", 1, 5);
        addRating(db, "Математика", 1, 3);
        addRating(db, "Математика", 1, 4);
        addRating(db, "Математика", 1, 5);

        addRating(db, "Физика", 1, 5);
        addRating(db, "Физика", 1, 4);
        addRating(db, "Физика", 1, 3);
        addRating(db, "Физика", 1, 5);
        addRating(db, "Физика", 1, 4);

        addRating(db, "Химия", 1, 3);
        addRating(db, "Химия", 1, 4);
        addRating(db, "Химия", 1, 5);
        addRating(db, "Химия", 1, 3);
        addRating(db, "Химия", 1, 4);

        addRating(db, "История", 1, 5);
        addRating(db, "История", 1, 3);
        addRating(db, "История", 1, 4);
        addRating(db, "История", 1, 5);
        addRating(db, "История", 1, 3);

        addRating(db, "Биология", 1, 3);
        addRating(db, "Биология", 1, 5);
        addRating(db, "Биология", 1, 4);
        addRating(db, "Биология", 1, 3);
        addRating(db, "Биология", 1, 5);

        addRating(db, "Английский яз.", 1, 4);
        addRating(db, "Английский яз.", 1, 3);
        addRating(db, "Английский яз.", 1, 5);
        addRating(db, "Английский яз.", 1, 4);
        addRating(db, "Английский яз.", 1, 3);

        addRating(db, "География", 1, 5);
        addRating(db, "География", 1, 3);
        addRating(db, "География", 1, 4);
        addRating(db, "География", 1, 5);
        addRating(db, "География", 1, 3);

        addRating(db, "Информатика", 1, 3);
        addRating(db, "Информатика", 1, 4);
        addRating(db, "Информатика", 1, 5);
        addRating(db, "Информатика", 1, 3);
        addRating(db, "Информатика", 1, 4);

        addRating(db, "Физ-ра", 1, 4);
        addRating(db, "Физ-ра", 1, 5);
        addRating(db, "Физ-ра", 1, 3);
        addRating(db, "Физ-ра", 1, 4);
        addRating(db, "Физ-ра", 1, 5);

        addRating(db, "ИЗО", 1, 3);
        addRating(db, "ИЗО", 1, 4);
        addRating(db, "ИЗО", 1, 5);
        addRating(db, "ИЗО", 1, 3);
        addRating(db, "ИЗО", 1, 4);

        addRating(db, "Музыка", 1, 5);
        addRating(db, "Музыка", 1, 3);
        addRating(db, "Музыка", 1, 4);
        addRating(db, "Музыка", 1, 5);
        addRating(db, "Музыка", 1, 3);

        addRating(db, "ОБЖ", 1, 4);
        addRating(db, "ОБЖ", 1, 5);
        addRating(db, "ОБЖ", 1, 3);
        addRating(db, "ОБЖ", 1, 4);
        addRating(db, "ОБЖ", 1, 5);




        addRating(db, "История", 2, 5);
        addRating(db, "История", 2, 3);
        addRating(db, "История", 2, 4);
        addRating(db, "История", 2, 5);
        addRating(db, "История", 2, 3);

        addRating(db, "Биология", 2, 3);
        addRating(db, "Биология", 2, 5);
        addRating(db, "Биология", 2, 4);
        addRating(db, "Биология", 2, 3);
        addRating(db, "Биология", 2, 5);

        addRating(db, "Английский яз.", 2, 4);
        addRating(db, "Английский яз.", 2, 3);
        addRating(db, "Английский яз.", 2, 5);
        addRating(db, "Английский яз.", 2, 4);
        addRating(db, "Английский яз.", 2, 3);
    }

//    public void deleteDatabase() {
//        context.deleteDatabase(DATABASE_NAME);
//    }
}
