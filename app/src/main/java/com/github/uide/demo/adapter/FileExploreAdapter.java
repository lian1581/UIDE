package com.github.uide.demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.github.uide.demo.R;
import com.github.uide.demo.utils.FileAdapterUtils;

import java.io.File;
import java.util.List;

public class FileExploreAdapter extends RecyclerView.Adapter<FileExploreAdapter.FileViewHold> {

    private File parentDir;
    private List<FileAdapterUtils.ItemContain> itemContains;
    private OnItemClickListener onItemClickListener;

    public FileExploreAdapter(File file) {
        this.parentDir = file;
    }

    private void init() {

    }

    @NonNull
    @Override
    public FileViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_layout, parent, false);
        return new FileViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHold holder, final int position) {
        View currentView = holder.getItemView();
        ImageView fileIcon = currentView.findViewById(R.id.imageView);
        TextView file_name = currentView.findViewById(R.id.text_file_name);
        TextView file_description = currentView.findViewById(R.id.text_description);
//        fileIcon.setImageResource(itemContains.get(position).getFileIcon());
        file_name.setText(itemContains.get(position).getFileObject().getName());
        file_description.setText("");
        holder.getItemView()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, position);
                    }
                });
        holder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onLongItemClick(v, position);
                return true;
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return itemContains.size();
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onLongItemClick(View view, int position);
    }

    static class FileViewHold extends RecyclerView.ViewHolder {
        private View itemView;

        FileViewHold(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        View getItemView() {
            return itemView;
        }
    }

}