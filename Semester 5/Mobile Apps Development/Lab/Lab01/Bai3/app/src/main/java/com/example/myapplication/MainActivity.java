package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextAddress;
    RatingBar ratingBar;
    SeekBar seekBar;
    RadioGroup radioGroupGender;
    RadioGroup radioGroupLove;
    CheckBox checkBoxSoccer, checkBoxSwim, checkBoxRun;
    Switch checkSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.txtPersonName);
        editTextEmail = findViewById(R.id.txtEmail);
        editTextAddress = findViewById(R.id.txtAddress);
        ratingBar = findViewById(R.id.ratingBar);
        seekBar = findViewById(R.id.toeicPoint);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupLove = findViewById(R.id.radioGroupLove);
        checkBoxSoccer = findViewById(R.id.checkBoxSoccer);
        checkBoxSwim = findViewById(R.id.checkBoxSwim);
        checkBoxRun = findViewById(R.id.checkBoxRun);
        checkSwitch = findViewById(R.id.reNoti);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            if (validateInputs()) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String address = editTextAddress.getText().toString();
                int rating = (int) ratingBar.getRating();
                int toeicScore = seekBar.getProgress() * 5;

                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                String gender = selectedGenderId == R.id.radioButtonMale ? "Nam" : "Nữ";

                int selectedFavId = radioGroupLove.getCheckedRadioButtonId();
                String love;

                if (selectedFavId == R.id.radioButtonMale1) {
                    love = "Nam";
                }
                else if (selectedFavId == R.id.radioButtonFemale1) {
                    love = "Nữ";
                }
                else {
                    love = "Both";
                }

                String reNoti;
                if (checkSwitch.isChecked()) {
                    reNoti = "Có";
                }
                else {
                    reNoti = "Không";
                }

                StringBuilder hobbies = new StringBuilder();
                if (checkBoxSoccer.isChecked()) hobbies.append("Bóng đá");
                if (checkBoxSwim.isChecked()) hobbies.append("Bơi lội");
                if (checkBoxRun.isChecked()) hobbies.append("Chạy bộ");

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("address", address);
                intent.putExtra("rating", rating);
                intent.putExtra("toeicScore", toeicScore);
                intent.putExtra("gender", gender);
                intent.putExtra("hobbies", hobbies.toString().trim());
                intent.putExtra("love", love );
                intent.putExtra("reNoti", reNoti);

                startActivity(intent);
                finish();
            }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean validateInputs() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Tên không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (address.isEmpty()) {
            Toast.makeText(this, "Địa chỉ không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (radioGroupGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Vui lòng chọn giới tính!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (radioGroupLove.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Vui lòng chọn sở thích!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}