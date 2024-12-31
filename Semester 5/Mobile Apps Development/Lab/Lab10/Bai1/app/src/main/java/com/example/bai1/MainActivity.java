package com.example.bai1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.bai1.R;

public class MainActivity extends AppCompatActivity {
    private List<Contact> contactsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        mAdapter = new ContactsAdapter(getBaseContext(), contactsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // set the adapter
        recyclerView.setAdapter(mAdapter);

        prepareContactData();
    }

    private void prepareContactData() {
        Contact contact = new Contact("Vo Nhat Hao", "0912 345 632");
        contactsList.add(contact);

        Contact contact2 = new Contact("Pham Van Phuc", "0944 345 632");
        contactsList.add(contact2);

        Contact contact3 = new Contact("Huynh Trong Tri", "0912 345 666");
        contactsList.add(contact3);

        mAdapter.notifyDataSetChanged();
    }
}