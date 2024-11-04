package com.example.bai01;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_LAUNCH_COUNT = "launch_count";
    private static final String KEY_TEXT_COLOR = "text_color";
    private static final String KEY_BACKGROUND_COLOR = "background_color";

    private TextView centerNumber;
    private EditText textColorInput;
    private EditText backgroundColorInput;
    private Button saveButton;
    private SharedPreferences sharedPreferences;
    private int launchCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        centerNumber = findViewById(R.id.centerNumber);
        textColorInput = findViewById(R.id.textColorInput);
        backgroundColorInput = findViewById(R.id.backgroundColorInput);
        saveButton = findViewById(R.id.saveButton);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        launchCount = sharedPreferences.getInt(KEY_LAUNCH_COUNT, 0) + 1;
        centerNumber.setText(String.valueOf(launchCount));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_LAUNCH_COUNT, launchCount);
        editor.apply();

        String textColor = sharedPreferences.getString(KEY_TEXT_COLOR, "#FFFFFF");
        String backgroundColor = sharedPreferences.getString(KEY_BACKGROUND_COLOR, "#1A1FD1");

        textColorInput.setText(textColor);
        backgroundColorInput.setText(backgroundColor);

        try {
            centerNumber.setTextColor(Color.parseColor(textColor));
            centerNumber.setBackgroundColor(Color.parseColor(backgroundColor));
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Mã màu không hợp lệ", Toast.LENGTH_SHORT).show();
        }

        saveButton.setOnClickListener(v -> {
            String newTextColor = textColorInput.getText().toString();
            String newBackgroundColor = backgroundColorInput.getText().toString();

            try {
                centerNumber.setTextColor(Color.parseColor(newTextColor));
                centerNumber.setBackgroundColor(Color.parseColor(newBackgroundColor));

                editor.putString(KEY_TEXT_COLOR, newTextColor);
                editor.putString(KEY_BACKGROUND_COLOR, newBackgroundColor);
                editor.apply();

                Toast.makeText(MainActivity.this, "Màu đã được lưu!", Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {
                Toast.makeText(MainActivity.this, "Mã màu không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
