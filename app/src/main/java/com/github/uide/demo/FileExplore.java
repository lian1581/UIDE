package com.github.uide.demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.uide.demo.adapter.FileExploreAdapter;

import java.io.File;

public class FileExplore extends AppCompatActivity {
    private TextView emptyFolderNotice;
    private RecyclerView recyclerView;
    private FileExploreAdapter fileExploreAdapter;
    private File file = Environment.getExternalStorageDirectory();
    private File currentParentFolder = file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explore);
        recyclerView = findViewById(R.id.recycle_file);
        emptyFolderNotice = findViewById(R.id.text_empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(this
                , LinearLayoutManager.VERTICAL, false));
        fileExploreAdapter = new FileExploreAdapter(file);
        recyclerView.setAdapter(fileExploreAdapter);
        Toast.makeText(FileExplore.this, file.getName(), Toast.LENGTH_LONG).show();
        fileExploreAdapter.setOnItemClickListener(new FileExploreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, File file) {
//                判断当前文件管理器打开方式，选择文件或管理文件
                if (fileExploreAdapter.getDefaultStrategy() == fileExploreAdapter.CHOSE_FILE_ONLY) {
                    if (file.isFile()) {
                        new AlertDialog.Builder(FileExplore.this)
                                .setTitle("确定？")
                                .setMessage("你确定要选择这个文件吗？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        TODO 完成跳转
                                    }
                                }).setNegativeButton("否", null)
                                .show();
                        Intent intent = new Intent();
                    } else {
                        if (checkEmptyFolder(file)) {
                            recyclerView.setVisibility(View.INVISIBLE);
                            emptyFolderNotice.setVisibility(View.VISIBLE);
                        }
                        fileExploreAdapter.refresh(file);
                    }
                } else {
                    if (file.isFile()) {
//                        打开方式
                        new AlertDialog.Builder(FileExplore.this)
                                .show();
                        Intent intent = new Intent();
                    } else {
                        if (checkEmptyFolder(file)) {
                            recyclerView.setVisibility(View.INVISIBLE);
                            emptyFolderNotice.setVisibility(View.VISIBLE);
                        }
                        fileExploreAdapter.refresh(file);
                    }
                }
            }

            @Override
            public void onLongItemClick(View view, int position, File file) {
//TODO 完成长按弹出选项
            }
        });
    }

    private boolean checkEmptyFolder() {
        return !(currentParentFolder.listFiles() == null | currentParentFolder.listFiles().length == 0);
    }

    /**
     * @param f 想要检查的文件夹
     * @return 是：空文件夹
     */
    private boolean checkEmptyFolder(File f) {
        this.currentParentFolder = f;
        return checkEmptyFolder();
    }
}
