package com.example.bai04;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FileItem> fileItemList;
    private ItemClickListener itemClickListener;
    private Set<FileItem> selectedItems = new HashSet<>();

    private static final int TYPE_FOLDER = 0;
    private static final int TYPE_FILE = 1;

    public FileAdapter(List<FileItem> fileItemList, ItemClickListener itemClickListener) {
        this.fileItemList = fileItemList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return fileItemList.get(position).isDirectory() ? TYPE_FOLDER : TYPE_FILE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_FOLDER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new FolderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
            return new FileViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FileItem fileItem = fileItemList.get(position);

        if (holder instanceof FolderViewHolder) {
            FolderViewHolder folderHolder = (FolderViewHolder) holder;
            folderHolder.folderNameTextView.setText(fileItem.getName());
            folderHolder.checkBox.setChecked(selectedItems.contains(fileItem));
            folderHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedItems.add(fileItem);
                } else {
                    selectedItems.remove(fileItem);
                }
            });
            folderHolder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(fileItem));
        } else if (holder instanceof FileViewHolder) {
            FileViewHolder fileHolder = (FileViewHolder) holder;
            fileHolder.fileNameTextView.setText(fileItem.getName());
            fileHolder.checkBox.setChecked(selectedItems.contains(fileItem));
            fileHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedItems.add(fileItem);
                } else {
                    selectedItems.remove(fileItem);
                }
            });
            fileHolder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(fileItem));
        }
    }

    @Override
    public int getItemCount() {
        return fileItemList.size();
    }

    public void updateFileList(List<FileItem> newFileItems) {
        fileItemList.clear();
        fileItemList.addAll(newFileItems);
        notifyDataSetChanged();
    }

    public Set<FileItem> getSelectedItems() {
        return selectedItems;
    }

    public static class FolderViewHolder extends RecyclerView.ViewHolder {
        TextView folderNameTextView;
        CheckBox checkBox;

        public FolderViewHolder(View itemView) {
            super(itemView);
            folderNameTextView = itemView.findViewById(R.id.folderNameTextView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        TextView fileNameTextView;
        CheckBox checkBox;

        public FileViewHolder(View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.fileNameTextView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public interface ItemClickListener {
        void onItemClick(FileItem fileItem);
    }
}
