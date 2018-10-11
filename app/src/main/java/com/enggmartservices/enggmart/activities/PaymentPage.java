package com.enggmartservices.enggmart.activities;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class PaymentPage extends AppCompatActivity {


    private String itemID;
    private String itemType, itemIsa;
    private String price;
    private String itemNameString, itemImageString;
    private DatabaseReference mDatabase;
    private TextView itemName, itemPrice, itemCondition, rentPolicy;
    private ImageView img;
    private EditText name, address, landmark, contactno;
    private Button placeOrder;
    private DatabaseReference mDatabaseorder;
    private DatabaseReference mDatabaseorderAdmin;
    private FirebaseAuth userAuth;
    private String itemDescriptionString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                itemID = extras.getString("idItem");
                itemType = extras.getString("itemtype");
                itemIsa = extras.getString("itemisa");
            }
        } else {
            itemID = (String) savedInstanceState.getSerializable("idItem");
            itemType = (String) savedInstanceState.getSerializable("itemtype");
            itemIsa = (String) savedInstanceState.getSerializable("itemisa");
        }
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
        itemCondition = findViewById(R.id.condition);
        itemName = findViewById(R.id.item_name_pay);
        rentPolicy = findViewById(R.id.rent_policy);
        itemPrice = findViewById(R.id.price_to_be_paid);
        name = findViewById(R.id.name_order);
        address = findViewById(R.id.address_order);
        contactno = findViewById(R.id.contact_order);
        landmark = findViewById(R.id.landmark_order);
        placeOrder = findViewById(R.id.p_placeorder);
        img = findViewById(R.id.image_item_payment);
        userAuth = FirebaseAuth.getInstance();
        mDatabaseorderAdmin = FirebaseDatabase.getInstance().getReference().child("ordersAdmin");
        mDatabaseorder = FirebaseDatabase.getInstance().getReference().child("orders").child(userAuth.getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("storeDetails").child(itemIsa).child(itemID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemImageString = dataSnapshot.child("itemImage").getValue().toString();
                Glide.with(getApplicationContext()).load(itemImageString).into(img);
                itemName.setText(dataSnapshot.child("itemName").getValue().toString());
                itemNameString = dataSnapshot.child("itemName").getValue().toString();
                itemDescriptionString = dataSnapshot.child("itemDescription").getValue().toString();
                String itemPriceString = dataSnapshot.child("itemPrice").getValue().toString();
                if (itemType.equals("NEW")) {
                    price = "\u20B9 " + Math.round(Float.parseFloat(itemPriceString) * 0.8) + "";
                    itemPrice.setText(price);
                    itemCondition.setText("NEW");
                    rentPolicy.setVisibility(View.GONE);
                } else if (itemType.equals("OLD")) {
                    itemCondition.setText("OLD");
                    price = "\u20B9 " + Math.round(Float.parseFloat(itemPriceString) * 0.6) + "";
                    itemPrice.setText(price);
                    rentPolicy.setVisibility(View.GONE);
                } else if (itemType.equals("RENT")) {
                    itemCondition.setText("On RENT");
                    price = "\u20B9 " + Math.round(Float.parseFloat(itemPriceString) * 0.77) + "";
                    itemPrice.setText(price);
                    rentPolicy.setVisibility(View.VISIBLE);
                } else if (itemType.equals("none")) {
                    itemCondition.setVisibility(View.GONE);
                    price = "\u20B9 " + Math.round(Float.parseFloat(itemPriceString)) + "";
                    itemPrice.setText(price);
                    rentPolicy.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeMethod();
            }
        });
    }

    private void placeMethod() {

        final String nameUserorder = name.getText().toString().trim();
        final String addressorder = address.getText().toString().trim();
        final String landmarkorder = landmark.getText().toString().trim();
        final String contectorder = contactno.getText().toString().trim();
        if (validateOrderDetails(nameUserorder, addressorder, landmarkorder, contectorder)) {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            builder.setTitle("Are You Sure To Place Order")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            placeOrder.setEnabled(false);
                            final String orderId = "EM" + new SimpleDateFormat("ddMMyyyyHHmmss",
                                    Locale.getDefault()).format(new Date());
                            DatabaseReference orderReference = mDatabaseorder.child(orderId);
                            final DatabaseReference orderReferenceAdmin = mDatabaseorderAdmin.child(orderId);
                            final Map<String, String> map = new HashMap<>();
                            map.put("orderAddress", addressorder);
                            map.put("orderUserName", nameUserorder);
                            map.put("orderLandmark", landmarkorder);
                            map.put("orderContactNumber", contectorder);
                            map.put("orderAmount", price);
                            map.put("orderItemId", itemID);
                            if (itemIsa.equals("books"))
                                map.put("orderName", itemNameString + " ( " + itemType + " ) ");
                            else
                                map.put("orderName", itemNameString);
                            map.put("orderStatus", "Order Confirmation Pending");
                            map.put("orderImage", itemImageString);
                            final String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                                    Locale.getDefault()).format(new Date());
                            map.put("orderTime", timeStamp);
                            map.put("orderDescription", itemDescriptionString);
                            orderReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(PaymentPage.this,
                                            "Your Order is Confirmation is Pending & you can see further details in My Orders",
                                            Toast.LENGTH_LONG).show();
                                    placeOrder.setVisibility(View.GONE);
                                    orderReferenceAdmin.setValue(map);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PaymentPage.this,
                                            "There are Some error while placing your order",
                                            Toast.LENGTH_LONG).show();
                                    placeOrder.setEnabled(true);
                                    placeOrder.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    })
                    .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(R.drawable.confirm)
                    .show();
        }
    }

    private boolean validateOrderDetails(String nameorder, String addressorder, String landmarkorder, String contectorder) {
        if (TextUtils.isEmpty(nameorder)) {
            name.setError("Please Enter Name");
        } else if (TextUtils.isEmpty(addressorder)) {
            address.setError("Please Enter full Address");
        } else if (TextUtils.isEmpty(landmarkorder)) {
            landmark.setError("Please Enter LandMark");
        } else if (!checkPhone(contectorder)) {
            contactno.setError("enter valid phone no.");
        } else
            return true;
        return false;
    }

    private boolean checkPhone(String contectOrder) {
        if (Pattern.matches("[0-9]+", contectOrder) && contectOrder.length() == 10)
            return true;
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}