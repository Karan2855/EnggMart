package com.example.user.enggmart;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    private ImageView Imv;
    private EditText email;
    private EditText password;
    private EditText uname;
    private Uri imageUri;
    private EditText phone;
    private Button btnregister;
    private String uid, uri;
    private TextView login;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String name, pass, emailid, phoneno;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        btnregister = (Button) findViewById(R.id.buttonregister);
        email = (EditText) findViewById(R.id.emailsignup);
        btnregister = (Button) findViewById(R.id.buttonregister);
        uname = (EditText) findViewById(R.id.namesignup);
        password = (EditText) findViewById(R.id.passwordsignup);
        login = (TextView) findViewById(R.id.logintvsignup);
        phone = findViewById(R.id.mobnosignup);
        Imv = findViewById(R.id.imv);
        btnregister.setOnClickListener(this);
        login.setOnClickListener(this);
        imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.mipmap.usera)
                + '/' + getResources().getResourceTypeName(R.mipmap.usera) + '/' + getResources().getResourceEntryName(R.mipmap.usera));
        progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        Imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Signup.this, Login.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == btnregister) {
            registerUser();
        }
        if (view == login) {

            startActivity(new Intent(Signup.this, LoginPage.class));
            finish();
            //log in activty
        }
    }

    public void registerUser() {
        boolean yes = userValidte();
        if (yes) {
            progressDialog.setMessage("Registering user...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            firebaseAuth.createUserWithEmailAndPassword(emailid, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                user = firebaseAuth.getCurrentUser();
                                uid = user.getUid().toString();
                                mStorageRef = FirebaseStorage.getInstance().getReference().child("profileImages").child(uid);
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                                addDirectory();
                                Toast.makeText(Signup.this, "Register sucessfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Signup.this, LoginPage.class));
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Signup.this, "Error in Registration!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }
    }

    private void addDirectory() {
        final StorageReference sRef = mStorageRef.child("profile.jpg");

        sRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content

                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageUri = uri;
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ModelRegister modelRegister = new ModelRegister();
                modelRegister.setEmail("" + emailid);
                modelRegister.setName("" + name);
                modelRegister.setPhone("" + phoneno);
                modelRegister.setPassword("" + pass);
                modelRegister.setImage(imageUri.toString());
                mDatabase.setValue(modelRegister);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private boolean userValidte() {
        name = uname.getText().toString().trim();
        pass = password.getText().toString().trim();
        emailid = email.getText().toString().trim();
        phoneno = phone.getText().toString().trim();
        if (name.equals("")) {
            uname.setError("can't be blank");
        } else if (!name.matches("[A-Za-z]+")) {
            uname.setError("only alphabet allowed");
        } else if (name.length() < 5) {
            uname.setError("at least 5 characters long");
        } else if (emailid.equals("")) {
            email.setError("can't be blank");
        } else if (!checkEmail()) {
            email.setError("enter valid email");
        } else if (!checkPhone()) {
            phone.setError("enter valid phone no.");
        } else if (pass.length() < 5) {
            password.setError("at least 5 characters long");
        } else
            return true;
        return false;
    }

    private boolean checkPhone() {
        if (Pattern.matches("[0-9]+", phone.getText().toString().trim()) && phone.length() == 10)
            return true;
        return false;
    }

    private boolean checkEmail() {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(emailid);
        if (matcher.matches())
            return true;
        return false;
    }

}
