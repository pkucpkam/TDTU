package com.example.bai2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private CheckBox cbAndroid, cbIos, cbWin, cbRim;
    private Button btnClick;
    private TextView tvResults1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cbAndroid = findViewById(R.id.cbAndroid);
        cbIos = findViewById(R.id.cbIos);
        cbWin = findViewById(R.id.cbWin);
        cbRim = findViewById(R.id.cbRim);
        btnClick = findViewById(R.id.btnClick);
        tvResults1 = findViewById(R.id.tvResults1);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayResults();
            }
        });
    }

    private void displayResults() {
        // Prepare the result string based on checkbox selections
        StringBuilder result = new StringBuilder("The following were selected...\n");
        result.append("Android: ").append(cbAndroid.isChecked()).append("\n");
        result.append("iOS: ").append(cbIos.isChecked()).append("\n");
        result.append("Windows: ").append(cbWin.isChecked()).append("\n");
        result.append("RIM: ").append(cbRim.isChecked());

        // Display the results in the TextView
        tvResults1.setText(result.toString());
    }
}