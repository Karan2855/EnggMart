package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class July extends AppCompatActivity {
    PDFView ca7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_july);

        ca7=(PDFView)findViewById(R.id.ca17);
        ca7.fromAsset("july.pdf").load();
    }
}
