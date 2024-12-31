package com.example.bai01;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

public class AddContactActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, phoneEditText, emailEditText, companyEditText;
    private static final int REQUEST_CODE_WRITE_CONTACTS = 2;
    private String firstName, lastName, phone, email, company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact2);

        // Initialize EditText fields
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        phoneEditText = findViewById(R.id.phone);
        emailEditText = findViewById(R.id.email);
        companyEditText = findViewById(R.id.company);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            firstName = firstNameEditText.getText().toString();
            lastName = lastNameEditText.getText().toString();
            phone = phoneEditText.getText().toString();
            email = emailEditText.getText().toString();
            company = companyEditText.getText().toString(); // Changed to company

            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return true;
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS},
                        REQUEST_CODE_WRITE_CONTACTS);
            } else {
                saveContactToPhone(firstName, lastName, phone, email, company);
                finish();
            }
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveContactToPhone(String firstName, String lastName, String phone, String email, String company) {
        ContentResolver contentResolver = getContentResolver();

        ContentValues values = new ContentValues();
        values.put(ContactsContract.RawContacts.ACCOUNT_TYPE, "");
        values.put(ContactsContract.RawContacts.ACCOUNT_NAME, "");
        Uri rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);

        if (rawContactUri == null) {
            Toast.makeText(this, "Failed to create new contact.", Toast.LENGTH_SHORT).show();
            return;
        }

        long rawContactId = ContentUris.parseId(rawContactUri);

        if (firstName != null && !firstName.isEmpty()) {
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstName);  // First name
            values.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastName);  // Last name
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
        }

        if (phone != null && !phone.isEmpty()) {
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
        }

        if (email != null && !email.isEmpty()) {
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Email.DATA, email);
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
        }

        if (company != null && !company.isEmpty()) {
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/organization");
            values.put(ContactsContract.Data.DATA1, company);
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
        }

        Toast.makeText(this, "Contact saved to phone", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_WRITE_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveContactToPhone(firstName, lastName, phone, email, company); // Now passing company
                finish();
            } else {
                Toast.makeText(this, "Permission denied. Can't save contact.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
