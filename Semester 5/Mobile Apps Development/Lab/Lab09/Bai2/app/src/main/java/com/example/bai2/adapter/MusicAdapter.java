package com.example.bai2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai2.R;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private final Context context;
    private final List<Music> dataSource;
    private final OnMusicClickListener onMusicClickListener;

    public MusicAdapter(Context context, List<Music> dataSource, OnMusicClickListener onMusicClickListener) {
        this.context = context;
        this.dataSource = dataSource;
        this.onMusicClickListener = onMusicClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvMusicName.setText(dataSource.get(position).getName());
        holder.viewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMusicClickListener.onMusicClick(dataSource.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface OnMusicClickListener {
        void onMusicClick(Music music);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMusicName;
        View viewContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewContainer = itemView;
            tvMusicName = itemView.findViewById(R.id.tv_music_name);
        }
    }
}