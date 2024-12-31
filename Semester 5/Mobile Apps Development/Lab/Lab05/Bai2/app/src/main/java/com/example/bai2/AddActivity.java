package com.example.bai2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private EditText nameTxt, placeTxt, dateTxt, timeTxt;
    private String[] places = {"Place 1", "Place 2", "Place 3", "Place 4"};
    private Calendar selectedDate = Calendar.getInstance();
    private Calendar selectedTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("bai02");
        }

        // Thiết lập view và xử lý WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các EditText
        nameTxt = findViewById(R.id.nameTxt);
        placeTxt = findViewById(R.id.placeTxt);
        dateTxt = findViewById(R.id.dateTxt);
        timeTxt = findViewById(R.id.timeTxt);

        // Lấy ngày và giờ hiện tại
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int hour = currentDate.get(Calendar.HOUR_OF_DAY);
        int minute = currentDate.get(Calendar.MINUTE);

        // Đặt ngày hiện tại cho dateTxt
        dateTxt.setText(String.format("%02d/%02d/%d", day, month + 1, year));

        // Đặt giờ hiện tại cho timeTxt
        timeTxt.setText(String.format("%02d:%02d", hour, minute));

        // Lưu lại ngày và giờ hiện tại trong selectedDate và selectedTime để người dùng có thể chỉnh sửa
        selectedDate.set(year, month, day);
        selectedTime.set(Calendar.HOUR_OF_DAY, hour);
        selectedTime.set(Calendar.MINUTE, minute);

        // Xử lý chọn địa điểm
        placeTxt.setOnClickListener(view -> showPlaceDialog());

        // Xử lý chọn ngày
        dateTxt.setOnClickListener(view -> showDatePickerDialog());

        // Xử lý chọn giờ
        timeTxt.setOnClickListener(view -> showTimePickerDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Xử lý khi nhấn nút "Back"
            finish();
            return true;
        } else if (id == R.id.action_save) {
            saveData(); // Gọi phương thức lưu dữ liệu
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPlaceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Place")
                .setItems(places, (dialog, which) -> placeTxt.setText(places[which]))
                .show();
    }

    private void showDatePickerDialog() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            selectedDate.set(year1, month1, dayOfMonth);
            dateTxt.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        int hour = selectedTime.get(Calendar.HOUR_OF_DAY);
        int minute = selectedTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedTime.set(Calendar.MINUTE, minute1);
            timeTxt.setText(String.format("%02d:%02d", hourOfDay, minute1));
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void saveData() {
        TextInputLayout nameInputLayout = findViewById(R.id.nameInputLayout);
        TextInputLayout placeInputLayout = findViewById(R.id.placeInputLayout);
        TextInputLayout dateInputLayout = findViewById(R.id.dateInputLayout);
        TextInputLayout timeInputLayout = findViewById(R.id.timeInputLayout);

        String name = nameTxt.getText().toString().trim();
        String place = placeTxt.getText().toString().trim();
        String date = dateTxt.getText().toString().trim();
        String time = timeTxt.getText().toString().trim();

        boolean isValid = true;

        if (name.isEmpty()) {
            nameInputLayout.setError("Please enter event name");
            isValid = false;
        } else {
            nameInputLayout.setError(null); // Xóa lỗi nếu đã nhập dữ liệu
        }

        if (place.isEmpty()) {
            placeInputLayout.setError("Please select a place");
            isValid = false;
        } else {
            placeInputLayout.setError(null);
        }

        if (date.isEmpty()) {
            dateInputLayout.setError("Please select a date");
            isValid = false;
        } else {
            dateInputLayout.setError(null);
        }

        if (time.isEmpty()) {
            timeInputLayout.setError("Please select a time");
            isValid = false;
        } else {
            timeInputLayout.setError(null);
        }

        // Nếu tất cả dữ liệu hợp lệ, gửi dữ liệu về MainActivity
        if (isValid) {
            String dateTime = date + " " + time;
            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("place", place);
            intent.putExtra("dateTime", dateTime);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
