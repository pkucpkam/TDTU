package com.example.bai01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        productList = new ArrayList<>();
        productList.add(new Product("Apple"));
        productList.add(new Product("Samsung"));
        productList.add(new Product("Nokia"));
        productList.add(new Product("Oppo"));

        adapter = new RecyclerViewAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_check_uncheck) {
            boolean allChecked = true;
            for (Product product : productList) {
                if (!product.isSelected()) {
                    allChecked = false;
                    break;
                }
            }
            for (Product product : productList) {
                product.setSelected(!allChecked);
            }
            adapter.notifyDataSetChanged();
            Toast.makeText(this, allChecked ? "Uncheck all" : "Check all", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_delete_selected) {
            showConfirmationDialog("Xóa mục đã chọn", "Bạn có chắc chắn muốn xóa các mục đã chọn không?", true);
            return true;
        } else if (id == R.id.action_delete_all) {
            showConfirmationDialog("Xóa tất cả", "Bạn có chắc chắn muốn xóa tất cả các mục không?", false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showConfirmationDialog(String title, String message, boolean deleteSelected) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (deleteSelected) {
                    List<Product> selectedProducts = new ArrayList<>();
                    for (Product product : productList) {
                        if (product.isSelected()) {
                            selectedProducts.add(product);
                        }
                    }
                    productList.removeAll(selectedProducts);
                    Toast.makeText(MainActivity.this, "Các mục đã chọn đã bị xóa", Toast.LENGTH_SHORT).show();
                } else {
                    productList.clear();
                    Toast.makeText(MainActivity.this, "Tất cả các mục đã bị xóa", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
