package com.example.bai3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button removeAllButton = findViewById(R.id.removeAllButton);
        Button removeSelectedButton = findViewById(R.id.removeSelectedButton);

        productList = new ArrayList<>();
        productList.add(new Product("Apple"));
        productList.add(new Product("Samsung"));
        productList.add(new Product("Nokia"));
        productList.add(new Product("Oppo"));

        adapter = new RecyclerViewAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Sự kiện nút REMOVE ALL
        removeAllButton.setOnClickListener(v -> {
            productList.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "All items removed", Toast.LENGTH_SHORT).show();
        });

        // Sự kiện nút REMOVE SELECTED
        removeSelectedButton.setOnClickListener(v -> {
            List<Product> selectedProducts = new ArrayList<>();
            for (Product product : productList) {
                if (product.isSelected()) {
                    selectedProducts.add(product);
                }
            }
            productList.removeAll(selectedProducts);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Selected items removed", Toast.LENGTH_SHORT).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
