package com.example.bai01;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static final int REQUEST_CODE_ADD_CONTACT = 2;

    private RecyclerView recyclerView;
    private ViewHolderAdapter adapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        contactList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ViewHolderAdapter(contactList);
        recyclerView.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            getContacts();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getContacts() {
        String[] projection = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                projection, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Contact contact = new Contact(displayName, "", "", "", ""); // Initialize with company as an empty string

                String phone = getPhoneNumber(contact.getFirstName());
                contact.setPhone(phone);

                String email = getEmail(contact.getFirstName());
                contact.setEmail(email);

                String company = getCompany(contact.getFirstName()); // Changed from getRelationship to getCompany
                contact.setCompany(company); // Set company info

                contactList.add(contact);
            }
            cursor.close();
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    @SuppressLint("Range")
    private String getPhoneNumber(String contactId) {
        String phone = "";
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{contactId},
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();
        }
        return phone;
    }

    @SuppressLint("Range")
    private String getEmail(String contactId) {
        String email = "";
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Email.DATA},
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{contactId},
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            cursor.close();
        }
        return email;
    }

    @SuppressLint("Range")
    private String getCompany(String contactId) { // Changed from getRelationship to getCompany
        String company = "";
        Cursor cursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data.DATA1}, // Company info is stored in DATA1
                ContactsContract.Data.CONTACT_ID + " = ? AND " +
                        ContactsContract.Data.MIMETYPE + " = ?",
                new String[]{
                        contactId,
                        "vnd.android.cursor.item/organization" // MIME type for organization info
                },
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            company = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DATA1));
            cursor.close();
        }
        return company;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                getContacts();
            } else {
                Toast.makeText(this, "Permission denied. Can't access contacts.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
