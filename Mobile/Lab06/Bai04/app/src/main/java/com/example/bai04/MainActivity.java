package com.example.bai04;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FileAdapter fileAdapter;
    private List<FileItem> fileItemList;
    private List<File> directoryHistory = new ArrayList<>();
    private File currentDirectory;

    private static final int REQUEST_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileItemList = new ArrayList<>();
        fileAdapter = new FileAdapter(fileItemList, this::onItemClick);
        recyclerView.setAdapter(fileAdapter);

        checkAndRequestPermissions();

        findViewById(R.id.textView4).setOnClickListener(v -> onBackPressed());
    }

    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSIONS);
        } else {
            loadFiles(Environment.getExternalStorageDirectory());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                loadFiles(Environment.getExternalStorageDirectory());
            } else {
                Toast.makeText(this, "Permission denied. Cannot access external storage.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void loadFiles(File directory) {
        if (directory == null) {
            directory = getExternalFilesDir(null);
        }

        if (directory != null && directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                List<FileItem> newFileItems = new ArrayList<>();
                for (File file : files) {
                    FileItem fileItem = new FileItem(file.getName(), file.isDirectory());
                    newFileItems.add(fileItem); // Thêm file và folder vào danh sách
                    Log.d("MainActivity", "File/Folder: " + file.getName() + " | Is Directory: " + file.isDirectory());
                }
                fileAdapter.updateFileList(newFileItems); // Cập nhật hiển thị

                if (directoryHistory.isEmpty() || !directory.equals(directoryHistory.get(directoryHistory.size() - 1))) {
                    directoryHistory.add(directory);
                }
                currentDirectory = directory;
            }
        } else {
            Log.d("MainActivity", "Directory does not exist or is not a directory");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create_file) {
            createNewFile();
            return true;
        }
        else if (id == R.id.action_create_folder) {
            createNewFolder();
            return true;
        }
        else if (id == R.id.action_delete) {
            deleteSelectedFiles();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewFile() {
        if (currentDirectory == null) {
            currentDirectory = getExternalFilesDir(null);
        }

        File newFile = new File(currentDirectory, "NewFile_" + System.currentTimeMillis() + ".txt");
        try {
            if (newFile.createNewFile()) {
                Toast.makeText(this, "File created: " + newFile.getName(), Toast.LENGTH_SHORT).show();
                loadFiles(currentDirectory);
            } else {
                Toast.makeText(this, "Failed to create file", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating file", Toast.LENGTH_SHORT).show();
        }
    }

    private void createNewFolder() {
        if (currentDirectory == null) {
            currentDirectory = getExternalFilesDir(null);
        }

        File newFolder = new File(currentDirectory, "NewFolder_" + System.currentTimeMillis());
        if (newFolder.mkdirs()) {
            Toast.makeText(this, "Folder created: " + newFolder.getName(), Toast.LENGTH_SHORT).show();
            loadFiles(currentDirectory);
        } else {
            Toast.makeText(this, "Failed to create folder", Toast.LENGTH_SHORT).show();
        }
    }

    private void onItemClick(FileItem fileItem) {
        if (fileItem.isDirectory()) {
            File directory = new File(currentDirectory, fileItem.getName());
            loadFiles(directory);
        } else {
            Toast.makeText(this, "File clicked: " + fileItem.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSelectedFiles() {
        Set<FileItem> selectedItems = fileAdapter.getSelectedItems();

        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "No files or folders selected for deletion.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean allDeleted = true;
        for (FileItem item : selectedItems) {
            File file = new File(currentDirectory, item.getName());
            boolean deleted = deleteRecursively(file);
            if (!deleted) allDeleted = false;
        }

        if (allDeleted) {
            Toast.makeText(this, "Selected files and folders deleted successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Some files or folders could not be deleted.", Toast.LENGTH_SHORT).show();
        }

        fileAdapter.getSelectedItems().clear();
        loadFiles(currentDirectory);
    }

    // Hàm xóa đệ quy cho thư mục
    private boolean deleteRecursively(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursively(child);
            }
        }
        return fileOrDirectory.delete();
    }

    @Override
    public void onBackPressed() {
        if (directoryHistory.size() > 1) {
            directoryHistory.remove(directoryHistory.size() - 1);
            File previousDirectory = directoryHistory.get(directoryHistory.size() - 1);
            loadFiles(previousDirectory);
        } else {
            super.onBackPressed();
        }
    }
}
