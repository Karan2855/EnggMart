package com.enggmartservices.enggmart.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.adapers.AdapterUsers;
import com.enggmartservices.enggmart.utility.UserDetails;
import com.enggmartservices.enggmart.models.ModelUserClass;
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

import de.hdodenhof.circleimageview.CircleImageView;


public class UsersActivity extends AppCompatActivity {
    private ListView usersList;
    private TextView noUsersText;
    private List<ModelUserClass> listUsers;
    int totalUsers = 0;
    private Context context;
    private DatabaseReference mDatabase;
    private String uid;
    FirebaseUser mUser;
    private RecyclerView mUsersList;
    //private FirebaseRecyclerAdapter<ModelUserClass, UsersViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
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
        setTitle("All Users");



        context = UsersActivity.this;
        mUsersList = findViewById(R.id.usersList);
        noUsersText = (TextView) findViewById(R.id.noUsersText);
        listUsers = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UsersActivity.this);
        mUsersList.setLayoutManager(linearLayoutManager);

        listUsers.clear();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.hasChildren()) {
                    if (dataSnapshot.getKey() != mUser.getUid()) {
                        Map map = (Map) dataSnapshot.getValue();
                        String userName = map.get("name").toString();
                        String userEmail = map.get("email").toString();
                        String userImage = map.get("image").toString();
                        String userStatus = map.get("status").toString();
                        String userUid = dataSnapshot.getKey();
                        String userPhone = map.get("phone").toString();
                        ModelUserClass modelUserClass = new ModelUserClass(userPhone, userUid, userName, userEmail, userImage, userStatus);
                        listUsers.add(modelUserClass);
                        AdapterUsers adapterUsers = new AdapterUsers(context, listUsers);
                        mUsersList.setHasFixedSize(true);
                        mUsersList.setAdapter(adapterUsers);
                    }
                }
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


    /*
    mUsersList.setLayoutManager(new LinearLayoutManager(this));
    mDatabase = FirebaseDatabase.getInstance().getReference();
    Query query = mDatabase.child("users");

    FirebaseRecyclerOptions<ModelUserClass> options =
            new FirebaseRecyclerOptions.Builder<ModelUserClass>()
                    .setQuery(query, ModelUserClass.class)
                    .build();
    firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelUserClass, UsersViewHolder>(options) {
        @Override
        protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull ModelUserClass model) {
            final String list_user_id = getRef(position).getKey();
            final String userName = model.getName();
            final String chatWithImg = model.getImage();
            if (!list_user_id.equals(mUser.getUid())) {
                holder.setUserNameView(model.getName() + "");
                holder.setUserEmailView(model.getEmail() + "");
                holder.setUserStatusView(model.getStatus() + "");
                holder.setUserImageView(model.getImage(), getApplicationContext());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent chatIntent = new Intent(UsersActivity.this, ChatActivity.class);
                        chatIntent.putExtra("User_Id", list_user_id);
                        UserDetails.chatWith = "" + list_user_id;
                        UserDetails.chatWithname = userName;
                        UserDetails.chatwithImage = chatWithImg;
                        startActivity(chatIntent);
                    }
                });
            }
        }

        @NonNull
        @Override
        public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.usersinflate, parent, false);
            return new UsersViewHolder(view);
        }
    };

    mUsersList.setAdapter(firebaseRecyclerAdapter);
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



public static class UsersViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public UsersViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setUserNameView(String userName) {
        TextView userNameView = mView.findViewById(R.id.nameu);
        userNameView.setText(userName + "");
    }

    public void setUserStatusView(String userStatus) {
        TextView userStatusView = mView.findViewById(R.id.single_user_status);
        if (!userStatus.equals(""))
            userStatusView.setText(userStatus + "");
    }

    public void setUserEmailView(String userEmail) {
        TextView userEmailView = mView.findViewById(R.id.emailu);
        userEmailView.setText(userEmail + "");
    }

    public void setUserImageView(String userImage, Context applicationContext) {
        CircleImageView userImageView = mView.findViewById(R.id.usersdp);
        if (!userImage.equals("not Provided"))
            Glide.with(applicationContext).load(userImage).into(userImageView);
        //Picasso.get().load(userImage).placeholder(R.mipmap.usera).into(userImageView);
    }
}*/
    @Override
    public void onBackPressed() {
        finish();
    }

}
