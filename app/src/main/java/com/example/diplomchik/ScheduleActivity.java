package com.example.diplomchik;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import com.example.diplomchik.Helpers.HomeworkContract;
import com.example.diplomchik.Helpers.HomeworkDBHelper;

public class ScheduleActivity extends AppCompatActivity {

    private HomeworkDBHelper dbHelper;
    int currentWeek = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        dbHelper = new HomeworkDBHelper(this);

        displayWeeklySchedule();
        Button btnPreviousWeek = findViewById(R.id.btn_previous_week);
        Button btnNextWeek = findViewById(R.id.btn_next_week);
        TextView textViewWeek = findViewById(R.id.textView_week);

         // Начнем с первой недели

        btnPreviousWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentWeek > 1) {
                    currentWeek--;
                    textViewWeek.setText("Week " + currentWeek);
                    displayWeeklySchedule(); // Обновляем расписание для предыдущей недели
                }
            }
        });

        btnNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Предположим, что у нас есть ограничение на количество недель
                int maxWeeks = 10; // Например, допустим 10 недель
                if (currentWeek < maxWeeks) {
                    currentWeek++;
                    textViewWeek.setText("Week " + currentWeek);
                    displayWeeklySchedule(); // Обновляем расписание для следующей недели
                }
            }
        });

    }

    private void displayWeeklySchedule() {
        LinearLayout layout = findViewById(R.id.layout_weekly_schedule);
        layout.removeAllViews(); // Очищаем макет перед добавлением данных

        // Загрузка расписания из базы данных
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                HomeworkContract.WeeklyScheduleEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        String currentDay = "";
        LinearLayout dayLayout = null;

        // Проходим по каждой записи и отображаем ее на экране
        while (cursor.moveToNext()) {
            String day = cursor.getString(cursor.getColumnIndexOrThrow(HomeworkContract.WeeklyScheduleEntry.COLUMN_DAY));
            String subject = cursor.getString(cursor.getColumnIndexOrThrow(HomeworkContract.WeeklyScheduleEntry.COLUMN_SUBJECT));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(HomeworkContract.WeeklyScheduleEntry.COLUMN_TIME));

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

            // Создаем TextView для предмета и времени и добавляем в LinearLayout для текущего дня недели
            TextView subjectTextView = new TextView(this);
            subjectTextView.setText(subject + " - " + time);
            dayLayout.addView(subjectTextView);
        }

        // Закрываем курсор после использования
        cursor.close();
    }

}