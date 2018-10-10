package com.enggmartservices.enggmart.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.enggmartservices.enggmart.R;

public class HelpCenter extends AppCompatActivity {
    private ImageView hlpCancle;
    private LinearLayout email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        hlpCancle = (ImageView) findViewById(R.id.canclehelp);
        hlpCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        email = findViewById(R.id.send_email_help);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"enggmart2018@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(HelpCenter.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        phone = findViewById(R.id.call_help);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "7734031944", null)));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
