package com.example.bai2;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView nameTxt, placeTxt,dateTxt, timeTxt;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchCheck;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTxt = itemView.findViewById(R.id.nameTxt);
        placeTxt = itemView.findViewById(R.id.placeTxt);
        timeTxt = itemView.findViewById(R.id.timeTxt);
        dateTxt = itemView.findViewById(R.id.dateTxt);
        switchCheck = itemView.findViewById(R.id.switch1);
    }
}
