package com.example.doorbellcamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailPhoto extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    public static final String KEY_TIMESTAMP = "TIMESTAMP";
    public static final String KEY_IMAGE = "IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_photo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.detailPhoto);
        textView = findViewById(R.id.detailTimestamp);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String time = b.getString(KEY_TIMESTAMP);
        String image = b.getString(KEY_IMAGE);
        textView.setText(time);
        byte[] decode = Base64.decode(image.getBytes(),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode,0,decode.length);
        imageView.setImageBitmap(bitmap);
    }
}
