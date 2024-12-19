package com.example.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "MessageChannel";
    private static final String TAG = "ReceiverApp";
    private static final String ACTION_SEND_MESSAGE = "com.example.SENDER_ACTION";

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_SEND_MESSAGE.equals(intent.getAction())) {
                String message = intent.getStringExtra("message");

                // Log to confirm that the broadcast was received
                Log.d(TAG, "Broadcast received with message: " + message);

                Toast.makeText(context, "Received message: " + message, Toast.LENGTH_LONG).show();


                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(
                            CHANNEL_ID,
                            "Message Channel",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );
                    notificationManager.createNotificationChannel(channel);
                }

                Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("New Message")
                        .setContentText(message != null ? message : "No message received")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .build();

                notificationManager.notify(1, notification);
            } else {
                Log.d(TAG, "ERROR");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Đăng ký BroadcastReceiver để lắng nghe broadcast từ Sender
        IntentFilter filter = new IntentFilter(ACTION_SEND_MESSAGE);
        registerReceiver(messageReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy đăng ký BroadcastReceiver để tránh rò rỉ bộ nhớ
        unregisterReceiver(messageReceiver);
    }
}
