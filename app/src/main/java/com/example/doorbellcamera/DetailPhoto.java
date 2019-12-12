package com.example.doorbellcamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailPhoto extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_photo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.detailPhoto);
        textView = findViewById(R.id.detailTimestamp);

    }
}
