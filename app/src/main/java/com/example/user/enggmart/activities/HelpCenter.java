package com.example.user.enggmart.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.enggmart.R;
import com.example.user.enggmart.utility.Utils;

public class HelpCenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        Utils.darkenStatusBar(this, R.color.textColorPrimary);
    }
}
