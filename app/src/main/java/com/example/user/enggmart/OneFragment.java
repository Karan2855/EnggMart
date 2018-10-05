package com.example.user.enggmart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.enggmart.activities.WritePost;
import com.example.user.enggmart.models.ModelUserClass;
import com.example.user.enggmart.models.PostModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;


public class OneFragment extends Fragment {
    private RecyclerView mPostsListView;
    private PostsItemViewAdapter mPostsItemViewAdapter;
    private List<PostModel> posts;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fabAddPost;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<PostModel, PostsViewHolder> firebaseRecyclerAdapter;
    public static DatabaseReference getmDatabaseUsers;

    public OneFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        mPostsListView = view.findViewById(R.id.posts_list_view);
        fabAddPost = view.findViewById(R.id.fab_add_post);
        mLayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) mLayoutManager).setReverseLayout(true);
        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
        mPostsListView.setLayoutManager(mLayoutManager);
        getmDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("posts");
        FirebaseRecyclerOptions<PostModel> options =
                new FirebaseRecyclerOptions.Builder<PostModel>()
                        .setQuery(query, PostModel.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PostModel, PostsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull PostModel model) {
                holder.setPostUserUid(model.getUid() + "");
                holder.setPostTime(model.getPostTime() + "");
                holder.setPostImage(model.getPostUrl() + "", getApplicationContext());
                holder.setPostDescription(model.getPostDescription() + "");
            }

            @NonNull
            @Override
            public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.inflate_posts_layout, parent, false);
                return new PostsViewHolder(view);
            }
        };

        mPostsListView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WritePost.class));
            }
        });
        return view;
    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public PostsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setPostUserUid(String userUid) {
            final TextView postUserName = mView.findViewById(R.id.posts_user_name);
            final TextView postUserEmail = mView.findViewById(R.id.posts_email);
            final CircleImageView postUserImage = mView.findViewById(R.id.posts_dp);


            DatabaseReference reference = getmDatabaseUsers.child(userUid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    postUserName.setText(dataSnapshot.child("name").getValue().toString());
                    postUserEmail.setText(dataSnapshot.child("email").getValue().toString());
                    String imageurl = dataSnapshot.child("image").getValue().toString();
                    if (!imageurl.equals("not Provided"))
                        Glide.with(getApplicationContext()).load(imageurl).into(postUserImage);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public void setPostTime(String postTime) {
            TextView postTimeView = mView.findViewById(R.id.posts_time);
            postTimeView.setText(postTime);
        }

        public void setPostImage(String imageurl, Context applicationContext) {
            ImageView mPostImageView = mView.findViewById(R.id.posts_img_View);
            if (!imageurl.equals("no Image")) {
                Glide.with(getApplicationContext()).load(imageurl).into(mPostImageView);
                mPostImageView.setVisibility(View.VISIBLE);
            }
        }

        public void setPostDescription(String postDescription) {
            TextView postDescriptionView = mView.findViewById(R.id.posts_description);
            postDescriptionView.setText(postDescription);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }

}
