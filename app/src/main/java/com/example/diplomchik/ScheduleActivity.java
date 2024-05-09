package com.example.diplomchik;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import com.example.diplomchik.Helpers.HomeworkContract;
import com.example.diplomchik.Helpers.HomeworkDBHelper;

public class ScheduleActivity extends AppCompatActivity {

    private HomeworkDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        dbHelper = new HomeworkDBHelper(this);

        displayWeeklySchedule();
    }

    private void displayWeeklySchedule() {
        LinearLayout layout = findViewById(R.id.layout_weekly_schedule);
        layout.removeAllViews(); // Очищаем макет перед добавлением данных

        // Загрузка расписания из базы данных
        Cursor cursor = dbHelper.getReadableDatabase().query(
                HomeworkContract.WeeklyScheduleEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Проходим по каждой записи и отображаем ее на экране
        while (cursor.moveToNext()) {
            String day = cursor.getString(cursor.getColumnIndexOrThrow(HomeworkContract.WeeklyScheduleEntry.COLUMN_DAY));
            String subject = cursor.getString(cursor.getColumnIndexOrThrow(HomeworkContract.WeeklyScheduleEntry.COLUMN_SUBJECT));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(HomeworkContract.WeeklyScheduleEntry.COLUMN_TIME));

            // Создаем новое представление для каждого элемента расписания
            LinearLayout itemView = (LinearLayout) getLayoutInflater().inflate(R.layout.item_schedule, layout, false);

            // Находим TextView в макете для отображения дня, предмета и времени
            TextView textViewDay = itemView.findViewById(R.id.textViewDay);
            TextView textViewSubject = itemView.findViewById(R.id.textViewSubject);
            TextView textViewTime = itemView.findViewById(R.id.textViewTime);

            // Устанавливаем текст в TextView
            textViewDay.setText(day);
            textViewSubject.setText(subject);
            textViewTime.setText(time);

            // Добавляем itemView в макет
            layout.addView(itemView);
        }

        // Закрываем курсор после использования
        cursor.close();
    }
}