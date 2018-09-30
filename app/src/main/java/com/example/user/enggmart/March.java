package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class March extends AppCompatActivity {
    PDFView ca3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_march);

        ca3=(PDFView)findViewById(R.id.ca13);
        ca3.fromAsset("march.pdf").load();
    }
}
