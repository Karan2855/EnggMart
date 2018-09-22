package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Seveneges extends AppCompatActivity {
PDFView bookse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seveneges);

        bookse=(PDFView)findViewById(R.id.sevenegespdf);
        bookse.fromAsset("seveneges.pdf").load();


    }
}
