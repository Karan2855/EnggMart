package com.example.user.enggmart.wrost_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.enggmart.R;
import com.github.barteksc.pdfviewer.PDFView;

public class Billgates extends AppCompatActivity {
    PDFView bookje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billgates);

        bookje=(PDFView)findViewById(R.id.billgatespdf);
        bookje.fromAsset("bill_gates_print_-_biography1.pdf").load();

    }
}
