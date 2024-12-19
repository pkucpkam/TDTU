package com.example.bai1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateEditStudentActivity extends AppCompatActivity {
    EditText etId, etName, etEmail, etPhone;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_student);

        etId = findViewById(R.id.et_id);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btnSave = findViewById(R.id.btn_save);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String action = intent.getStringExtra("action");
        if ("add".equals(action)) {
            etId.setVisibility(View.GONE);
        } else if ("edit".equals(action)) {
            etId.setVisibility(View.VISIBLE);
            etId.setText(String.valueOf(intent.getIntExtra("id", -1)));
            etName.setText(intent.getStringExtra("name"));
            etEmail.setText(intent.getStringExtra("email"));
            etPhone.setText(intent.getStringExtra("phone"));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                if ("add".equals(action)) {
                    resultIntent.putExtra("name", etName.getText().toString());
                    resultIntent.putExtra("email", etEmail.getText().toString());
                    resultIntent.putExtra("phone", etPhone.getText().toString());
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else if ("edit".equals(action)) {
                    resultIntent.putExtra("position", intent.getIntExtra("position", -1));
                    resultIntent.putExtra("name", etName.getText().toString());
                    resultIntent.putExtra("email", etEmail.getText().toString());
                    resultIntent.putExtra("phone", etPhone.getText().toString());
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
