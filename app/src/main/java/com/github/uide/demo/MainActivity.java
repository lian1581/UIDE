package com.github.uide.demo;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.view.View;
import android.widget.TextView;

import com.github.uide.demo.adapter.FileExploreAdapter;
import com.github.uide.demo.view.CodeEditor;


public class MainActivity extends Activity {
    TextView textChoseFile;
    CodeEditor codeEditor;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textChoseFile = findViewById(R.id.text_chose_file);
        codeEditor = findViewById(R.id.code_editor);
        textChoseFile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FileExplore.class);
            intent.putExtra("ExploreMode", FileExploreAdapter.CHOSE_FILE_ONLY);
            startActivity(intent);
        });
        intent.getStringExtra("path");
        textChoseFile.setVisibility(View.INVISIBLE);
        codeEditor.setVisibility(View.VISIBLE);
    }

}
