package com.example.user.enggmart;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentPage extends AppCompatActivity {


    private String itemID;
    private String itemPriceget;
    private DatabaseReference mDatabase;
    private TextView itemName, itemPrice;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
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
        itemName = findViewById(R.id.item_name_pay);
        itemPrice = findViewById(R.id.price_to_be_paid);

        img = findViewById(R.id.image_item_payment);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
            } else {
                itemID = extras.getString("idItem");
                itemPriceget = extras.getString("itemprice");
            }
        } else {
            itemID = (String) savedInstanceState.getSerializable("id");
            itemPriceget = (String) savedInstanceState.getSerializable("itemprice");
        }
        itemPrice.setText("\u20B9 " + itemPriceget);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("storeDetails").child(itemID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.child("itemImage").getValue().toString()).into(img);
                itemName.setText(dataSnapshot.child("itemName").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}