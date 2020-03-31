package com.github.uide.demo;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity 
{
    TextView textChoseFile;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textChoseFile = findViewById(R.id.text_chose_file);
        textChoseFile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FileExplore.class);
            startActivity(intent);
        });
    }
}
