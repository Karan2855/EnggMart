package com.example.user.enggmart;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class OneFragment extends Fragment {
    private RecyclerView mPostsListView;
    private PostsItemViewAdapter mPostsItemViewAdapter;
    private List<PostModel> posts;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fabAddPost;

    public OneFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        mPostsListView = view.findViewById(R.id.posts_list_view);
        fabAddPost = view.findViewById(R.id.fab_add_post);
        posts = new ArrayList<>();
        fillList();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mPostsListView.setLayoutManager(mLayoutManager);
        mPostsItemViewAdapter = new PostsItemViewAdapter(getActivity(), posts);
        mPostsListView.setAdapter(mPostsItemViewAdapter);
        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WritePost.class));
            }
        });
        return view;
    }

    private void fillList() {
        for (int i = 0; i < 20; i++) {
            PostModel postModel = new PostModel();
            posts.add(postModel);
        }
    }
}
