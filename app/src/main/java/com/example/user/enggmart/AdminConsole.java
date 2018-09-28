package com.example.user.enggmart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class AdminConsole extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST_ONE = 1;
    private static final int PICK_IMAGE_REQUEST_TWO = 2;
    private static final int PICK_IMAGE_REQUEST_THREE = 3;
    private static final int PICK_IMAGE_REQUEST_ONE_C = 11;
    private static final int PICK_IMAGE_REQUEST_TWO_C = 22;
    private static final int PICK_IMAGE_REQUEST_THREE_C = 33;
    private static final int MEDIA_TYPE_IMAGE = 100;
    private ImageView mimage1, mimage2, mimage3;
    private EditText mItemName, mItemPrice, mItemDescription;
    private Button submitItem;
    private AlertDialog.Builder builder;
    private Uri fileUriImage1, fileUriImage2, fileUriImage3;
    private StorageReference mStorageRef;
    private DatabaseReference mdDatabase;
    private FirebaseAuth userAuth;
    private DatabaseReference mdDatabase2;
    private ProgressDialog progressDialog;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_console);
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
        setTitle("Admin Console");
        context = AdminConsole.this;
        mStorageRef = FirebaseStorage.getInstance().getReference().child("storeImages");
        mdDatabase = FirebaseDatabase.getInstance().getReference().child("storeDetails");

        mimage1 = findViewById(R.id.image1);
        mimage2 = findViewById(R.id.image2);
        mimage3 = findViewById(R.id.image3);
        mItemName = findViewById(R.id.name_item_to_server);
        mItemPrice = findViewById(R.id.price_item_to_server);
        mItemDescription = findViewById(R.id.description_item_to_server);
        submitItem = findViewById(R.id.submit_item_to_server);
        mimage1.setOnClickListener(this);
        mimage2.setOnClickListener(this);
        mimage3.setOnClickListener(this);
        submitItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mimage1) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Image for Item").setMessage("Taking Image from")
                    .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            /* *//* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_REQUEST_ONE_C);*//*
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUriImage1);

                            // start the image capture Intent
                            startActivityForResult(intent, PICK_IMAGE_REQUEST_ONE_C);*/
                            Toast.makeText(AdminConsole.this, "Willbe Add Soon", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, PICK_IMAGE_REQUEST_ONE);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (v == mimage2) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Image for Item")
                    .setMessage("Taking Image from")
                    .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
/*
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_REQUEST_TWO_C);
*/
                            Toast.makeText(AdminConsole.this, "Willbe Add Soon", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, PICK_IMAGE_REQUEST_TWO);
                        }
                    })
                    .setIcon(android.R.drawable.ic_menu_camera)
                    .show();
        } else if (v == mimage3) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Image for Item")
                    .setMessage("Taking Image from")
                    .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                          /*  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_REQUEST_THREE_C);
                  */
                            Toast.makeText(AdminConsole.this, "Willbe Add Soon", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, PICK_IMAGE_REQUEST_THREE);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (v == submitItem) {
            String iName = mItemName.getText().toString().trim();
            String iPrice = mItemPrice.getText().toString().trim();
            String iDescription = mItemDescription.getText().toString().trim();

            if (fileUriImage1 == null || fileUriImage2 == null || fileUriImage3 == null) {
                Toast.makeText(AdminConsole.this, "Please select Three Images Of Product", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(iName)) {
                mItemName.setError("please Enter Item name");
            } else if (TextUtils.isEmpty(iPrice) || !(Pattern.matches("[0-9]+", iPrice))) {
                mItemPrice.setError("please enter Valid price");
            } else if (TextUtils.isEmpty(iDescription)) {
                mItemDescription.setError("Please Enter Some Discription of item");
            } else
                uploadImage(iName, iPrice, iDescription);
        }
    }

    private void uploadImage(final String iName, final String iPrice, final String iDescription) {
        progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        progressDialog.setMessage("uploading...");
        progressDialog.show();

        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        final StorageReference sRef1 = mStorageRef.child(timeStamp).child("image1.jpg");
        final StorageReference sRef2 = mStorageRef.child(timeStamp).child("image2.jpg");
        final StorageReference sRef3 = mStorageRef.child(timeStamp).child("image3.jpg");

        sRef1.putFile(fileUriImage1)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        sRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fileUriImage1 = uri;
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });

        sRef2.putFile(fileUriImage2)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        sRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fileUriImage2 = uri;
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });

        sRef3.putFile(fileUriImage3)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        sRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fileUriImage3 = uri;
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("itemName", iName + " ");
                                map.put("itemPrice", iPrice + ".00");
                                map.put("itemDescription", iDescription + " ");
                                map.put("image1", fileUriImage1.toString());
                                map.put("image2", fileUriImage2.toString());
                                map.put("image3", fileUriImage3.toString());
                                map.put("storagefolderName", timeStamp);
                                mdDatabase.push().setValue(map);
                            }
                        });
                        Toast.makeText(context, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        mItemName.setText("");
                        mItemPrice.setText("");
                        mItemDescription.setText("");
                        mimage1.setImageResource(R.drawable.ic_menu_camera);
                        mimage2.setImageResource(R.drawable.ic_menu_camera);
                        mimage3.setImageResource(R.drawable.ic_menu_camera);
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

    private static File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("No", "Oops! Failed create "
                        + Constants.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_ONE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUriImage1 = data.getData();
            mimage1.setImageURI(fileUriImage1);
        } else if (requestCode == PICK_IMAGE_REQUEST_TWO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUriImage2 = data.getData();
            mimage2.setImageURI(fileUriImage2);
        } else if (requestCode == PICK_IMAGE_REQUEST_THREE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUriImage3 = data.getData();
            mimage3.setImageURI(fileUriImage3);
        } /*else if (requestCode == PICK_IMAGE_REQUEST_ONE_C && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mimage1.setImageBitmap(bitmap);
        } else if (requestCode == PICK_IMAGE_REQUEST_TWO_C && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mimage2.setImageBitmap(bitmap);
        } else if (requestCode == PICK_IMAGE_REQUEST_THREE_C && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mimage3.setImageBitmap(bitmap);
        } else
        if (requestCode == PICK_IMAGE_REQUEST_ONE_C) {
            if (resultCode == RESULT_OK) {
                fileUriImage1 = Uri.fromFile(getOutputMediaFile());
                mimage1.setImageURI(fileUriImage1);

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("fileUriImage1", fileUriImage1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUriImage1 = savedInstanceState.getParcelable("fileUriImage1");
    }

}
