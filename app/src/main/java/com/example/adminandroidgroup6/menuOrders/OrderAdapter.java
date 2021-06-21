package com.example.adminandroidgroup6.menuOrders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.model.Order;
import com.example.adminandroidgroup6.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private ArrayList<Order> listOrder;
    private Map<String , User> mapUsers;
    private OnItemClickListener mListener;

    public OrderAdapter(Context context, ArrayList<Order> listOrder, Map<String, User> mapUsers) {
        this.context = context;
        this.listOrder = listOrder;
        this.mapUsers = mapUsers;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_item,parent,false);

        return new OrderViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  OrderAdapter.OrderViewHolder holder, int position) {
        Order order = listOrder.get(position);
        User user  = mapUsers.get(order.getIdUser());
        if(order==null||user==null)return;
        holder.name.setText(user.getName());
        holder.date.setText(order.getDateDelivery());
        holder.address.setText(order.getAddress());
        if (user.getLinkImage() != null)
            Picasso.with(this.context).load(user.getLinkImage()).fit().centerCrop().into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        if(listOrder!= null) {
            return listOrder.size();
        }
        return 0;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView date;
        private TextView address;
        private CircleImageView ivAvatar;

        public OrderViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewCustomerNameLayoutOrderItem);
            date = itemView.findViewById(R.id.textViewDateOrderLayoutOrderItem);
            address = itemView.findViewById(R.id.textViewAddressOrderLayoutOrderItem);
            ivAvatar =itemView.findViewById(R.id.imageViewAvatarLayoutOrderItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
