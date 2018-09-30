package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class February extends AppCompatActivity {
    PDFView ca2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_february);

        ca2=(PDFView)findViewById(R.id.ca22);
        ca2.fromAsset("feb.pdf").load();
    }
}
