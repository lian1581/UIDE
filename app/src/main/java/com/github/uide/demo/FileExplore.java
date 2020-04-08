package com.github.uide.demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.uide.demo.adapter.FileExploreAdapter;

import java.io.File;
import java.util.Objects;

public class FileExplore extends AppCompatActivity {
    private TextView emptyFolderNotice;
    private RecyclerView recyclerView;
    private FileExploreAdapter fileExploreAdapter;
    private File file = Environment.getExternalStorageDirectory();
    private File currentParentFolder = file;
    private Intent intent;

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
        intent = getIntent();
        fileExploreAdapter.setDefaultStrategy(intent.getIntExtra("ExploreMode", FileExploreAdapter.FILE_EXPLORE));
        Toast.makeText(FileExplore.this, file.getName(), Toast.LENGTH_LONG).show();
        fileExploreAdapter.setOnItemClickListener(new FileExploreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, File file) {
//                判断当前文件管理器打开方式，选择文件或管理文件
                if (fileExploreAdapter.getDefaultStrategy() == FileExploreAdapter.CHOSE_FILE_ONLY) {
                    if (file.isFile()) {
                        new AlertDialog.Builder(FileExplore.this)
                                .setTitle("确定？")
                                .setMessage("你确定要选择这个文件吗？")
                                .setPositiveButton("是", (dialog, which) -> {
//                                        TODO 完成跳转
                                }).setNegativeButton("否", null)
                                .show();
                        Intent intent = new Intent(FileExplore.this, MainActivity.class);
                        intent.putExtra("path", file.getAbsolutePath());
                        startActivity(intent);
                    } else {
                        checkEmptyFolder(file);
                        fileExploreAdapter.refresh(file);
                    }
                } else {
                    if (file.isFile()) {
//                        打开方式
                        new AlertDialog.Builder(FileExplore.this)
                                .show();
                        Intent intent = new Intent();
                    } else {
                        checkEmptyFolder(file);
                        fileExploreAdapter.refresh(file);
                    }
                }
            }

            @Override
            public void onLongItemClick(View view, int position, File file) {
                final String[] operations = new String[]{"复制", "删除", "属性"};
                AlertDialog alertDialog = new AlertDialog.Builder(FileExplore.this)
//                        DialogInterface dialog, int which
//                        TODO 完善删除等操作
                        .setItems(operations, (dialog, which) -> {
                            switch (which) {
                                case 0:
                                    dialog.dismiss();
                                case 1:
                                    dialog.dismiss();
                                case 2:
                                    dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.file_explore_setting, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (currentParentFolder.getAbsolutePath().compareTo(file.getAbsolutePath()) == 0) {
            finish();
        }
        fileExploreAdapter.refresh(currentParentFolder.getParentFile());
        checkEmptyFolder(currentParentFolder.getParentFile());
    }

    private void checkEmptyFolder() {
        if (Objects.requireNonNull(currentParentFolder.listFiles()).length == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyFolderNotice.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyFolderNotice.setVisibility(View.INVISIBLE);
        }
        setTitle(currentParentFolder.getName());
    }

    /**
     * @param f 想要检查的文件夹
     */
    private void checkEmptyFolder(File f) {
        this.currentParentFolder = f;
        checkEmptyFolder();
    }
}
