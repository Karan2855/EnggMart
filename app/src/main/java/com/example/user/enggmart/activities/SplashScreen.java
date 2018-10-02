package com.example.user.enggmart.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.user.enggmart.R;
import com.felipecsl.gifimageview.library.GifImageView;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;
    private GifImageView gifImageView;
    private LinearLayout animat;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_in_logo);
        a.reset();
        animat = (LinearLayout) findViewById(R.id.anim);
        animat.clearAnimation();
        animat.startAnimation(a);

        userAuth = FirebaseAuth.getInstance();
        //  gifImageView = (GifImageView) findViewById(R.id.gifImageView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//Do what you need for this SD
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
                Intent i = new Intent(SplashScreen.this, SignInSignUp.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}