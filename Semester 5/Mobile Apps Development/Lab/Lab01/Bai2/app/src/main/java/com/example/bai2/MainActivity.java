package com.example.bai2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button myButton = findViewById(R.id.clickMeButton);
        EditText getText = findViewById(R.id.content);


        myButton.setOnClickListener(v -> {
            final TextView currentContent = findViewById(R.id.currentContent);
            String text = getText.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui long nhap thong tin", Toast.LENGTH_SHORT).show();
            } else {
                getText.setText("");
                currentContent.setText(text);
            }
        });

        getText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String currentText = getText.getText().toString();
                if (currentText.contains("on")) {
                    myButton.setEnabled(true);
                } else if (currentText.contains("off")) {
                    myButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}