package com.example.bai4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    private TextView txtName, txtSmallName ,txtPhone, txtEmail, txtAddress, txtHomepage, editBtn, txtJob;
    private ShapeableImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editBtn = findViewById(R.id.editBtn);
        txtName = findViewById(R.id.txtName);
        txtSmallName = findViewById(R.id.txtSmallName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtJob = findViewById(R.id.txtJob);
        txtAddress = findViewById(R.id.txtAddress);
        txtHomepage = findViewById(R.id.txtHomepage);
        imgAvatar = findViewById(R.id.imgAvatar);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("name", txtSmallName.getText().toString());
                Log.d("MainActivity", "Name: " + txtName.getText().toString());
                intent.putExtra("major", txtJob.getText().toString());
                intent.putExtra("phone", txtPhone.getText().toString());
                intent.putExtra("email", txtEmail.getText().toString());
                intent.putExtra("address", txtAddress.getText().toString());
                intent.putExtra("homepage", txtHomepage.getText().toString());
                startActivityForResult(intent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String major = data.getStringExtra("major");
            String phone = data.getStringExtra("phone");
            String email = data.getStringExtra("email");
            String address = data.getStringExtra("address");
            String homepage = data.getStringExtra("homepage");

            String formattedName = name.toLowerCase().replaceAll(" ", "_");
            txtName.setText(formattedName);
            txtSmallName.setText(name);
            txtJob.setText(major);
            txtPhone.setText(phone);
            txtEmail.setText(email);
            txtAddress.setText(address);
            txtHomepage.setText(homepage);

            Bitmap avatarBitmap = data.getParcelableExtra("avatar");
            if (avatarBitmap != null) {
                imgAvatar.setImageBitmap(avatarBitmap);
            }
        }
    }
}