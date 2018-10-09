package com.enggmartservices.enggmart.adapers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.activities.ChatActivity;
import com.enggmartservices.enggmart.activities.ProductDescription;
import com.enggmartservices.enggmart.activities.UsersActivity;
import com.enggmartservices.enggmart.models.ModelUserClass;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.UserDetails;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.UsersViewHolder> {
    private Context context;
    private List<ModelUserClass> list;

    public AdapterUsers(Context context, List<ModelUserClass> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usersinflate, parent, false);
        return new UsersViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder holder, final int position) {
        final ModelUserClass modelUserClass = list.get(position);
        holder.userEmailView.setText(modelUserClass.getEmail() + "");
        holder.userNameView.setText(modelUserClass.getName() + "");
        if (!modelUserClass.getStatus().equals(""))
            holder.userStatusView.setText(modelUserClass.getStatus() + "");
        if (!modelUserClass.getImage().equals("not Provided"))
            Glide.with(context).load(modelUserClass.getImage()).into(holder.userImageView);
        else
            holder.userImageView.setImageResource(R.mipmap.usera);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(holder.itemView.getContext(), ChatActivity.class);
                chatIntent.putExtra("User_Id", modelUserClass.getUid());
                UserDetails.chatWith = "" + modelUserClass.getUid();
                UserDetails.chatWithname = modelUserClass.getName();
                UserDetails.chatwithImage = modelUserClass.getImage();
               holder.itemView.getContext().startActivity(chatIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class UsersViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView userNameView, userStatusView, userEmailView;
        private CircleImageView userImageView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            userNameView = itemView.findViewById(R.id.nameu);
            userStatusView = itemView.findViewById(R.id.single_user_status);
            userEmailView = itemView.findViewById(R.id.emailu);
            userImageView = itemView.findViewById(R.id.usersdp);
        }
    }
}
