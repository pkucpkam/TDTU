package com.example.bai4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity2 extends AppCompatActivity {

    private EditText etxtJob, etxtName, etxtPhone, etxtEmail, etxtAddress, etxtHomepage;
    private ShapeableImageView shapeableImageView;
    private TextView txtName;
    private ImageButton cameraButton;
    private Button btnSave;
    private Bitmap avatarBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        txtName = findViewById(R.id.txtName);
        etxtJob = findViewById(R.id.etxtJob);
        etxtName = findViewById(R.id.etxtName);
        etxtPhone = findViewById(R.id.etxtPhone);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtAddress = findViewById(R.id.etxtAddress);
        etxtHomepage = findViewById(R.id.etxtHomepage);
        shapeableImageView = findViewById(R.id.shapeableImageView);
        cameraButton = findViewById(R.id.cameraButton);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        String newName = intent.getStringExtra("name").toLowerCase().replaceAll(" ", "_");
        txtName.setText(newName);
        etxtName.setText(intent.getStringExtra("name"));
        etxtJob.setText(intent.getStringExtra("major"));
        etxtPhone.setText(intent.getStringExtra("phone"));
        etxtEmail.setText(intent.getStringExtra("email"));
        etxtAddress.setText(intent.getStringExtra("address"));
        etxtHomepage.setText(intent.getStringExtra("homepage"));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", etxtName.getText().toString());
                resultIntent.putExtra("major", etxtJob.getText().toString());
                resultIntent.putExtra("phone", etxtPhone.getText().toString());
                resultIntent.putExtra("email", etxtEmail.getText().toString());
                resultIntent.putExtra("address", etxtAddress.getText().toString());
                resultIntent.putExtra("homepage", etxtHomepage.getText().toString());

                if (avatarBitmap != null) {
                    resultIntent.putExtra("avatar", avatarBitmap);
                }

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity2.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.CAMERA}, 2);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 2);

                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                avatarBitmap = (Bitmap) data.getExtras().get("data");
                shapeableImageView.setImageBitmap(avatarBitmap);
            }
        }
    }
}