package com.example.bai3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText linkEditText = findViewById(R.id.link);
        Button submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = linkEditText.getText().toString();

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }

                Intent myBrowserIntent = new Intent(MainActivity.this, WebViewActivity.class);
                myBrowserIntent.setData(Uri.parse(url));

                Uri webPage = Uri.parse(url);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, webPage);

                browserIntent.setData(Uri.parse(url));

                Intent i2 = Intent.createChooser(browserIntent, "Choose Browser");

                i2.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{myBrowserIntent});

                startActivity(i2);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

