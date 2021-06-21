package com.example.adminandroidgroup6.menuOrders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.model.Food;
import com.example.adminandroidgroup6.model.OrderDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private Context context;
    private ArrayList<OrderDetail> listOrderDetails;
    private Map<String,Food> mapFoods;

    public OrderDetailAdapter(Context context, ArrayList<OrderDetail> listOrderDetails,Map<String,Food>  mapFoods) {
        this.context = context;
        this.listOrderDetails = listOrderDetails;
        this.mapFoods =mapFoods;
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
        Food food  = mapFoods.get(orderDetail.getIdFood());
        if(orderDetail==null||food == null) return;
        holder.tvFoodName.setText(food.getFoodName());
        holder.tvQuantity.setText("Số lượng: "+orderDetail.getQuantity());
        holder.tvTotalPrice.setText("Tổng tiền: "+food.getPrice()*orderDetail.getQuantity());
        if (food.getLinkImage() != null)
            Picasso.with(this.context).load(food.getLinkImage()).fit().centerCrop().into(holder.ivFood);
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
        private CircleImageView ivFood;
        public OrderDetailViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.textViewFoodNameLayoutFoodItemOrderDetail);
            tvQuantity = itemView.findViewById(R.id.textViewQuantityLayoutFoodItemOrderDetail);
            tvTotalPrice  =itemView.findViewById(R.id.textViewTotalPriceLayoutFoodItemOrderDetail);
            ivFood = itemView.findViewById(R.id.imageViewFoodLayoutFoodItemOrderDetail);
        }
    }
}
