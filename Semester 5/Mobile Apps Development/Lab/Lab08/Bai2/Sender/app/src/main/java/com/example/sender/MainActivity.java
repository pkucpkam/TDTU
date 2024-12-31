package com.example.sender;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String ACTION_SEND_MESSAGE = "com.example.SENDER_ACTION";
    private static final String TAG = "SenderApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = findViewById(R.id.btn_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION_SEND_MESSAGE);
                intent.putExtra("message", "Hello from Sender!");
                Log.d(TAG, "Broadcast sent with message: Hello from Sender!");
                sendBroadcast(intent);

                Log.d(TAG, "Broadcast sent with message: Hello from Sender!");
            }
        });
    }
}
