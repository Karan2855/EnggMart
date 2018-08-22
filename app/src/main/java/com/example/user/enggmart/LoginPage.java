package com.example.user.enggmart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgback;

    private EditText usernamelog;
    private EditText usrpasswordlog;
    private Button btnlog;
    private TextView signup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        imgback = (ImageView) findViewById(R.id.imv12);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(new Intent(LoginPage.this, Login.class));
                finish();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        btnlog = (Button) findViewById(R.id.buttonlogin);
        usernamelog = (EditText) findViewById(R.id.editusernamelg);
        usrpasswordlog = (EditText) findViewById(R.id.editpasswordlg);
        signup = (TextView) findViewById(R.id.goSignup);
        btnlog.setOnClickListener(this);
        signup.setOnClickListener(this);
        progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);

    }

    public void userLogin() {
        String email = usernamelog.getText().toString().trim();
        String password = usrpasswordlog.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email empty
            usernamelog.setText("");
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password empty
            usrpasswordlog.setText("");
            usernamelog.setError("");
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;

        }
        progressDialog.setMessage("Login user...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //start profile activity
                            startActivity(new Intent(LoginPage.this, HomeActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.slide_out_down);
                            finish();
                        } else {
                            mthd();
                        }

                    }
                });


    }

    private void mthd() {

        Toast.makeText(this, "incorrect user id or password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == btnlog) {
            userLogin();
        }
        if (view == signup) {
            //log in activty
            startActivity(new Intent(LoginPage.this, Signup.class));
            overridePendingTransition(R.anim.fade_out, R.anim.slide_out_down);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginPage.this, Login.class));
        overridePendingTransition(R.anim.fade_out, R.anim.slide_out_down);
        finish();
    }
}
