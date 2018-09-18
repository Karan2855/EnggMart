package com.example.user.enggmart;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.felipecsl.gifimageview.library.GifImageView;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;
    private GifImageView gifImageView;
    private FirebaseAuth userAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        userAuth = FirebaseAuth.getInstance();
      //  gifImageView = (GifImageView) findViewById(R.id.gifImageView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//Do what you need for this SDK
        }
    //    try {
         //   InputStream inputStream = getAssets().open("loading.gif");
          //  byte[] bytes = IOUtils.toByteArray(inputStream);
         //   gifImageView.setBytes(bytes);
         //   gifImageView.startAnimation();
       // } catch (IOException e) {

        //}
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userAuth.getCurrentUser() != null) {
                    startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                    overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                    finish();
                } else {
                    Intent i = new Intent(SplashScreen.this, SignInSignUp.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
