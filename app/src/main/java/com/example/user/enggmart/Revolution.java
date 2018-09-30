package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Revolution extends AppCompatActivity {
    PDFView bookre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revolution);

        bookre=(PDFView)findViewById(R.id.revolutionpdff);
        bookre.fromAsset("success.pdf").load();

    }
}
