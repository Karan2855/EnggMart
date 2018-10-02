package com.example.user.enggmart.wrost_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.user.enggmart.R;

public class CurrentAffairs extends AppCompatActivity {
    LinearLayout jan;
    LinearLayout feb;
    LinearLayout mar;
    LinearLayout apr;
    LinearLayout may;
    LinearLayout jun;
    LinearLayout july;
    LinearLayout aug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_affairs);
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
        setTitle("Current Affairs");


        LinearLayout jan=(LinearLayout)findViewById(R.id.jan);

        jan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CurrentAffairs.this,January.class);
                startActivity(i);
            }
        });

        LinearLayout feb=(LinearLayout)findViewById(R.id.feb);

        feb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CurrentAffairs.this,February.class);
                startActivity(i);
            }
        });

        LinearLayout mar=(LinearLayout)findViewById(R.id.mar);

        mar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CurrentAffairs.this,March.class);
                startActivity(i);
            }
        });
        LinearLayout apr=(LinearLayout)findViewById(R.id.apr);

        apr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CurrentAffairs.this,April.class);
                startActivity(i);
            }
        });
        LinearLayout may=(LinearLayout)findViewById(R.id.may);

        may.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CurrentAffairs.this,May.class);
                startActivity(i);
            }
        });
        LinearLayout jun=(LinearLayout)findViewById(R.id.jun);

        jun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CurrentAffairs.this,June.class);
                startActivity(i);
            }
        });
        LinearLayout july=(LinearLayout)findViewById(R.id.jul);

        july.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CurrentAffairs.this,July.class);
                startActivity(i);
            }
        });
        LinearLayout aug=(LinearLayout)findViewById(R.id.aug);

        aug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CurrentAffairs.this,August.class);
                startActivity(i);
            }
        });
    }

}
