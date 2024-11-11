package com.example.bai2.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.IntentSender;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bai2.R;
import com.example.bai2.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Image> dataSource;

    public ImageAdapter(Context context, ArrayList<Image> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }

    public void deleteSelectedImages() throws IntentSender.SendIntentException {
        ContentResolver contentResolver = context.getContentResolver();
        List<Uri> uriList = new ArrayList<>();
        for (int i = 0; i < dataSource.size(); i++) {
            if (dataSource.get(i).isSelected()) {
                uriList.add(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Long.parseLong(dataSource.get(i).getId())));
                dataSource.remove(i);
                i--;
            }
        }
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            pendingIntent = MediaStore.createDeleteRequest(contentResolver, uriList);
        }
        ((Activity) context).startIntentSenderForResult(pendingIntent.getIntentSender(), 1, null, 0, 0, 0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bindData(position, dataSource.get(position));
        holder.cbPictureSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.get(position).setSelected(holder.cbPictureSelected.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View viewContainer;
        ImageView ivPicture;
        TextView tvPictureName;
        CheckBox cbPictureSelected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewContainer = itemView;
            ivPicture = itemView.findViewById(R.id.iv_image);
            tvPictureName = itemView.findViewById(R.id.tv_image_name);
            cbPictureSelected = itemView.findViewById(R.id.cb_image_selected);
        }

        public void bindData(int postion, Image image) {
            Glide.with(context).asBitmap().load(image.getUri()).into(ivPicture);
            tvPictureName.setText(image.getName());
        }
    }
}