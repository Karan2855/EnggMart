package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class January extends AppCompatActivity {
    PDFView ca1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_january);

        ca1=(PDFView)findViewById(R.id.ca11);
        ca1.fromAsset("jan.pdf").load();
    }
}
