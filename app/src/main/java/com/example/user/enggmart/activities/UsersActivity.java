package com.example.user.enggmart.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.enggmart.R;
import com.example.user.enggmart.utility.UserDetails;
import com.example.user.enggmart.adapers.AdapterUsers;
import com.example.user.enggmart.models.ModelUserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UsersActivity extends AppCompatActivity {
    private ListView usersList;
    private TextView noUsersText;
    private List<ModelUserClass> listUsers = new ArrayList<>();
    int totalUsers = 0;
    private Context context;
    private DatabaseReference mDatabase;
    private FirebaseAuth userAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

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
        setTitle("Users");

        usersList = (ListView) findViewById(R.id.usersList);
        noUsersText = (TextView) findViewById(R.id.noUsersText);
        userAuth = FirebaseAuth.getInstance();
        uid = userAuth.getCurrentUser().getUid().toString();

        context = UsersActivity.this;
        final ProgressDialog pd = new ProgressDialog(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        pd.setMessage("Loading Users...");
        pd.setCancelable(false);
        pd.show();
        listUsers.clear();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    if (!userSnapshot.getKey().equals(uid)) {
                        ModelUserClass modelUserClass = new ModelUserClass();
                        Log.e("name", userSnapshot.child("name").getValue().toString());
                        Log.e("email", userSnapshot.child("email").getValue().toString());
                        Log.e("phone", userSnapshot.child("phone").getValue().toString());
                        Log.e("Image", userSnapshot.child("image").getValue().toString());

                        modelUserClass.setEmail(userSnapshot.child("email").getValue().toString());
                        modelUserClass.setName(userSnapshot.child("name").getValue().toString());
                        modelUserClass.setImage(userSnapshot.child("image").getValue().toString());
                        modelUserClass.setPhone(userSnapshot.child("phone").getValue().toString());
                        modelUserClass.setChatWithuser(userSnapshot.getKey() + "");
                        listUsers.add(modelUserClass);
                    }
                    totalUsers++;
                }
                if (totalUsers <= 1) {
                    noUsersText.setVisibility(View.VISIBLE);
                    usersList.setVisibility(View.GONE);
                } else {
                    noUsersText.setVisibility(View.GONE);
                    usersList.setVisibility(View.VISIBLE);
                    usersList.setAdapter(new AdapterUsers(context, listUsers));
                }
                pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = listUsers.get(position).getChatWithuser();
                UserDetails.chatWithname = listUsers.get(position).getName();
                UserDetails.chatwithImage=listUsers.get(position).getImage();
                startActivity(new Intent(context, ChatActivity.class));
                listUsers.clear();
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UsersActivity.this,HomeActivity.class));
        finish();
    }
}
