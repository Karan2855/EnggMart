package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Artofps extends AppCompatActivity {
    PDFView bookps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artofps);
        bookps=(PDFView)findViewById(R.id.artofpsbookpdf);
        bookps.fromAsset("arofpuspeaking.pdf").load();

    }
}
