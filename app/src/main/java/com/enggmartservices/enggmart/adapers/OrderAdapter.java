package com.enggmartservices.enggmart.adapers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.activities.OrderDescriptionActivity;
import com.enggmartservices.enggmart.activities.ProductDescription;
import com.enggmartservices.enggmart.models.ModelOrders;
import com.enggmartservices.enggmart.models.StoreModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private List<ModelOrders> listItemsOrder;
    private Context context;


    public OrderAdapter(Context context, List<ModelOrders> listItemsOrder) {
        this.context = context;
        this.listItemsOrder = listItemsOrder;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_my_orders, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ModelOrders modelOrders = listItemsOrder.get(position);
        Log.e("model", modelOrders.getOrderID());
        Log.e("model", modelOrders.getOrderName());
        Log.e("model", modelOrders.getOrderImage());

        holder.orderIDView.setText(modelOrders.getOrderID() + "");
        holder.orderNameView.setText(modelOrders.getOrderName() + "");
        Glide.with(context).load(modelOrders.getOrderImage() + "").into(holder.orderImageView);
        holder.orderStatusView.setText(modelOrders.getOrderStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(holder.itemView.getContext(), OrderDescriptionActivity.class);
                intent.putExtra("idOrder", modelOrders.getOrderID());
                holder.itemView.getContext().startActivity(intent); // start Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemsOrder.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        private View mView;
        private TextView orderIDView, orderNameView, orderStatusView;
        private ImageView orderImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            orderIDView = itemView.findViewById(R.id.order_id_list_order);
            orderNameView = itemView.findViewById(R.id.item_name_list_order);
            orderStatusView = itemView.findViewById(R.id.status_list_order);
            orderImageView = itemView.findViewById(R.id.image_list_order);
        }
    }
}
