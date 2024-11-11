package com.example.bai01;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    // Các view trong mỗi item
    public TextView textView;

    // Constructor của ViewHolder, nơi bạn ánh xạ các view
    public ViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.contact_item);
    }
}

