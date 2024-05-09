package com.example.diplomchik.Helpers;

import android.provider.BaseColumns;
// структура таблицы
public final class HomeworkContract {
    private HomeworkContract() {}

    public static class HomeworkEntry implements BaseColumns {
        public static final String TABLE_NAME = "homework";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DUE_DATE = "due_date";
        public static final String COLUMN_COMPLETED = "completed";
    }

    // В классе HomeworkContract добавим новый внутренний класс для представления таблицы с расписанием на неделю
    public static class WeeklyScheduleEntry implements BaseColumns {
        public static final String TABLE_NAME = "weekly_schedule";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_TIME = "time";
    }

}
