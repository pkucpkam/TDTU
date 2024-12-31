package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;


public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        Button submitBtn = findViewById(R.id.submitBtn);
        EditText nameEditText = findViewById(R.id.name);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email_key");

        // Khởi tạo TextView và hiển thị tên trong TextView
        TextView textView = findViewById(R.id.textView2);
        textView.setText("Xin chào " + email + ", vui lòng nhập tên");

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = nameEditText.getText().toString();
                Log.d("MainActivity2", userName);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("user_name_key", userName); // Trả lại tên người dùng
                setResult(RESULT_OK, resultIntent);
                finish(); // Kết thúc Activity
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}