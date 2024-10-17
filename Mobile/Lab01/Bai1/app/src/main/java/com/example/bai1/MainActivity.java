package com.example.bai1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        ButtonShow
        Button myButton = findViewById(R.id.loginButton);
        CheckBox checkBoxRememberMe = findViewById(R.id.checkBox2);

        myButton.setOnClickListener(v -> {

            final EditText getUsername = findViewById(R.id.editTextUsername);
            final EditText getPassword = findViewById(R.id.editTextPassword);

            final String username = getUsername.getText().toString();
            final String password = getPassword.getText().toString();


            if (username.equals("admin") && password.equals("123456")) {
                finish();
            } else {
                String message;
                if (username.isEmpty()) {
                    message = "Yêu cầu nhập Username";
                    getUsername.requestFocus();
                }
                else if (password.isEmpty()) {
                    message = "Yêu cầu nhập Password";
                    getPassword.requestFocus();
                }
                else {
                    message = "Tên đăng nhập hoặc mật khẩu không chính xác";
                    getPassword.requestFocus();
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        checkBoxRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Tài khoản của bạn sẽ được ghi nhớ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Bạn sẽ cần phải đăng nhập trong các lần tiếp theo", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}