package com.example.diplomchik;

import static androidx.constraintlayout.widget.StateSet.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diplomchik.Helpers.DBContract;
import com.example.diplomchik.Helpers.DBHelper;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new DBHelper(this);

        // Вставляем тестовые данные (можете пропустить этот блок, если данные уже есть)
        insertTestData();

        // Выводим данные на экран
        displayHomework();


    }


    private void insertTestData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Добавляем задания для первого семестра
        values.put(DBContract.HomeworkEntry.COLUMN_SUBJECT, "Математика");
        values.put(DBContract.HomeworkEntry.COLUMN_DESCRIPTION, "Выполнить упражнения 1-10, страница 50.");
        values.put(DBContract.HomeworkEntry.COLUMN_DUE_DATE, "2024-04-30");
        values.put(DBContract.HomeworkEntry.COLUMN_COMPLETED, 0); // Значение по умолчанию для нового столбца "completed"
        db.insert(DBContract.HomeworkEntry.TABLE_NAME, null, values);

        clearScheduleData(db,2);

        // Добавляем записи в таблицу расписания на неделю для первого семестра
        addScheduleData(db, 1, "Понедельник", "Математика", "8:00 AM");
        addScheduleData(db, 1, "Понедельник", "Физика", "9:00 AM");
        addScheduleData(db, 1, "Понедельник", "Геометрия", "10:00 AM");

        addScheduleData(db, 1, "Вторник", "История", "8:00 AM");
        addScheduleData(db, 1, "Вторник", "Биология", "9:00 AM");
        addScheduleData(db, 1, "Вторник", "ОБЖ", "10:00 AM");

        addScheduleData(db, 1, "Среда", "Математика", "8:00 AM");
        addScheduleData(db, 1, "Среда", "Литература", "9:00 AM");
        addScheduleData(db, 1, "Среда", "Экономика", "10:00 AM");

        addScheduleData(db, 1, "Четверг", "География", "8:00 AM");
        addScheduleData(db, 1,"Четверг", "Информатика", "9:00 AM");
        addScheduleData(db, 1, "Четверг","Физ-ра", "10:00 AM");

        addScheduleData(db, 1, "Пятница", "ИЗО", "8:00 AM");
        addScheduleData(db, 1, "Пятница","Музыка", "9:00 AM");
        addScheduleData(db, 1, "Пятница","История", "10:00 AM");

        // Добавляем остальное расписание для первого семестра

        // Добавляем задания для второго семестра
        values.clear(); // Очищаем значения, чтобы использовать их повторно
        values.put(DBContract.HomeworkEntry.COLUMN_SUBJECT, "Биология");
        values.put(DBContract.HomeworkEntry.COLUMN_DESCRIPTION, "Выполнить лабораторную работу.");
        values.put(DBContract.HomeworkEntry.COLUMN_DUE_DATE, "2024-05-15");
        values.put(DBContract.HomeworkEntry.COLUMN_COMPLETED, 0); // Значение по умолчанию для нового столбца "completed"
        db.insert(DBContract.HomeworkEntry.TABLE_NAME, null, values);

        // Добавляем записи в таблицу расписания на неделю для второго семестра
        addScheduleData(db, 2, "Понедельник", "Алгебра", "8:00 AM");
        addScheduleData(db, 2, "Понедельник","Геометрия", "9:00 AM");
        addScheduleData(db, 2, "Понедельник","Русский язык", "10:00 AM");

        addScheduleData(db, 2, "Вторник", "Экономика", "8:00 AM");
        addScheduleData(db, 2, "Вторник","Обществознание", "9:00 AM");
        addScheduleData(db, 2, "Вторник","Правоведение", "10:00 AM");

        addScheduleData(db, 2, "Среда", "Литература", "8:00 AM");
        addScheduleData(db, 2, "Среда","Психология", "9:00 AM");
        addScheduleData(db, 2, "Среда","Психология", "10:00 AM");

        addScheduleData(db, 2, "Четверг", "Английский язык", "8:00 AM");
        addScheduleData(db, 2, "Четверг","Русский язык", "9:00 AM");
        addScheduleData(db, 2, "Четверг","Литература", "10:00 AM");

        addScheduleData(db, 2, "Пятница", "Физ-ра", "8:00 AM");
        addScheduleData(db, 2, "Пятница","ОБЖ", "9:00 AM");
        addScheduleData(db, 2, "Пятница","Технология", "10:00 AM");

    }

    private void addScheduleData(SQLiteDatabase db, int semester, String day, String subject, String time) {
        Cursor cursor = db.query(
                DBContract.WeeklyScheduleEntry.TABLE_NAME,
                null,
                DBContract.WeeklyScheduleEntry.COLUMN_SEMESTER + " = ? AND " +
                        DBContract.WeeklyScheduleEntry.COLUMN_DAY + " = ? AND " +
                        DBContract.WeeklyScheduleEntry.COLUMN_SUBJECT + " = ? AND " +
                        DBContract.WeeklyScheduleEntry.COLUMN_TIME + " = ?",
                new String[]{String.valueOf(semester), day, subject, time},
                null,
                null,
                null
        );

        if (cursor.getCount() == 0) {
            // Если записи с такими значениями нет, добавляем новую запись в базу данных
            ContentValues values = new ContentValues();
            values.put(DBContract.WeeklyScheduleEntry.COLUMN_DAY, day);
            values.put(DBContract.WeeklyScheduleEntry.COLUMN_SUBJECT, subject);
            values.put(DBContract.WeeklyScheduleEntry.COLUMN_TIME, time);
            values.put(DBContract.WeeklyScheduleEntry.COLUMN_SEMESTER, semester);
            db.insert(DBContract.WeeklyScheduleEntry.TABLE_NAME, null, values);
        }

        // Закрываем курсор после использования
        cursor.close();
    }

    private void displayHomework() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DBContract.HomeworkEntry._ID,
                DBContract.HomeworkEntry.COLUMN_SUBJECT,
                DBContract.HomeworkEntry.COLUMN_DESCRIPTION,
                DBContract.HomeworkEntry.COLUMN_DUE_DATE,
                DBContract.HomeworkEntry.COLUMN_COMPLETED // Добавляем столбец "completed" в запрос
        };
        Cursor cursor = db.query(
                DBContract.HomeworkEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        LinearLayout layout = findViewById(R.id.layout_homework);
        layout.removeAllViews(); // Очищаем макет перед добавлением данных

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.HomeworkEntry._ID));
            String subject = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HomeworkEntry.COLUMN_SUBJECT));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HomeworkEntry.COLUMN_DESCRIPTION));
            String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.HomeworkEntry.COLUMN_DUE_DATE));
            int completed = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.HomeworkEntry.COLUMN_COMPLETED)); // Получаем состояние выполнения задания

            // Создаем новое представление для каждого элемента списка
            LinearLayout itemView = (LinearLayout) getLayoutInflater().inflate(R.layout.homework_item, layout, false);
            TextView textView = itemView.findViewById(R.id.textView);
            CheckBox checkBox = itemView.findViewById(R.id.checkBox);

            // Устанавливаем текст в TextView
            textView.setText("ID: " + id + "\n" +
                    "Предмет: " + subject + "\n" +
                    "Описание: " + description + "\n" +
                    "Срок сдачи: " + dueDate);

            // Устанавливаем состояние выполнения задания в CheckBox
            checkBox.setChecked(completed == 1);

            // Устанавливаем обработчик щелчка для CheckBox
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Обновляем состояние выполнения задания в базе данных
                updateHomeworkStatus(id, isChecked);
                // Удаляем выполненное задание из списка
                if (isChecked) {
                    deleteHomework(id);
                    layout.removeView(itemView);
                }
            });

            // Добавляем itemView в макет
            layout.addView(itemView);
        }
        cursor.close();
    }

    private void updateHomeworkStatus(long id, boolean isCompleted) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.HomeworkEntry.COLUMN_COMPLETED, isCompleted ? 1 : 0);
        db.update(DBContract.HomeworkEntry.TABLE_NAME, values, DBContract.HomeworkEntry._ID + " = ?", new String[]{String.valueOf(id)});
    }

    private void deleteHomework(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBContract.HomeworkEntry.TABLE_NAME, DBContract.HomeworkEntry._ID + " = ?", new String[]{String.valueOf(id)});
    }



    private void clearScheduleData(SQLiteDatabase db, int semester) {
        db.delete(
                DBContract.WeeklyScheduleEntry.TABLE_NAME,
                DBContract.WeeklyScheduleEntry.COLUMN_SEMESTER + " = ?",
                new String[]{String.valueOf(semester)}
        );
    }
}