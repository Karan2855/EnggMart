package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class May extends AppCompatActivity {
    PDFView ca5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_may);

        ca5=(PDFView)findViewById(R.id.ca15);
        ca5.fromAsset("may.pdf").load();
    }
}
