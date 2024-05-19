package com.example.diplomchik;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.diplomchik.Helpers.DBContract;
import com.example.diplomchik.Helpers.DBHelper;

import java.util.ArrayList;

public class RatingsActivity extends AppCompatActivity {

    private TextView textViewSemester1, textViewSemester2;
    private ListView listViewSemester1, listViewSemester2;
    private Button btnSemester1, btnSemester2;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        dbHelper = new DBHelper(this);

        textViewSemester1 = findViewById(R.id.textViewSemester1);
        textViewSemester2 = findViewById(R.id.textViewSemester2);
        listViewSemester1 = findViewById(R.id.listViewSemester1);
        listViewSemester2 = findViewById(R.id.listViewSemester2);
        btnSemester1 = findViewById(R.id.btnSemester1);
        btnSemester2 = findViewById(R.id.btnSemester2);

        // Установка слушателей для кнопок
        btnSemester1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGrades(1, listViewSemester1);
                textViewSemester1.setVisibility(View.VISIBLE);
                textViewSemester2.setVisibility(View.GONE);
                listViewSemester1.setVisibility(View.VISIBLE);
                listViewSemester2.setVisibility(View.GONE);
            }
        });

        btnSemester2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGrades(2, listViewSemester2);
                textViewSemester1.setVisibility(View.GONE);
                textViewSemester2.setVisibility(View.VISIBLE);
                listViewSemester1.setVisibility(View.GONE);
                listViewSemester2.setVisibility(View.VISIBLE);
            }
        });

        // Загрузка оценок из базы данных и установка адаптеров по умолчанию
        loadGrades(1, listViewSemester1);
    }

    private void loadGrades(int semester, ListView listView) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DBContract.RatingsEntry.COLUMN_SUBJECT + ", AVG(" + DBContract.RatingsEntry.COLUMN_RATING + ") FROM " + DBContract.RatingsEntry.TABLE_NAME + " WHERE " + DBContract.RatingsEntry.COLUMN_SEMESTER + " = ? GROUP BY " + DBContract.RatingsEntry.COLUMN_SUBJECT, new String[]{String.valueOf(semester)});
        ArrayList<String> gradeList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String subject = cursor.getString(0);
                double averageRating = cursor.getDouble(1);
                gradeList.add(subject + ": " + averageRating);
            } while (cursor.moveToNext());
        }
        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gradeList);
        listView.setAdapter(adapter);
    }
}
