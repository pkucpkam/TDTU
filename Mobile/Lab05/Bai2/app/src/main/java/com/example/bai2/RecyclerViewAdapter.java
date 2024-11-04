package com.example.bai2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private List<Event> eventList;

    public RecyclerViewAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event =eventList.get(position);
        holder.nameTxt.setText(event.getName());
        holder.placeTxt.setText(event.getPlace());
        holder.timeTxt.setText(event.getTime());
        holder.switchCheck.setChecked(event.getCheck());


        holder.switchCheck.setOnCheckedChangeListener(null);
        holder.switchCheck.setChecked(event.getCheck());

        holder.itemView.setOnLongClickListener(v -> {
            v.showContextMenu();
            return true;
        });

        holder.switchCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            eventList.get(position).setCheck(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
