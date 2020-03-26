package com.github.uide.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.github.uide.demo.adapter.FileExploreAdapter;

public class FileExplore extends AppCompatActivity {
    RecyclerView recyclerView;
    FileExploreAdapter fileExploreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explore);
        recyclerView = findViewById(R.id.recycle_file);
    }
}
