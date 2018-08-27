package com.example.user.enggmart;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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

public class Signup extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private static final int RC_SIGN_IN = 111;
    private ImageView Imv;
    private EditText email, password, uname, phone;
    private Uri imageUri;
    private Button btnregister, btnverify;
    private TextView login;
    private ProgressDialog progressDialog;
    private String name = "", pass = "", emailid = "", phoneno = "", uid = "";
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private StorageReference mStorageRef;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnregister = findViewById(R.id.buttonregister);
        btnverify = findViewById(R.id.buttonverify);
        email = findViewById(R.id.emailsignup);
        uname = findViewById(R.id.namesignup);
        password = findViewById(R.id.passwordsignup);
        login = findViewById(R.id.logintvsignup);
        phone = findViewById(R.id.mobnosignup);
        Imv = findViewById(R.id.imv);
        btnregister.setOnClickListener(this);
        login.setOnClickListener(this);
        btnverify.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();

        imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.mipmap.usera)
                + '/' + getResources().getResourceTypeName(R.mipmap.usera) + '/' + getResources().getResourceEntryName(R.mipmap.usera));
        progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        Imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Signup.this, SignInSignUp.class);
                startActivity(i);
                //              overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });


        //google login
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    private void updateInformation(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            uid = user.getUid().toString();
            mStorageRef = FirebaseStorage.getInstance().getReference().child("profileImages").child(uid);
            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            progressDialog.setMessage("Updating Informations");
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
                                    updateDatabase();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.setMessage("Failed");
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    private void updateDatabase() {
        ModelRegister modelRegister = new ModelRegister();
        modelRegister.setEmail("" + emailid);
        modelRegister.setName("" + name);
        modelRegister.setPhone("+91 " + phoneno);
        modelRegister.setPassword("" + pass);
        modelRegister.setImage(imageUri.toString());
        mDatabase.setValue(modelRegister);
        completeTask();
    }

    private void completeTask() {
        progressDialog.dismiss();
        Toast.makeText(this, "Registration Successfull..!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Signup.this, SignIn.class));
        auth.signOut();
        finish();
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("Tag", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fUser = task.getResult().getUser();
                            UpdateUI(fUser);
                        } else {
                            Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    private void UpdateUI(final FirebaseUser fUser) {
                        progressDialog.setMessage("Updating User Informations...");
                        String fuid = fUser.getUid().toString();
                        mStorageRef = FirebaseStorage.getInstance().getReference()
                                .child("profileImages").child(fuid);
                        mDatabase = FirebaseDatabase.getInstance()
                                .getReference().child("users").child(fuid);
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                ModelRegister modelRegister = new ModelRegister();
                                modelRegister.setEmail("" + fUser.getEmail());
                                modelRegister.setName("" + fUser.getDisplayName());
                                modelRegister.setPhone("");
                                modelRegister.setPassword("");
                                if (dataSnapshot.child("image").getValue().equals(null)) {
                                    modelRegister.setImage(imageUri.toString());
                                } else
                                    modelRegister.setImage(dataSnapshot.child("image").getValue().toString());
                                mDatabase.setValue(modelRegister);
                                startActivity(new Intent(Signup.this, HomeActivity.class));
                                finish();
                                progressDialog.dismiss();
                                return;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(Signup.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.ll), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            progressDialog.setMessage("Registering user Using Gmail...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnregister) {
            registerUser();
        } else if (view == login) {
            startActivity(new Intent(Signup.this, SignIn.class));
            // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        } else if (view == btnverify) {

        }
    }

    public void registerUser() {
        if (userValidte()) {
            progressDialog.setMessage("Registering user...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            auth.createUserWithEmailAndPassword(emailid, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                user = task.getResult().getUser();
                                user.sendEmailVerification().addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Signup.this,
                                                        "Verification email sent to " + user.getEmail(),
                                                        Toast.LENGTH_SHORT).show();
                                                updateInformation(user);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Signup.this, e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                        user.delete();

                                        Toast.makeText(Signup.this,
                                                "Registration is not Successful!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else

                            {
                                progressDialog.dismiss();
                                Toast.makeText(Signup.this, "Registeration Failed...!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
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
        if (Pattern.matches("[0-9]+", phone.getText().toString().trim()) && phoneno.length() == 10)
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Signup.this, SignInSignUp.class));
        overridePendingTransition(R.anim.fade_out, R.anim.slide_out_down);
        finish();
    }
}
