package com.enggmartservices.enggmart.activities;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderDescriptionActivity extends AppCompatActivity {
    private TextView orderID, orderName, orderDes, orderUserName, orderDate, orderAddress, orderLandmark, orderAmount, orderStatus, orderContact;
    private ImageView orderImage;
    private DatabaseReference mDatabaseReference;
    private String orderIDString;
    private FirebaseAuth userAuth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_description);
        Utils.darkenStatusBar(this, R.color.colorPrimary);
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
        setTitle("Order Details");
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                orderIDString = extras.getString("idOrder");
            }
        } else {
            orderIDString = (String) savedInstanceState.getSerializable("idOrder");
        }
        findIds();
        userAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child(userAuth.getUid()).child(orderIDString);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.child("orderImage").getValue().toString()).into(orderImage);
                orderID.setText(dataSnapshot.getKey());
                orderAddress.setText(dataSnapshot.child("orderAddress").getValue().toString());
                orderAmount.setText(dataSnapshot.child("orderAmount").getValue().toString());
                orderContact.setText(dataSnapshot.child("orderContactNumber").getValue().toString());
                orderDate.setText(dataSnapshot.child("orderTime").getValue().toString());
                orderLandmark.setText(dataSnapshot.child("orderLandmark").getValue().toString());
                orderName.setText(dataSnapshot.child("orderName").getValue().toString());
                orderUserName.setText(dataSnapshot.child("orderUserName").getValue().toString());
                orderDes.setText(dataSnapshot.child("orderDescription").getValue().toString());
                orderStatus.setText(dataSnapshot.child("orderStatus").getValue().toString());
                if (dataSnapshot.child("orderStatus").getValue().toString().equals("Order Cancled")) {
                    orderStatus.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void findIds() {
        orderID = findViewById(R.id.order_des_id);
        orderAddress = findViewById(R.id.order_des_address);
        orderAmount = findViewById(R.id.order_des_amount);
        orderDate = findViewById(R.id.order_des_time);
        orderImage = findViewById(R.id.order_des_image);
        orderLandmark = findViewById(R.id.order_des_landmark);
        orderDes = findViewById(R.id.order_des_des);
        orderStatus = findViewById(R.id.order_des_status);
        orderUserName = findViewById(R.id.order_des_user_name);
        orderName = findViewById(R.id.order_des_name);
        orderContact = findViewById(R.id.order_des_contact_no);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
