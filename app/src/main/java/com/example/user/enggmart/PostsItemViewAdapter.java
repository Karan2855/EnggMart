package com.example.user.enggmart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by UJJAWAL-KUMAR on 9/20/2018.
 */

class PostsItemViewAdapter extends RecyclerView.Adapter<PostsItemViewAdapter.PostsItemViewHolder> {
    private Context context;
    private List<PostModel> mPosts;
    private DatabaseReference mDatabase;

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
    public void onBindViewHolder(@NonNull final PostsItemViewHolder holder, final int position) {
        final PostModel postModel = mPosts.get(position);
        String postUserUid = postModel.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(postUserUid);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.mPostUserName.setText(dataSnapshot.child("name").getValue().toString());
                holder.mPostUserEmail.setText(dataSnapshot.child("email").getValue().toString());
                String imageurl = dataSnapshot.child("image").getValue().toString();
                if (!imageurl.equals("not Provided"))
                    Glide.with(getApplicationContext()).load(imageurl).into(holder.mPostUserDp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        String imageurl = postModel.getPostUrl();
        if (!imageurl.equals("no Image")) {
            Glide.with(getApplicationContext()).load(imageurl).into(holder.mPostImageView);
            holder.mPostImageView.setVisibility(View.VISIBLE);
        }
        holder.mPostDiscription.setText(postModel.getPostDescription());
        holder.mPostTime.setText(postModel.getPostTime());

        holder.mPostLikeCounts.setText(postModel.getPostLikesCount() + " Likes");
        holder.mPostCommentsCounts.setText(postModel.getPostCommentsCount() + " Comments");
        if (postModel.isPostLiked()) {
            holder.mPostLiketv.setText("Unlike");
        } else
            holder.mPostLiketv.setText("Like");
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class PostsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View mView;
        private ImageView mPostImageView;
        private CircleImageView mPostUserDp;
        private TextView mPostUserName, mPostUserEmail, mPostTime, mPostLikeCounts, mPostCommentsCounts, mPostDiscription, mPostLiketv;
        private LinearLayout mPostLike, mPostComment, mPostShare;
        private ImageView mPostLikeiv;

        public PostsItemViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            mPostUserDp = itemView.findViewById(R.id.posts_dp);
            mPostUserName = itemView.findViewById(R.id.posts_user_name);
            mPostUserEmail = itemView.findViewById(R.id.posts_email);
            mPostTime = itemView.findViewById(R.id.posts_time);
            mPostDiscription = itemView.findViewById(R.id.posts_description);
            mPostImageView = itemView.findViewById(R.id.posts_img_View);
            mPostLikeCounts = itemView.findViewById(R.id.posts_likes_count);
            mPostCommentsCounts = itemView.findViewById(R.id.posts_comments_count);
            mPostLike = itemView.findViewById(R.id.posts_like_post);
            mPostComment = itemView.findViewById(R.id.posts_comment_post);
            mPostShare = itemView.findViewById(R.id.posts_share_post);
            mPostLikeiv = itemView.findViewById(R.id.post_like_iv);
            mPostLiketv = itemView.findViewById(R.id.post_like_tv);
            mPostLike.setOnClickListener(this);
            mPostComment.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.posts_like_post) {
                Toast.makeText(context, "You Liked", Toast.LENGTH_SHORT).show();

            } else if (view.getId() == R.id.posts_comment_post) {
                Toast.makeText(context, "You Commented", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
