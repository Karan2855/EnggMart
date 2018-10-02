package com.example.user.enggmart.wrost_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.enggmart.R;
import com.github.barteksc.pdfviewer.PDFView;

public class Pdf1 extends AppCompatActivity {
    PDFView book22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf1);

        book22=(PDFView)findViewById(R.id.pdfpdf1);
        book22.fromAsset("pdf1.pdf").load();
    }
}
