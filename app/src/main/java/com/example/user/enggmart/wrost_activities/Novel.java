package com.example.user.enggmart.wrost_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.user.enggmart.R;


public class Novel extends AppCompatActivity {
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);

        ImageView imageView = (ImageView) findViewById(R.id.seveneges);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Novel.this, Seveneges.class);
                startActivity(i);
            }
        });

        ImageView imageView2 = (ImageView) findViewById(R.id.theaops);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Novel.this, Artofps.class);
                startActivity(i);
            }
        });

        ImageView imageView3 = (ImageView) findViewById(R.id.journalism);

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Novel.this, Journalism.class);
                startActivity(i);
            }
        });

        ImageView imageView4 = (ImageView) findViewById(R.id.ataleoftwo);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Novel.this, Taleoftwocities.class);
                startActivity(i);
            }
        });


    }
}
