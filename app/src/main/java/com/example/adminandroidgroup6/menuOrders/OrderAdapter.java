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

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private ArrayList<Order> listOrder;

    public OrderAdapter(Context context, ArrayList<Order> listOrder) {
        this.context = context;
        this.listOrder = listOrder;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_item,parent,false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  OrderAdapter.OrderViewHolder holder, int position) {
        Order order = listOrder.get(position);
        if(order==null)return;
        holder.name.setText(order.getCustomerName());
        holder.date.setText(order.getDate());
        holder.address.setText(order.getAddress());
    }

    @Override
    public int getItemCount() {
        if(listOrder!= null) {
            return listOrder.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView date;
        private TextView address;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewCustomerNameLayoutOrderItem);
            date = itemView.findViewById(R.id.textViewDateOrderLayoutOrderItem);
            address = itemView.findViewById(R.id.textViewAddressOrderLayoutOrderItem);
        }
    }
}
