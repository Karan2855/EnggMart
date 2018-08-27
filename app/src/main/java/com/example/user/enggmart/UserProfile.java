package com.example.user.enggmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private TextView Emailusr;
    private Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //profileactivity
            finish();
            startActivity(new Intent(getApplicationContext(),SignInSignUp.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Emailusr=(TextView)findViewById(R.id.useremail);
        Emailusr.setText("Welcome--"+user.getEmail());
        Logout=(Button)findViewById(R.id.logoutup);
        Logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == Logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,SignInSignUp.class));
        }
    }
}
