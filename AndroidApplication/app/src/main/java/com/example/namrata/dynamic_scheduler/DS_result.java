package com.example.namrata.dynamic_scheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DS_result extends AppCompatActivity {
    TextView mTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_result);
        mTextview = findViewById(R.id.DSResultView);
        mTextview.setText(getIntent().getStringExtra("mytext"));
    }
}
