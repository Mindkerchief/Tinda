package com.lspuspcc.tinda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        boolean startTyping = getIntent().getBooleanExtra("isTyping", false);
        SearchView searchVSearchField = findViewById(R.id.searchV_searchField);

        if (startTyping)
            searchVSearchField.setIconified(false);

        searchVSearchField.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                searchVSearchField.setIconified(false);
            }
            return false;
        });
    }
}