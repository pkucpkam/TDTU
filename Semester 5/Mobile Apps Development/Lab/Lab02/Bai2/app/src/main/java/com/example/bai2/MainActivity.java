package com.example.bai2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView viewFollower;
    private Button btnFollow;
    private boolean isFollowing = false;
    private int followersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        viewFollower = findViewById(R.id.viewFollower);
        TextView viewFollowing = findViewById(R.id.viewFollowing);
        btnFollow = findViewById(R.id.btnFollow);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        Random random = new Random();
        followersCount = random.nextInt(9901) + 100;
        int followingCount = random.nextInt(9901) + 100;

        viewFollower.setText(String.valueOf(followersCount));
        viewFollowing.setText(String.valueOf(followingCount));

        ratingBar.setRating(4.5f);
        ratingBar.setIsIndicator(true);

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFollowing) {
                    followersCount--;
                    viewFollower.setText(String.valueOf(followersCount));
                    btnFollow.setText("Follow");
                    isFollowing = false;
                    Toast.makeText(MainActivity.this, "Unfollow", Toast.LENGTH_SHORT).show();
                } else {
                    followersCount++;
                    viewFollower.setText(String.valueOf(followersCount));
                    btnFollow.setText("Unfollow");
                    isFollowing = true;
                    Toast.makeText(MainActivity.this, "Followed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}