package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Book1 extends AppCompatActivity {
    PDFView book11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book1);

        book11=(PDFView)findViewById(R.id.pdfbook1);
        book11.fromAsset("Book1.pdf").load();
    }
}
