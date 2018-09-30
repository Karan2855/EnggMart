package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Godan extends AppCompatActivity {
    PDFView bookje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_godan);

        bookje=(PDFView)findViewById(R.id.godanpdf);
        bookje.fromAsset("selfdet.pdf").load();

    }
}
