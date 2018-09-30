package com.example.user.enggmart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private CircleImageView profileImage;
    private String uriupdate;
    private TextView namepro, phonepro, emailpro, detailedpro, update;
    private EditText etnamepro, etphonepro;
    private StorageReference mStorageRef;
    private DatabaseReference mdDatabase;
    private FirebaseAuth userAuth;
    private DatabaseReference mdDatabase2;
    private ProgressBar progressBar;
    private ProfileActivity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle("User Profile");
        findIds();
        progressBar.setVisibility(View.VISIBLE);
        context = ProfileActivity.this;
        userAuth = FirebaseAuth.getInstance();
        String uid = userAuth.getCurrentUser().getUid().toString();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("profileImages").child(uid);
        mdDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid + "");

        mdDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("image").getValue().toString().equals("not Provided")) {
                    Glide.with(getApplicationContext()).load(dataSnapshot.child("image").getValue().toString()).into(profileImage);
                    progressBar.setVisibility(View.GONE);
                }
                namepro.setText(dataSnapshot.child("name").getValue().toString());
                emailpro.setText(dataSnapshot.child("email").getValue().toString());
                phonepro.setText(dataSnapshot.child("phone").getValue().toString());
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void findIds() {
        profileImage = findViewById(R.id.userdpprofile);
        namepro = findViewById(R.id.nameprofiletv);
        phonepro = findViewById(R.id.phoneprofiletv);
        detailedpro = findViewById(R.id.detailedprofile);
        update = findViewById(R.id.updatepro);
        etnamepro = findViewById(R.id.nameprofileet);
        etphonepro = findViewById(R.id.phoneprofileet);
        emailpro = findViewById(R.id.emailprofiletv);
        progressBar = findViewById(R.id.progress_bar_pro_dp);
        profileImage.setOnClickListener(this);
        phonepro.setOnClickListener(this);
        namepro.setOnClickListener(this);
        detailedpro.setOnClickListener(this);
        update.setOnClickListener(this);
        etphonepro.setOnClickListener(this);
        etnamepro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == profileImage) {
            profileImageUploadMethod();
        } else if (view == namepro) {
        } else if (view == phonepro) {
        } else if (view == etnamepro) {
        } else if (view == etphonepro) {
        } else if (view == detailedpro) {
        } else if (view == update) {
        }
    }

    private void profileImageUploadMethod() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Update Display Picture")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openFileChooser();
                    }
                })
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(R.mipmap.photocamera)
                .show();
    }

    private void openFileChooser() {
        Crop.pickImage(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {

            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }

    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            progressBar.setVisibility(View.VISIBLE);
            imageUri = Crop.getOutput(result);
            uploadImage();
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage() {
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference sRef = mStorageRef.child("profile.jpg");

        sRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                uriupdate = uri.toString();
                                mdDatabase.child("image").setValue(uriupdate);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));

    }
}
