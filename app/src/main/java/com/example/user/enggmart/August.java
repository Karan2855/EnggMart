package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class August extends AppCompatActivity {
    PDFView ca8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_august);

        ca8=(PDFView)findViewById(R.id.ca18);
        ca8.fromAsset("august.pdf").load();
    }
}
