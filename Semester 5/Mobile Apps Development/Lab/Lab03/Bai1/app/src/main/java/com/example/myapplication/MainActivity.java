package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText userEmail = findViewById(R.id.username);
        Button btnLogin = findViewById(R.id.loginBtn);
        TextView txtView = findViewById(R.id.textView2);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmailStr = userEmail.getText().toString();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("email_key", userEmailStr);
                startActivityForResult(intent, 1);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView txtView = findViewById(R.id.textView2);
        EditText username = findViewById(R.id.username);
        Button btnLogin = findViewById(R.id.loginBtn);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String userName = data.getStringExtra("user_name_key");
            if (userName != null) {
                username.setText(userName);
                txtView.setText("Hẹn gặp lại");
                username.setEnabled(false);
                btnLogin.setVisibility(View.GONE);
            }
        }
    }

}