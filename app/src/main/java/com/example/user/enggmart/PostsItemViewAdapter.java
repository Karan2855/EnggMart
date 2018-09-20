package com.example.user.enggmart;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by UJJAWAL-KUMAR on 9/20/2018.
 */

class PostsItemViewAdapter extends RecyclerView.Adapter<PostsItemViewAdapter.PostsItemViewHolder> {
    private Context context;
    private List<PostModel> mPosts;

    public PostsItemViewAdapter(Context context, List<PostModel> posts) {
        this.context = context;
        this.mPosts = posts;
    }


    @NonNull
    @Override
    public PostsItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inflate_posts_layout, viewGroup, false);
        return new PostsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsItemViewHolder holder, int position) {
        PostModel postModel = mPosts.get(position);

        Uri dpuserUri = Uri.parse(postModel.getPostUserdpurl());
        Uri postUri = Uri.parse(postModel.getPostUrl());
        String

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class PostsItemViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private SimpleDraweeView mPostImageView;
        private CircleImageView mPostUserDp;
        private TextView mPostUserName,mPostUserEmail,mPostTime

        public PostsItemViewHolder(View view) {
            super(view);
        }
    }
}
