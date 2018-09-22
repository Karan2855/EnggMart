package com.example.user.enggmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Journalism extends AppCompatActivity {
    PDFView bookje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journalism);

        bookje=(PDFView)findViewById(R.id.journalismpdf);
        bookje.fromAsset("journalism and the novel.pdf").load();

    }
}
