package com.example.user.enggmart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SecondActivity extends AppCompatActivity {
    ImageView selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        selectedImage = (ImageView) findViewById(R.id.selectedImage); // init a ImageView
        Intent intent = getIntent(); // get Intent which was set from adapter of Previous Activity
        Glide.with(SecondActivity.this).load(intent.getStringExtra("Image").toString()).into(selectedImage);
    }
}

