package com.example.diplomchik;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import com.example.diplomchik.Helpers.DBContract;
import com.example.diplomchik.Helpers.DBHelper;


public class ScheduleActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private int currentSemester = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        dbHelper = new DBHelper(this);

        // Отображаем расписание для текущего семестра
        displayWeeklySchedule(currentSemester);

        // Обработчики нажатий для кнопок переключения семестров
        Button btnSemester1 = findViewById(R.id.btn_semester_1);
        Button btnSemester2 = findViewById(R.id.btn_semester_2);

        btnSemester1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSemester = 1;
                displayWeeklySchedule(currentSemester); // Обновляем расписание для первого семестра
            }
        });

        btnSemester2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSemester = 2;
                displayWeeklySchedule(currentSemester); // Обновляем расписание для второго семестра
            }
        });
    }

    private void displayWeeklySchedule(int semester) {
        LinearLayout layout = findViewById(R.id.layout_weekly_schedule);
        layout.removeAllViews(); // Очищаем макет перед добавлением данных

        // Загружаем расписание из базы данных для указанного семестра
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DBContract.WeeklyScheduleEntry.TABLE_NAME,
                null,
                DBContract.WeeklyScheduleEntry.COLUMN_SEMESTER + " = ?",
                new String[]{String.valueOf(semester)},
                null,
                null,
                null
        );
        String currentDay = "";
        LinearLayout dayLayout = null;

        // Отображаем расписание на экране
        while (cursor.moveToNext()) {
            String day = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.WeeklyScheduleEntry.COLUMN_DAY));
            String subject = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.WeeklyScheduleEntry.COLUMN_SUBJECT));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.WeeklyScheduleEntry.COLUMN_TIME));

            if (!day.equals(currentDay)) {
                // Создаем новый LinearLayout для нового дня недели
                dayLayout = new LinearLayout(this);
                dayLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                dayLayout.setOrientation(LinearLayout.VERTICAL);

                // Добавляем TextView с названием дня недели над расписанием
                TextView dayTextView = new TextView(this);
                dayTextView.setText(day);
                dayTextView.setTextSize(18);
                dayTextView.setPadding(0, 16, 0, 8);
                dayLayout.addView(dayTextView);

                // Добавляем созданный Layout в основной макет
                layout.addView(dayLayout);
                currentDay = day;
            }
            // Создаем TextView для отображения записи расписания
            TextView scheduleTextView = new TextView(this);
            scheduleTextView.setText(day + ": " + subject + " - " + time);
            layout.addView(scheduleTextView);
        }

        // Закрываем курсор после использования
        cursor.close();
    }
}