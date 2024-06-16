package com.example.diplomchik;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.diplomchik.Helpers.DBContract;
import com.example.diplomchik.Helpers.DBHelper;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);


        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText loginEditText = findViewById(R.id.editTextLogin);
                EditText passwordEditText = findViewById(R.id.editTextPassword);

                String login = loginEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (validateLogin(login, password)) {
                    Intent intent = new Intent(LoginActivity.this, StudentInfoActivity.class);
                    intent.putExtra("login", login);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateLogin(String login, String password) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            String[] projection = {
                    DBContract.StudentEntry.COLUMN_LOGIN,
                    DBContract.StudentEntry.COLUMN_PASSWORD
            };
            String selection = DBContract.StudentEntry.COLUMN_LOGIN + " = ? AND " + DBContract.StudentEntry.COLUMN_PASSWORD + " = ?";
            String[] selectionArgs = { login, password };

            cursor = db.query(
                    DBContract.StudentEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String dbLogin = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.StudentEntry.COLUMN_LOGIN));
                String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.StudentEntry.COLUMN_PASSWORD));
                Log.d(TAG, "DB Login: " + dbLogin + ", DB Password: " + dbPassword);
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error validating login", e);
            return false;
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