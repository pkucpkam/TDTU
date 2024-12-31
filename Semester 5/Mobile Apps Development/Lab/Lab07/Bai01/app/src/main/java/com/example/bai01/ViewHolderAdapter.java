package com.example.bai01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Contact> contactList;

    public ViewHolderAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        String displayName = contact.getFirstName() + " " + contact.getLastName();
        holder.textView.setText(displayName);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}
