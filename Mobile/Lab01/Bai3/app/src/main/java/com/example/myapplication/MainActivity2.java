package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    EditText txtName, txtSex, txtLove, txtEmail, txtAddress, txtHobby, txtSwimAbility, txtToeicPoint, txtNoti;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        txtName = findViewById(R.id.txtName);
        txtSex = findViewById(R.id.txtSex);
        txtLove = findViewById(R.id.txtLove);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        txtHobby = findViewById(R.id.txtHobby);
        txtSwimAbility = findViewById(R.id.txtSwimAbility);
        txtToeicPoint = findViewById(R.id.txtToeicPoint);
        txtNoti = findViewById(R.id.txtNoti);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String address = getIntent().getStringExtra("address");
        String gender = getIntent().getStringExtra("gender");
        String hobbies = getIntent().getStringExtra("hobbies");
        int toeicScore = getIntent().getIntExtra("toeicScore", 0);
        String love = getIntent().getStringExtra("love");
        int swimAbility = getIntent().getIntExtra("rating",0);
        String strSwimAbility = String.valueOf(swimAbility);
        String noti = getIntent().getStringExtra("reNoti");

        txtName.setText(name);
        txtSex.setText(gender);
        txtLove.setText(love);
        txtEmail.setText(email);
        txtAddress.setText(address);
        txtHobby.setText(hobbies);
        txtSwimAbility.setText(String.format("%s/5", strSwimAbility));
        txtToeicPoint.setText(String.valueOf(toeicScore));
        txtNoti.setText(noti);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> finish());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}