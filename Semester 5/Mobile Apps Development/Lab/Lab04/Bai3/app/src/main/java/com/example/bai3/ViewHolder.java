package com.example.bai3;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView itemText;
    CheckBox checkBox;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        itemText = itemView.findViewById(R.id.itemText);
        checkBox = itemView.findViewById(R.id.checkBox);
    }
}
