package com.example.bai1.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1.R;
import com.example.bai1.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    public final ArrayList<Student> dataSource;
    private final Context context;

    public StudentAdapter(Context context, ArrayList<Student> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_one_student, parent, false));
    }

    private Student getStudent(int positon) {
        return dataSource.get(positon);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position, getStudent(position));

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        View viewContainer;
        TextView tvName;
        TextView tvEmail;
        TextView tvPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewContainer = itemView;
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvPhone = itemView.findViewById(R.id.tv_phone);

            viewContainer.setOnCreateContextMenuListener(this);
        }

        public void bindData(int position, Student student) {
            tvName.setText(student.getName());
            tvEmail.setText(student.getEmail());
            tvPhone.setText(student.getPhone());
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), 0, 0, "Edit");
            menu.add(getAdapterPosition(), 1, 1, "Delete");
        }
    }
}