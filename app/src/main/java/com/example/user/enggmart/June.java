package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class June extends AppCompatActivity {
    PDFView ca6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_june);

        ca6=(PDFView)findViewById(R.id.ca16);
        ca6.fromAsset("june.pdf").load();
    }
}
