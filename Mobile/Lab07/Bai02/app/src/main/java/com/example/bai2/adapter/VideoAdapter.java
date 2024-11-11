package com.example.bai2.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bai2.R;
import com.example.bai2.model.Video;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Video> dataSource;

    public VideoAdapter(Context context, ArrayList<Video> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }

    public void deleteSelectedVideo() throws IntentSender.SendIntentException {
        ContentResolver contentResolver = context.getContentResolver();
        List<Uri> uriList = new ArrayList<>();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        for (int i = 0; i < dataSource.size(); i++) {
            if (dataSource.get(i).isSelected()) {
                uriList.add(ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, Long.parseLong(dataSource.get(i).getId())));
                dataSource.remove(i);
                i--;
            }
        }
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            pendingIntent = MediaStore.createDeleteRequest(contentResolver, uriList);
        }
        ((Activity) context).startIntentSenderForResult(pendingIntent.getIntentSender(), 2, null, 0, 0, 0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bindData(position, dataSource.get(position));
        holder.cbVideoSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.get(position).setSelected(holder.cbVideoSelected.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View viewContainer;
        ShapeableImageView ivThumbnail;
        TextView tvVideoName;
        TextView tvVideoDuration;
        CheckBox cbVideoSelected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewContainer = itemView;
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
            tvVideoName = itemView.findViewById(R.id.tv_video_name);
            tvVideoDuration = itemView.findViewById(R.id.tv_video_duration);
            cbVideoSelected = itemView.findViewById(R.id.cb_video_selected);
        }

        public void bindData(int postion, Video video) {
            Glide.with(context).asBitmap().load(video.getUri()).into(ivThumbnail);
            tvVideoName.setText(video.getName());
            tvVideoDuration.setText(String.valueOf(DateUtils.formatElapsedTime(video.getDuration() / 1000)));
        }
    }
}
