package com.google.mediapipe.examples.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String title = getIntent().getStringExtra("title");
        int imageRes = getIntent().getIntExtra("imageRes", R.drawable.unknown);
        String description = getIntent().getStringExtra("description");

        TextView titleView = findViewById(R.id.title);
        ImageView imageView = findViewById(R.id.imageView);
        TextView descView = findViewById(R.id.description);

        titleView.setText(title);
        imageView.setImageResource(imageRes);
        descView.setText(description);
    }
}