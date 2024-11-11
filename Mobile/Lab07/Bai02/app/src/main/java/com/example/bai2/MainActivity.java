package com.example.bai2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai2.adapter.ImageAdapter;
import com.example.bai2.adapter.VideoAdapter;
import com.example.bai2.model.Image;
import com.example.bai2.model.Video;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvVideos;
    RecyclerView rvPictures;
    VideoAdapter videoAdapter;
    ImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvVideos = findViewById(R.id.VideoRecyclerView);
        rvPictures = findViewById(R.id.ImageRecyclerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }

        imageAdapter = new ImageAdapter(this, getAllPictures());
        rvPictures.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvPictures.setAdapter(imageAdapter);

        videoAdapter = new VideoAdapter(this, getAllVideos());
        rvVideos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvVideos.setAdapter(videoAdapter);
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted for read external storage", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied for read external storage", Toast.LENGTH_SHORT).show();
                openAppSettings();
            }
        } else if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted for write external storage", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied for write external storage", Toast.LENGTH_SHORT).show();
                openAppSettings();
            }
        }
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_management, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_selected_images) {
            try {
                imageAdapter.deleteSelectedImages();
            } catch (IntentSender.SendIntentException e) {
                throw new RuntimeException(e);
            }
        } else if (item.getItemId() == R.id.delete_selected_videos) {
            try {
                videoAdapter.deleteSelectedVideo();
            } catch (IntentSender.SendIntentException e) {
                throw new RuntimeException(e);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                imageAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                videoAdapter.notifyDataSetChanged();
            }
        }
    }

    public ArrayList<Image> getAllPictures() {
        ArrayList<Image> images = new ArrayList<>();
        String[] projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            @SuppressLint("Range") int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
            @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            try {
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                Image image = new Image(id, uri, name, size, path);
                images.add(image);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        cursor.close();
        return images;
    }

    public ArrayList<Video> getAllVideos() {
        ArrayList<Video> videos = new ArrayList<>();
        String[] projection = new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATA};
        String sortOrder = MediaStore.Video.Media.DATE_ADDED + " DESC";
        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            @SuppressLint("Range") int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
            @SuppressLint("Range") int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
            @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            try {
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                Video video = new Video(id, uri, name, duration, size, path);
                videos.add(video);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        cursor.close();
        return videos;
    }
}