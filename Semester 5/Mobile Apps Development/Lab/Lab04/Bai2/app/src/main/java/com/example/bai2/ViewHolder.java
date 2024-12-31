package com.example.bai2;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView itemText;
    Button removeButton;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        itemText = itemView.findViewById(R.id.itemText);
        removeButton = itemView.findViewById(R.id.removeButton);
    }
}
