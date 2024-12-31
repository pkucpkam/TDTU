package com.example.bai1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1.adapter.StudentAdapter;
import com.example.bai1.api.ApiResponse;
import com.example.bai1.api.ApiService;
import com.example.bai1.model.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    static final int ADD_STUDENT_REQUEST_CODE = 1;
    static final int EDIT_STUDENT_REQUEST_CODE = 2;

    RecyclerView rvStudents;
    StudentAdapter studentAdapter;
    ArrayList<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvStudents = findViewById(R.id.rv_students);

        rvStudents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (!isNetworkAvailable()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No internet connection").setMessage("Please check your internet connection and try again").setPositiveButton("OK", null);
            builder.create().show();
        } else {
            callApiGetStudents();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_management, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_student) {
            Intent intent = new Intent(MainActivity.this, CreateEditStudentActivity.class);
            intent.putExtra("action", "add");
            startActivityForResult(intent, ADD_STUDENT_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_STUDENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Student student = new Student();
                student.setName(data.getStringExtra("name"));
                student.setEmail(data.getStringExtra("email"));
                student.setPhone(data.getStringExtra("phone"));
                callApiAddStudent(student);
            }
        } else if (requestCode == EDIT_STUDENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", -1);
                Student student = studentAdapter.dataSource.get(position);
                student.setName(data.getStringExtra("name"));
                student.setEmail(data.getStringExtra("email"));
                student.setPhone(data.getStringExtra("phone"));
                callApiUpdateStudent(student);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = item.getGroupId();
        Student student = studentAdapter.dataSource.get(position);
        if (item.getItemId() == 0) { // edit
            int studentId = student.getId();
            Intent intent = new Intent(MainActivity.this, CreateEditStudentActivity.class);
            intent.putExtra("action", "edit");
            intent.putExtra("id", studentId);
            intent.putExtra("name", student.getName());
            intent.putExtra("email", student.getEmail());
            intent.putExtra("phone", student.getPhone());
            intent.putExtra("position", position);
            startActivityForResult(intent, EDIT_STUDENT_REQUEST_CODE);
        } else if (item.getItemId() == 1) { // delete
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure to delete this student?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    callApiDeleteStudent(student.getId(), position);
                }
            }).setNegativeButton("No", null);
            builder.create().show();
        }
        return super.onContextItemSelected(item);
    }

    private void callApiGetStudents() {
        ApiService.apiService.getAllStudent().enqueue(new Callback<ApiResponse<List<Student>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Student>>> call, Response<ApiResponse<List<Student>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Student>> apiResponse = response.body();
                    students = new ArrayList<>(apiResponse.getData());
                    studentAdapter = new StudentAdapter(MainActivity.this, students);
                    rvStudents.setAdapter(studentAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Error: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Student>>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callApiAddStudent(Student student) {
        ApiService.apiService.createStudent(student.getName(), student.getEmail(), student.getPhone()).enqueue(new Callback<ApiResponse<Integer>>() {
            @Override
            public void onResponse(Call<ApiResponse<Integer>> call, Response<ApiResponse<Integer>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Integer> apiResponse = response.body();
                    Integer studentId = apiResponse.getData();
                    student.setId(studentId);
                    studentAdapter.dataSource.add(student);
                    studentAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Error: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Integer>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callApiUpdateStudent(Student student) {
        ApiService.apiService.updateStudent(student.getId(), student.getName(), student.getEmail(), student.getPhone()).enqueue(new Callback<ApiResponse<Student>>() {
            @Override
            public void onResponse(Call<ApiResponse<Student>> call, Response<ApiResponse<Student>> response) {
                if (response.isSuccessful()) {
                    studentAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Error: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Student>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callApiDeleteStudent(Integer studentId, int position) {
        ApiService.apiService.deleteStudent(studentId).enqueue(new Callback<ApiResponse<Student>>() {
            @Override
            public void onResponse(Call<ApiResponse<Student>> call, Response<ApiResponse<Student>> response) {
                if (response.isSuccessful()) {
                    studentAdapter.dataSource.remove(position);
                    studentAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Error: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Student>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}