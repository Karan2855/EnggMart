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
    private ImageView profileImage;
    private String uri;
    private TextView namepro, phonepro, emailpro, detailedpro, update;
    private EditText etnamepro, etphonepro;
    private StorageReference mStorageRef;
    private DatabaseReference mdDatabase;
    private FirebaseAuth userAuth;
    private DatabaseReference mdDatabase2;
    private ProgressDialog progressDialog;
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
        context = ProfileActivity.this;

        userAuth = FirebaseAuth.getInstance();
        String uid = userAuth.getCurrentUser().getUid().toString();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("profileImages").child(uid);
        mdDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid + "");
        mdDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.child("image").getValue().toString()).into(profileImage);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Update Display Picture")
                .setMessage("Are you sure you want to Change Display Picture?")
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
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void openFileChooser() {
        profileImage.setImageURI(imageUri);
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
            imageUri = Crop.getOutput(result);
            profileImage.setImageURI(imageUri);
            uploadImage();
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage() {
        progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        progressDialog.setMessage("uploading...");
        progressDialog.show();
        final StorageReference sRef = mStorageRef.child("profile.jpg");

        sRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                mdDatabase.child("image").setValue(uri.toString());
                            }
                        });
                        Toast.makeText(context, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
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
