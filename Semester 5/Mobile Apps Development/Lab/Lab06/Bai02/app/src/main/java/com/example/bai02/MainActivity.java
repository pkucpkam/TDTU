package com.example.bai02;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText editTextContent;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextContent = findViewById(R.id.content);

        Button buttonReadInternal = findViewById(R.id.buttonReadInternal);
        Button buttonWriteInternal = findViewById(R.id.buttonWriteInternal);
        Button buttonReadExternal = findViewById(R.id.buttonReadExternal);
        Button buttonWriteExternal = findViewById(R.id.buttonWriteExternal);

        buttonReadInternal.setOnClickListener(v -> readInternalFile());
        buttonWriteInternal.setOnClickListener(v -> writeInternalFile(editTextContent.getText().toString()));
        buttonReadExternal.setOnClickListener(v -> readExternalFile());
        buttonWriteExternal.setOnClickListener(v -> writeExternalFile(editTextContent.getText().toString()));
    }

    private void writeInternalFile(String content) {
        if (!content.isEmpty()) {
            try (FileOutputStream fos = openFileOutput("internalFile.txt", MODE_PRIVATE)) {
                fos.write(content.getBytes());
                Toast.makeText(this, "Đã ghi vào bộ nhớ trong", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Nội dung trống", Toast.LENGTH_SHORT).show();
        }
    }

    private void readInternalFile() {
        File file = new File(getFilesDir(), "internalFile.txt");
        if (file.exists()) {
            try (FileInputStream fis = openFileInput("internalFile.txt")) {
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                editTextContent.setText(new String(buffer));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "File chưa tồn tại", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeExternalFile(String content) {
        if (checkPermission()) {
            if (isExternalStorageWritable()) {
                if (!content.isEmpty()) {
                    File file = new File(getExternalFilesDir(null), "externalFile.txt");
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(content.getBytes());
                        Toast.makeText(this, "Đã ghi vào bộ nhớ ngoài", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Nội dung trống", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Không thể ghi vào bộ nhớ ngoài", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readExternalFile() {
        if (isExternalStorageReadable()) {
            File file = new File(getExternalFilesDir(null), "externalFile.txt");
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[(int) file.length()];
                    fis.read(buffer);
                    editTextContent.setText(new String(buffer));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "File chưa tồn tại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Không thể đọc từ bộ nhớ ngoài", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Quyền truy cập bộ nhớ ngoài đã được cấp", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Không thể ghi vào bộ nhớ ngoài mà không có quyền", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
