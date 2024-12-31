package com.example.bai4;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gvComputers;
    private List<Computer> computers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gvComputers = findViewById(R.id.gvComputers);

        generateRandomItems();
        setCustomAdapter();
    }

    private void setCustomAdapter() {
        ComputerArrayAdapter computerArrayAdapter = new ComputerArrayAdapter(this,
                R.layout.list_computer_item, computers);
        gvComputers.setAdapter(computerArrayAdapter);
    }

    private void generateRandomItems() {
        for (int i = 1; i < 30; i++) {
            String computerName = "PC " + i;
            Computer newComputer = new Computer(computerName, true);
            computers.add(newComputer);
        }
    }
}