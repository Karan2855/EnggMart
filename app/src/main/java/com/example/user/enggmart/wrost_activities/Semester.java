package com.example.user.enggmart.wrost_activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.user.enggmart.R;

public class Semester extends AppCompatActivity {
    LinearLayout b11;
    LinearLayout b12;
    LinearLayout b13;
    LinearLayout b14;
    LinearLayout b15;
    LinearLayout b16;
    LinearLayout b17;
    LinearLayout b18;
    LinearLayout b19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newspaper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle("Newspaper");


        LinearLayout b11=(LinearLayout)findViewById(R.id.first);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.timesofindia.indiatimes.com/"));
                startActivity(i);

            }
        });

        LinearLayout b12=(LinearLayout)findViewById(R.id.second);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hindustantimes.com/"));
                startActivity(i);

            }
        });
        LinearLayout b13=(LinearLayout)findViewById(R.id.third);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thehindu.com/"));
                startActivity(i);

            }
        });
        LinearLayout b14=(LinearLayout)findViewById(R.id.fourth);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bbc.com/"));
                startActivity(i);

            }
        });
        LinearLayout b15=(LinearLayout)findViewById(R.id.fifth);
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.livehindustan.com/"));
                startActivity(i);

            }
        });
        LinearLayout b16=(LinearLayout)findViewById(R.id.sixth);
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jagran.com/"));
                startActivity(i);

            }
        });
        LinearLayout b17=(LinearLayout)findViewById(R.id.seventh);
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bhaskar.com/"));
                startActivity(i);

            }
        });
        LinearLayout b18=(LinearLayout)findViewById(R.id.eight);
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.patrika.com/"));
                startActivity(i);

            }
        });
        LinearLayout b19=(LinearLayout)findViewById(R.id.ninth);
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.indianexpress.com/"));
                startActivity(i);

            }
        });




    }

}
