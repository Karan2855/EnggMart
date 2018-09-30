package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class April extends AppCompatActivity {
    PDFView ca4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_april);

        ca4=(PDFView)findViewById(R.id.ca14);
        ca4.fromAsset("apr.pdf").load();
    }
}
