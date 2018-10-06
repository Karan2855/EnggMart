package com.example.user.enggmart.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.user.enggmart.R;

public class HelpCenter extends AppCompatActivity {
    private ImageView hlpCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        hlpCancle = (ImageView) findViewById(R.id.canclehelp);
        hlpCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HelpCenter.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}
