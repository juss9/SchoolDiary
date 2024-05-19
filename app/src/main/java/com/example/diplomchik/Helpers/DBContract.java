package com.example.diplomchik.Helpers;

import android.provider.BaseColumns;

public final class DBContract {
    private DBContract() {}

    public static class HomeworkEntry implements BaseColumns {
        public static final String TABLE_NAME = "homework";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DUE_DATE = "due_date";
        public static final String COLUMN_COMPLETED = "completed";
    }

    public static class WeeklyScheduleEntry implements BaseColumns {
        public static final String TABLE_NAME = "weekly_schedule";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_SEMESTER = "semester";
    }

    public static class StudentEntry implements BaseColumns {
        public static final String TABLE_NAME = "student";
        public static final String COLUMN_LOGIN = "login";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CLASS = "class";
        public static final String COLUMN_BIRTHDATE = "birthdate";
    }
    public static class RatingsEntry implements BaseColumns {
        public static final String TABLE_NAME = "ratings";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_SEMESTER = "semester";
        public static final String COLUMN_RATING = "rating";
    }
}
