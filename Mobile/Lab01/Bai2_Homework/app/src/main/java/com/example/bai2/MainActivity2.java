package com.example.bai2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private RadioGroup rgPlatforms;
    private Button btnShowResults;
    private TextView tvResults2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        rgPlatforms = findViewById(R.id.rgPlatforms);
        btnShowResults =  findViewById(R.id.btnShowResults);
        tvResults2 = findViewById(R.id.tvResults2);

        btnShowResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayResults2();
            }
        });
    }

    private void displayResults2() {
        int selectedId = rgPlatforms.getCheckedRadioButtonId();

        RadioGroup selectedRadioButton = findViewById(selectedId);

        if (selectedRadioButton != null) {
            tvResults2.setText("The following were selected... " + selectedRadioButton.getTextAlignment());
        } else {
            tvResults2.setText("No platform selected.");
        }
    }
}