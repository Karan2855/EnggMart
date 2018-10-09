package com.enggmartservices.enggmart.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.adapers.OrderAdapter;
import com.enggmartservices.enggmart.models.ModelOrders;
import com.enggmartservices.enggmart.utility.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyOrdersActivity extends AppCompatActivity {
    private RecyclerView mOrdersListView;
    private List<ModelOrders> list;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
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
        setTitle("My Orders");

        context = MyOrdersActivity.this;
        mOrdersListView = findViewById(R.id.my_orders_list);
        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mOrdersListView.setLayoutManager(linearLayoutManager);
        list.clear();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("orders").child(mUser.getUid());
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Map map = (Map) dataSnapshot.getValue();
                String orderName = map.get("orderName").toString();
                String orderImage = map.get("orderImage").toString();
                String orderStatus = map.get("orderStatus").toString();
                String orderID = dataSnapshot.getKey();
                ModelOrders modelOrders = new ModelOrders(orderID, orderName, orderStatus, orderImage);
                list.add(modelOrders);
                OrderAdapter orderAdapter = new OrderAdapter(context, list);
                mOrdersListView.setHasFixedSize(true);
                mOrdersListView.setAdapter(orderAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}


      /*  Query query = mDatabase.child("orders").child(mUser.getUid());
        FirebaseRecyclerOptions<ModelOrders> options =
                new FirebaseRecyclerOptions.Builder<ModelOrders>()
                        .setQuery(query, ModelOrders.class)
                        .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelOrders, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull ModelOrders model) {
                final String list_order_id = getRef(position).getKey();
                holder.setOrderIDView(list_order_id + "");
                holder.setOrderNameView(model.getOrderName() + "");
                holder.setOrderStatusView(model.getOrderStatus() + "");
                holder.setOrderImageView(model.getOrderImage(), getApplicationContext());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                          *//*  Intent chatIntent = new Intent(MyOrdersActivity.this, ChatActivity.class);
                            chatIntent.putExtra("User_Id", list_user_id);
                           *//**//* UserDetails.chatWith = "" + list_user_id;
                            UserDetails.chatWithname = userName;
                            UserDetails.chatwithImage = chatWithImg;*//**//*
                            startActivity(chatIntent);*//*
                    }
                });
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.inflate_my_orders, parent, false);
                return new OrderViewHolder(view);
            }
        };

        mOrdersListView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }


    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public OrderViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setOrderIDView(String orderID) {
            TextView orderIDView = mView.findViewById(R.id.order_id_list_order);
            orderIDView.setText(orderID + "");
        }

        public void setOrderNameView(String orderName) {
            TextView orderNameView = mView.findViewById(R.id.item_name_list_order);
            orderNameView.setText(orderName + "");
        }

        public void setOrderStatusView(String orderStatus) {
            TextView orderStatusView = mView.findViewById(R.id.status_list_order);
            orderStatusView.setText(orderStatus + "");
        }


        public void setOrderImageView(String orderImage, Context applicationContext) {
            ImageView orderImageView = mView.findViewById(R.id.image_list_order);
            Glide.with(applicationContext).load(orderImage).into(orderImageView);
        }
    }


}*/