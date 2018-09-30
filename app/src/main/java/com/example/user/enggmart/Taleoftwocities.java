package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Taleoftwocities extends AppCompatActivity {
    PDFView bookte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taleoftwocities);

        bookte=(PDFView)findViewById(R.id.totcpdf);
        bookte.fromAsset("eng.pdf").load();

    }
}
