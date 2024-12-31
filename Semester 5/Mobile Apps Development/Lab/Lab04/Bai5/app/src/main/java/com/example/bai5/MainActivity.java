package com.example.bai5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int TOTAL_ITEMS = 5;
    private Button btnCreate;
    private Button btnRemove;
    private TextView txtTotalUsers;
    private RecyclerView rvUsers;
    private List<User> users = new ArrayList<>();
    private UserRecyclerViewAdapter userRecyclerViewAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        rvUsers = findViewById(R.id.rv_users);
        btnCreate = findViewById(R.id.createBtn);
        btnRemove = findViewById(R.id.removeBtn);
        txtTotalUsers = findViewById(R.id.txtUsers);

        setCustomAdapter();
        handleEvents();
    }

    private void setCustomAdapter() {
        userRecyclerViewAdapter = new UserRecyclerViewAdapter(this, users);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(userRecyclerViewAdapter);
    }

    private void handleEvents() {
        btnCreate.setOnClickListener(view -> {
            populateUserDetails(5);
            setTotalUsers();
            userRecyclerViewAdapter.notifyDataSetChanged();
        });

        btnRemove.setOnClickListener(view -> {
            if (users.isEmpty()) {
                Toast.makeText(MainActivity.this, "List of users is empty", Toast.LENGTH_LONG).show();
            } else {
                removeUsers(5);
            }
            setTotalUsers();
            userRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    private void setTotalUsers() {
        int numberOfUsers = users.size();
        txtTotalUsers.setText("Total users: " + numberOfUsers);
    }

    private void removeUsers(int numberOfUsers) {
        for (int i = 0; i < numberOfUsers && !users.isEmpty(); i++) {
            users.remove(users.size() - 1);
        }
    }

    private void populateUserDetails(int numberOfUsers) {
        int indexFrom = users.size() + 1;
        int indexTo = indexFrom + numberOfUsers;
        for (int i = indexFrom; i < indexTo; i++) {
            users.add(new User("User " + i, "user" + i + "@tdtu.edu.vn"));
        }
    }
}
