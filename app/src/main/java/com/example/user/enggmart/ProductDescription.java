package com.example.user.enggmart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDescription extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Button button;
    private String itemID;
    private TextView name, price, description;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
            } else {
                itemID = extras.getString("id");
            }
        } else {
            itemID = (String) savedInstanceState.getSerializable("id");
        }
        name = findViewById(R.id.item_name_des);
        price = findViewById(R.id.item_price_des);
        description = findViewById(R.id.item_description_des);
        button = (Button) findViewById(R.id.purchase);
        img = findViewById(R.id.img_item_des);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("storeDetails").child(itemID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Glide.with(getApplicationContext()).load(dataSnapshot.child("itemImage").getValue().toString()).into(img);
                name.setText(dataSnapshot.child("itemName").getValue().toString());
                price.setText(dataSnapshot.child("itemPrice").getValue().toString() + " rs.");
                description.setText(dataSnapshot.child("itemDescription").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductDescription.this, PaymentPage.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);

            }
        });
    }
}
