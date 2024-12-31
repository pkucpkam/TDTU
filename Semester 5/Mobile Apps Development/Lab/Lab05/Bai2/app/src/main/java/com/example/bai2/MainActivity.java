package com.example.bai2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter adapter;
    private List<Event> eventList;
    private List<Event> filterEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView =findViewById(R.id.recyclerView);

        eventList = new ArrayList<>();
        eventList.add(new Event("Sinh hoat cong dan", "C102", "8/10/2024 18:25"));
        eventList.add(new Event("Huong dan luan van", "C102", "8/10/2024 18:25"));

        filterEventList = new ArrayList<>(eventList);

        adapter = new RecyclerViewAdapter(this, filterEventList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        registerForContextMenu(recyclerView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem switchItem = menu.findItem(R.id.app_bar_switch);
        View switchView = switchItem.getActionView();
        assert switchView != null;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchVisibility = switchView.findViewById(R.id.my_switch);
        switchVisibility.setChecked(true);

        switchVisibility.setOnCheckedChangeListener((buttonView, isChecked) -> {
            filterEventList.clear();
            if (isChecked) {
                // Khi Switch bật (ON), hiển thị tất cả các sự kiện
                Log.d("a", "b");
                filterEventList.addAll(eventList);
            } else {
                // Khi Switch tắt (OFF), chỉ hiển thị các sự kiện có check = false
                for (Event event : eventList) {
                    if (event.isCheck()) {  // Chỉ thêm các sự kiện có check = false
                        Log.d("Switch", "Thêm sự kiện: " + event.getName());
                        filterEventList.add(event);
                    }
                }
            }
            adapter.notifyDataSetChanged();


        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("Select Action");
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent, 1); // Sử dụng requestCode 1 để nhận diện kết quả
            return true;
        } else if (id == R.id.removeBtn) {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to delete all events?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        eventList.clear();
                        filterEventList.clear();
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
            return true;
        } else if (id == R.id.aboutBtn) {
            // Xử lý cho nút About
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) { // Kiểm tra requestCode và resultCode
            String name = data.getStringExtra("name");
            String place = data.getStringExtra("place");
            String dateTime = data.getStringExtra("dateTime");
            Log.d("a", dateTime);

            // Tạo một đối tượng Event mới với dữ liệu nhận được
            Event newEvent = new Event(name, place, dateTime);
            eventList.add(newEvent); // Thêm sự kiện vào danh sách chính
            filterEventList.add(newEvent); // Thêm sự kiện vào danh sách hiển thị
            adapter.notifyDataSetChanged(); // Cập nhật RecyclerView
        }
    }


}