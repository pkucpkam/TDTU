package com.example.bai1.recycler;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1.R;

import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHolder> {

    private List<DownloadFile> files;

    public FileAdapter() {
        this.files = new ArrayList<>();
    }

    public List<DownloadFile> getFiles() {
        return files;
    }

    public void setFiles(List<DownloadFile> files) {
        this.files.clear();
        this.files.addAll(files);
        //this.files.get(0).setLocalPath("/storage/sdcard/DOWNLOAD/Javascript Tutorial.mp4");
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_file, parent, false);
        return new FileHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder holder, int position) {
        DownloadFile file = files.get(position);
        holder.bind(file, position);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void add(DownloadFile downloadFile) {
        files.add(0, downloadFile);
        //notifyItemInserted(0);
        notifyDataSetChanged();
    }

    public static class FileHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {


        ImageView icon;
        TextView name, size, status;
        ProgressBar progressBar;

        public FileHolder(@NonNull View root) {
            super(root);

            icon = root.findViewById(R.id.icon);
            name = root.findViewById(R.id.name);
            size = root.findViewById(R.id.size);
            status = root.findViewById(R.id.status);
            progressBar = root.findViewById(R.id.progressBar);

            root.setOnCreateContextMenuListener(this);
        }

        public void bind(DownloadFile file, int position) {

            if (file.getName() == null || file.getName().isEmpty()) {
                icon.setImageResource(R.drawable.icon_other);
                name.setText("Reading file name...");
                size.setText("Reading file size...");
            }else {
                icon.setImageResource(file.getIcon());
                name.setText(file.getName());
                size.setText(file.getDisplaySize());
            }


            int colorSuccess = ContextCompat.getColor(icon.getContext(), R.color.success);
            int colorFail = ContextCompat.getColor(icon.getContext(), R.color.fail);
            int colorWaiting = ContextCompat.getColor(icon.getContext(), R.color.waiting);

            if (file.isWaiting()) {
                status.setText("Waiting");
                status.setTextColor(colorWaiting);
                status.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            else if (file.isCompleted()) {
                status.setText("Complete");
                status.setTextColor(colorSuccess);
                status.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }else if (file.isFailed()) {
                status.setText("Failed");
                status.setTextColor(colorFail);
                status.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            else {
                progressBar.setProgress(file.getProgress());
                status.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(1, this.getAdapterPosition(), 0, "Open");
            menu.add(2, this.getAdapterPosition(), 1, "Detail");
            menu.add(3, this.getAdapterPosition(), 2, "Delete");
            menu.add(4, this.getAdapterPosition(), 3, "Re-download");
        }
    }
}