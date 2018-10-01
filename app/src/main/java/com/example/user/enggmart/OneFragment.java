package com.example.user.enggmart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OneFragment extends Fragment {
    private RecyclerView mPostsListView;
    private PostsItemViewAdapter mPostsItemViewAdapter;
    private List<PostModel> posts;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fabAddPost;
    private DatabaseReference mDatabase;

    public OneFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        mPostsListView = view.findViewById(R.id.posts_list_view);
        fabAddPost = view.findViewById(R.id.fab_add_post);
        posts = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) mLayoutManager).setReverseLayout(true);
        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
        mPostsListView.setLayoutManager(mLayoutManager);

/*
        final ProgressDialog pd = new ProgressDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        pd.setMessage("Loading Posts...");
        pd.setCancelable(false);
        pd.show();
*/
        posts.clear();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.hasChildren()) {
                    Log.e("hello", dataSnapshot.toString());
                    String itemID = dataSnapshot.getKey();
                    Map map = (Map) dataSnapshot.getValue();
                    String postUid = map.get("uid").toString();
                    String postUrl = map.get("postUrl").toString();
                    String postDescription = map.get("postDescription").toString();
                    String postTime = map.get("postTime").toString();
                    PostModel postModel = new PostModel();
                    postModel.setUid(postUid);
                    postModel.setPostTime(postTime);
                    postModel.setPostDescription(postDescription);
                    postModel.setPostUrl(postUrl);
                    posts.add(postModel);
                    //  call the constructor of CustomAdapterStore to send the reference and data to Adapter
                    mPostsItemViewAdapter = new PostsItemViewAdapter(getActivity(), posts);
                    mPostsListView.setAdapter(mPostsItemViewAdapter);

                } else {
                    Toast.makeText(getActivity(), "no Items", Toast.LENGTH_SHORT).show();
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


        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WritePost.class));
            }
        });
        return view;
    }
}
