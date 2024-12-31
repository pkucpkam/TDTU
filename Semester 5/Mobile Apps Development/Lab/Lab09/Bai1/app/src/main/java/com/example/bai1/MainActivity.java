package com.example.bai1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1.network.Downloader;
import com.example.bai1.recycler.DownloadFile;
import com.example.bai1.recycler.FileAdapter;

import java.io.File;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Downloader.Callback {

    private EditText txtUrl;
    private Button btnDownload;
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private FileAdapter adapter;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Download Manager");

        if (!isNetworkAvailable()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        initViews();
        initObject();
        updateUI();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted for write external storage", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied for write external storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int action = item.getGroupId();
        if (action == 1) { // open
            DownloadFile downloadFile = adapter.getFiles().get(item.getItemId());

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setType("*/*");
            startActivity(intent);
        } else if (action == 2) {
            DownloadFile downloadFile = adapter.getFiles().get(item.getItemId());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("File Detail Info");
            builder.setMessage(downloadFile.toString());
            builder.setPositiveButton("OK", null);
            builder.setNegativeButton("Cancel", null);
            builder.show();
        } else if (action == 3) { // delete
            DownloadFile downloadFile = adapter.getFiles().get(item.getItemId());
            File file = new File(downloadFile.getLocalPath());

            file.delete();
            String downloadFileId = downloadFile.getId();
            adapter.getFiles().remove(item.getItemId());
            adapter.notifyDataSetChanged();
            dbHelper.removeDownloadFile(downloadFileId);

        } else if (action == 4) { // re-download
            DownloadFile downloadFile = adapter.getFiles().get(item.getItemId());
            if (downloadFile.isFailed()) {
                downloadFile(downloadFile.getDownloadUrl());
            }
        }
        return super.onContextItemSelected(item);
    }

    private void updateUI() {
        if (adapter.getItemCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void initObject() {
        dbHelper = new MyDatabaseHelper(this);
        adapter = new FileAdapter();
        adapter.setFiles(dbHelper.getAllDownloadFiles());
        recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        txtUrl = findViewById(R.id.txtUrl);
        btnDownload = findViewById(R.id.btnDownload);
        emptyView = findViewById(R.id.emptyView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String url = txtUrl.getText().toString();
        downloadFile(url);
    }

    private void downloadFile(String url) {
        File parent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.setDownloadUrl(url);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Downloader.download(downloadFile, parent, MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        // Create a File object from the URL
        File fileUpload = new File(url);
        String name = fileUpload.getName();  // Get the file name from the URL
        downloadFile.setLocalPath(parent.getPath() + "/" + name); // Construct the full path

        adapter.add(downloadFile);
    }


    @Override
    public void onFileInfoChanged(DownloadFile file) {
        // https://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DownloadFile downloadFile = adapter.getFiles().get(0);
                downloadFile.setProgress(file.getProgress());
                downloadFile.setSize(file.getSize());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onComplete(DownloadFile file) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DownloadFile downloadFile = adapter.getFiles().get(0);
                downloadFile.markAsComplete();
                adapter.notifyDataSetChanged();
                dbHelper.addDownloadFile(downloadFile);
            }
        });
    }

    @Override
    public void onError(Throwable t) {

    }
}