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
    //  设置文件管理器打开模式
    public int FILE_EXPLORE = 1;
    public int CHOSE_FILE_ONLY = 0;
    private int defaultStrategy = FILE_EXPLORE;

    //  打开时的目录
    private File parent;

    //  文件操作类
    private FileAdapterUtils fileAdapterUtils = new FileAdapterUtils();

    //  排序方法
    private int sortStrategy = FileAdapterUtils.SORT_BY_SIZE;
    private int orderStrategy = FileAdapterUtils.ORDER_ASCENDING;


    //  显示在屏幕上的列表
    private List<FileAdapterUtils.ItemContain> itemContains;
    //  处理点击事件接口
    private OnItemClickListener onItemClickListener;

    public FileExploreAdapter(File file) {
        this.parent = file;
        init();
    }

    public FileExploreAdapter(String path) {
        this.parent = new File(path);
        init();
    }

    //初始化列表
    private void init() {
        itemContains = fileAdapterUtils.initItemArray(parent, defaultStrategy, orderStrategy);
    }

    public void refresh(File file) {
        itemContains = fileAdapterUtils.initItemArray(file, defaultStrategy, orderStrategy);
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FileViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_layout, parent, false);
        return new FileViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHold holder, final int position) {
        View currentView = holder.getItemView();

        ImageView fileIcon = currentView.findViewById(R.id.imageView);
        TextView file_name = currentView.findViewById(R.id.text_file_name);
        TextView file_description = currentView.findViewById(R.id.text_description);
        fileIcon.setImageResource(itemContains.get(position).getFileIcon());
        file_name.setText(itemContains.get(position).getFileObject().getName());
        file_description.setText(itemContains.get(position).getDescription());
        holder.getItemView()
                .setOnClickListener(v -> onItemClickListener.onItemClick(v, position, itemContains.get(position).getFileObject()));
        holder.getItemView().setOnLongClickListener(v -> {
            onItemClickListener.onLongItemClick(v, position, itemContains.get(position).getFileObject());
            return true;
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return itemContains.size();
    }

    public void setDefaultStrategy(int defaultStrategy) {
        this.defaultStrategy = defaultStrategy;
    }

    public interface OnItemClickListener {
        /**
         * @param view     item
         * @param position 项
         * @param file     所选文件
         */
        void onItemClick(View view, int position, File file);

        /**
         * @param view     item
         * @param position 项
         * @param file     所选文件
         */
        void onLongItemClick(View view, int position, File file);
    }

    public int getSortStrategy() {
        return sortStrategy;
    }

    public int getDefaultStrategy() {
        return defaultStrategy;
    }

    public void setSortStrategy(int sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public int getOrderStrategy() {
        return orderStrategy;
    }

    public void setOrderStrategy(int orderStrategy) {
        this.orderStrategy = orderStrategy;
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
