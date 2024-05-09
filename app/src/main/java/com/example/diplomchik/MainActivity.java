package com.example.diplomchik;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    }

    private void insertTestData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HomeworkContract.HomeworkEntry.COLUMN_SUBJECT, "Математика");
        values.put(HomeworkContract.HomeworkEntry.COLUMN_DESCRIPTION, "Выполнить упражнения 1-10, страница 50.");
        values.put(HomeworkContract.HomeworkEntry.COLUMN_DUE_DATE, "2024-04-30");
        values.put(HomeworkContract.HomeworkEntry.COLUMN_COMPLETED, 0); // Значение по умолчанию для нового столбца "completed"
        db.insert(HomeworkContract.HomeworkEntry.TABLE_NAME, null, values);
    }

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
}
