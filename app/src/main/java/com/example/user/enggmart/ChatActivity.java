package com.example.user.enggmart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatActivity extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    TextView name;
    ImageView back;
    CircleImageView dp;
    ScrollView scrollView;
    private String imageWith = "";
    private Bitmap imgBitmap;
    private DatabaseReference mDatabase1, mDatabase2;
    private FirebaseAuth userAuth;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout) findViewById(R.id.layout2);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        name = (TextView) findViewById(R.id.name);
        back = (ImageView) findViewById(R.id.back);
        dp = (CircleImageView) findViewById(R.id.dp);
        userAuth = FirebaseAuth.getInstance();
        uid = userAuth.getCurrentUser().getUid().toString();
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("messages").child(uid + "_" + UserDetails.chatWith);
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("messages").child(UserDetails.chatWith + "_" + uid);

        name.setText(UserDetails.chatWithname);
       imageWith =  UserDetails.chatwithImage;
        Glide.with(getApplicationContext()).load(imageWith).into(dp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                onBackPressed();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString().trim();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.uid);
                    mDatabase1.push().setValue(map);
                    mDatabase2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        mDatabase1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("hello", dataSnapshot.toString());
                Map map = (Map) dataSnapshot.getValue();
                String message = map.get("message").toString();
                String userphone = map.get("user").toString();

                if (userphone.equals(UserDetails.uid)) {
                    addMessageBox("You :", message, 1);
                } else {
                    addMessageBox(UserDetails.chatWithname + " :", message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChatActivity.this,UsersActivity.class));
        finish();
    }

    public void addMessageBox(String user, String message, int type) {
        if (type == 1) {

            LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
            View inflatedLayout = inflater.inflate(R.layout.msgsent, null);
            TextView mesg = (TextView) inflatedLayout.findViewById(R.id.msgs);
            mesg.setText(message);
            layout.addView(inflatedLayout);


        } else {
            LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
            View inflatedLayout = inflater.inflate(R.layout.msgrecev, null, false);
            TextView mesg = (TextView) inflatedLayout.findViewById(R.id.msgr);
            mesg.setText(message);
            TextView usern = (TextView) inflatedLayout.findViewById(R.id.recever);
            usern.setText(user);
            layout.addView(inflatedLayout);

        }
        scrollView.fullScroll(View.FOCUS_DOWN);
    }


}