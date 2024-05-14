package com.example.diplomchik;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diplomchik.Helpers.HomeworkContract;
import com.example.diplomchik.Helpers.HomeworkDBHelper;

public class MainActivity extends AppCompatActivity {

    private HomeworkDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new HomeworkDBHelper(this);

        // Вставляем тестовые данные (можете пропустить этот блок, если данные уже есть)
        insertTestData();

        // Выводим данные на экран
        displayHomework();

        Button button = findViewById(R.id.but_shedule);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shedule_win();
            }
        });

    }

    private void insertTestData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HomeworkContract.HomeworkEntry.COLUMN_SUBJECT, "Математика");
        values.put(HomeworkContract.HomeworkEntry.COLUMN_DESCRIPTION, "Выполнить упражнения 1-10, страница 50.");
        values.put(HomeworkContract.HomeworkEntry.COLUMN_DUE_DATE, "2024-04-30");
        values.put(HomeworkContract.HomeworkEntry.COLUMN_COMPLETED, 0); // Значение по умолчанию для нового столбца "completed"
        db.insert(HomeworkContract.HomeworkEntry.TABLE_NAME, null, values);


        // Добавляем записи в таблицу расписания на неделю
        addScheduleData(db, "Monday", "Math", "8:00 AM");
        addScheduleData(db, "Monday", "Physics", "9:00 AM");
        addScheduleData(db, "Tuesday", "Chemistry", "10:00 AM");
        addScheduleData(db, "Tuesday", "History", "11:00 AM");
        addScheduleData(db, "Wednesday", "English", "12:00 PM");
        addScheduleData(db, "Wednesday", "Biology", "1:00 PM");
        addScheduleData(db, "Thursday", "History", "11:00 AM");
        addScheduleData(db, "Thursday", "Math", "11:00 AM");
        addScheduleData(db, "Friday", "English", "12:00 PM");
        addScheduleData(db, "Friday", "English", "13:00 PM");
        addScheduleData(db, "Friday", "English", "14:00 PM");


    }


    private void addScheduleData(SQLiteDatabase db, String day, String subject, String time) {
        Cursor cursor = db.query(
                HomeworkContract.WeeklyScheduleEntry.TABLE_NAME,
                null,
                HomeworkContract.WeeklyScheduleEntry.COLUMN_DAY + " = ? AND " +
                        HomeworkContract.WeeklyScheduleEntry.COLUMN_SUBJECT + " = ? AND " +
                        HomeworkContract.WeeklyScheduleEntry.COLUMN_TIME + " = ?",
                new String[]{day, subject, time},
                null,
                null,
                null
        );

        if (cursor.getCount() == 0) {
            // Если записи с такими значениями нет, добавляем новую запись в базу данных
            ContentValues values = new ContentValues();
            values.put(HomeworkContract.WeeklyScheduleEntry.COLUMN_DAY, day);
            values.put(HomeworkContract.WeeklyScheduleEntry.COLUMN_SUBJECT, subject);
            values.put(HomeworkContract.WeeklyScheduleEntry.COLUMN_TIME, time);
            db.insert(HomeworkContract.WeeklyScheduleEntry.TABLE_NAME, null, values);
        }

        // Закрываем курсор после использования
        cursor.close();
    }

    @SuppressLint("SetTextI18n")
    private void displayHomework() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                HomeworkContract.HomeworkEntry._ID,
                HomeworkContract.HomeworkEntry.COLUMN_SUBJECT,
                HomeworkContract.HomeworkEntry.COLUMN_DESCRIPTION,
                HomeworkContract.HomeworkEntry.COLUMN_DUE_DATE,
                HomeworkContract.HomeworkEntry.COLUMN_COMPLETED // Добавляем столбец "completed" в запрос
        };
        Cursor cursor = db.query(
                HomeworkContract.HomeworkEntry.TABLE_NAME,
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
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(HomeworkContract.HomeworkEntry._ID));
            String subject = cursor.getString(cursor.getColumnIndexOrThrow(HomeworkContract.HomeworkEntry.COLUMN_SUBJECT));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(HomeworkContract.HomeworkEntry.COLUMN_DESCRIPTION));
            String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(HomeworkContract.HomeworkEntry.COLUMN_DUE_DATE));
            int completed = cursor.getInt(cursor.getColumnIndexOrThrow(HomeworkContract.HomeworkEntry.COLUMN_COMPLETED)); // Получаем состояние выполнения задания

            // Создаем новое представление для каждого элемента списка
            LinearLayout itemView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_main, layout, false);
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
        values.put(HomeworkContract.HomeworkEntry.COLUMN_COMPLETED, isCompleted ? 1 : 0);
        db.update(HomeworkContract.HomeworkEntry.TABLE_NAME, values, HomeworkContract.HomeworkEntry._ID + " = ?", new String[]{String.valueOf(id)});
    }

    private void deleteHomework(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(HomeworkContract.HomeworkEntry.TABLE_NAME, HomeworkContract.HomeworkEntry._ID + " = ?", new String[]{String.valueOf(id)});
    }


    private void shedule_win(){
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}
