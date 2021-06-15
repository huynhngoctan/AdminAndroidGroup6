package com.example.adminandroidgroup6.menuOrders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.model.OrderDetail;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private Context context;
    private ArrayList<OrderDetail> listOrderDetails;

    public OrderDetailAdapter(Context context, ArrayList<OrderDetail> listOrderDetails) {
        this.context = context;
        this.listOrderDetails = listOrderDetails;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food_item_order_detail,parent,false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  OrderDetailAdapter.OrderDetailViewHolder holder, int position) {
        OrderDetail orderDetail = listOrderDetails.get(position);
        if(orderDetail==null) return;
        holder.tvFoodName.setText(orderDetail.getFoodName());
        holder.tvQuantity.setText(""+orderDetail.getQuantity());
        holder.tvTotalPrice.setText(""+orderDetail.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        if(listOrderDetails!=null){
            return listOrderDetails.size();
        }
        return 0;
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder{
        private TextView tvFoodName;
        private TextView tvQuantity;
        private TextView tvTotalPrice;
        public OrderDetailViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.textViewFoodNameLayoutFoodItemOrderDetail);
            tvQuantity = itemView.findViewById(R.id.textViewQuantityLayoutFoodItemOrderDetail);
            tvTotalPrice  =itemView.findViewById(R.id.textViewTotalPriceLayoutFoodItemOrderDetail);
        }
    }
}
