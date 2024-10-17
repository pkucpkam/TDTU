package com.example.bai1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText txtUser, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        Button btnSignIn = findViewById(R.id.btnSignIn);
        Button btnReset = findViewById(R.id.btnReset);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUser.getText().toString();
                String password = txtPass.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập username hoặc password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (username.equals("admin") && password.equals("admin1234")) {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.length() < 6 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*")) {
                        Toast.makeText(MainActivity.this, "Mật khẩu không đúng yêu cầu", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(MainActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUser.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (username.equals("admin")) {
                    Toast.makeText(MainActivity.this, "Reset mật khẩu thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Username không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}