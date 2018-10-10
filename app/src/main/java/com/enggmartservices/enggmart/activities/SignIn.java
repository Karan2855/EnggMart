package com.enggmartservices.enggmart.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgback;
    private EditText usernamelog;
    private EditText usrpasswordlog;
    private Button btnlog;
    private TextView signup, forgotPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Utils.darkenStatusBar(this, R.color.textColorPrimary);
        imgback = (ImageView) findViewById(R.id.imv12);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        btnlog = (Button) findViewById(R.id.buttonlogin);
        progressBar = findViewById(R.id.progress_bar_signin);
        usernamelog = (EditText) findViewById(R.id.editusernamelg);
        usrpasswordlog = (EditText) findViewById(R.id.editpasswordlg);
        signup = (TextView) findViewById(R.id.gosignup);
        forgotPassword = findViewById(R.id.forgot_password);
        btnlog.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    public void userLogin() {
        String email = usernamelog.getText().toString().trim();
        String password = usrpasswordlog.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            usernamelog.setText("");
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password empty
            usrpasswordlog.setText("");
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;

        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().getUser().isEmailVerified()) {
                                Intent mIntent = new Intent(SignIn.this, HomeActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mIntent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this,
                                        "Verification email is sent to "
                                                + task.getResult().getUser().getEmail() +
                                                " at the time of Registration..!  Please Click On that",
                                        Toast.LENGTH_LONG).show();
                                firebaseAuth.signOut();
                            }
                        } else {
                            // Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == btnlog) {
            userLogin();
        } else if (view == signup) {
            startActivity(new Intent(SignIn.this, Signup.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        } else if (view == forgotPassword) {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            builder.setTitle("Are You Sure To Change Password ?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String email = usernamelog.getText().toString().trim();
                            usrpasswordlog.setText("");
                            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                                usernamelog.setError("Enter your registered email id");
                                usernamelog.requestFocus();
                                return;
                            }
                            progressBar.setVisibility(View.VISIBLE);
                            firebaseAuth.sendPasswordResetEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                usernamelog.setText("");
                                                usrpasswordlog.setText("");
                                                Toast.makeText(SignIn.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(SignIn.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                            }

                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignIn.this, SignInSignUp.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
