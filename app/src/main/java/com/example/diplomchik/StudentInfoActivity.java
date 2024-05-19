package com.example.diplomchik;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.diplomchik.Helpers.DBContract;
import com.example.diplomchik.Helpers.DBHelper;

public class StudentInfoActivity extends AppCompatActivity {

    private static final String TAG = "StudentInfoActivity";
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);



        Button buttonSchedule = findViewById(R.id.button_schedule);
        Button buttonHomework = findViewById(R.id.button_homework);
        Button buttonGrades = findViewById(R.id.button_grades);

        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentInfoActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });


        buttonHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentInfoActivity.this, RatingsActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);

        String login = getIntent().getStringExtra("login");
        if (login != null) {
            displayStudentInfo(login);
        } else {
            Log.e(TAG, "Login is null");
        }
    }

    private void displayStudentInfo(String login) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            String[] projection = {
                    DBContract.StudentEntry.COLUMN_NAME,
                    DBContract.StudentEntry.COLUMN_BIRTHDATE,
                    DBContract.StudentEntry.COLUMN_CLASS
            };
            String selection = DBContract.StudentEntry.COLUMN_LOGIN + " = ?";
            String[] selectionArgs = { login };

            cursor = db.query(
                    DBContract.StudentEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            TextView nameTextView = findViewById(R.id.text_view_name);
            TextView birthdateTextView = findViewById(R.id.text_view_birthdate);
            TextView classTextView = findViewById(R.id.text_view_class);

            if (cursor != null && cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.StudentEntry.COLUMN_NAME));
                String birthdate = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.StudentEntry.COLUMN_BIRTHDATE));
                String studentClass = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.StudentEntry.COLUMN_CLASS));

                nameTextView.setText("Ф.И.О.: " + name);
                birthdateTextView.setText("Дата рождения: " + birthdate);
                classTextView.setText("Класс: " + studentClass);
            } else {
                Log.e(TAG, "No student found with login: " + login);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error displaying student info", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
}
