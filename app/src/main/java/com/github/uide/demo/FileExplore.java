package com.github.uide.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.github.uide.demo.adapter.FileExploreAdapter;

import java.io.File;

public class FileExplore extends AppCompatActivity {
    RecyclerView recyclerView;
    FileExploreAdapter fileExploreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explore);
        recyclerView = findViewById(R.id.recycle_file);
        recyclerView.setLayoutManager(new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(fileExploreAdapter);
        fileExploreAdapter.setOnItemClickListener(new FileExploreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, File file) {
//                判断当前文件管理器打开方式，选择文件或管理文件
                if (fileExploreAdapter.getDefaultStrategy() == fileExploreAdapter.CHOSE_FILE_ONLY) {

                    if (file.isFile()) {
//                       TODO 完成跳转
                    }
                }
            }

            @Override
            public void onLongItemClick(View view, int position, File file) {
//TODO 完成长按弹出选项
            }
        });
    }
}
