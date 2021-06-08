package com.example.adminandroidgroup6.menuFoods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.model.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Food> listFoods;

    public FoodAdapter(Context context, int layout, List<Food> listFoods) {
        this.context = context;
        this.layout = layout;
        this.listFoods = listFoods;
    }

    @Override
    public int getCount() {
        return listFoods.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        ImageView ivFood;
        TextView tvFoodName, tvType, tvPrice, tvStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder.tvFoodName = convertView.findViewById(R.id.textViewFoodNameLayoutFoodItem);
            viewHolder.tvType =convertView.findViewById(R.id.textViewTypeFoodLayoutFoodItem);
            viewHolder.tvPrice = convertView.findViewById(R.id.textViewPriceLayoutFoodItem);
            viewHolder.tvStatus = convertView.findViewById(R.id.textViewStatusLayoutFoodItem);
            viewHolder.ivFood = convertView.findViewById(R.id.imageViewFoodLayoutFoodItem);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Food food = listFoods.get(position);
        viewHolder.tvFoodName.setText(food.getFoodName());
        viewHolder.tvType.setText(food.getType());
        viewHolder.tvPrice.setText(""+food.getPrice()+" vnđ");
        viewHolder.tvStatus.setText(food.getStatus());

        if(food.getLinkImage()!=null)
            Picasso.with(context).load(food.getLinkImage()).fit().centerCrop()
                    .into(viewHolder.ivFood);
        else viewHolder.ivFood.setImageResource(R.drawable.ic_image);
        return convertView;
    }
}
