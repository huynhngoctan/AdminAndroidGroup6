package com.example.adminandroidgroup6.menuFoods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;


import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodsViewPaperFragment extends ListFragment {
    FoodAdapter adapter;
    List<Food> listFoods;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        listFoods = new ArrayList<>();
        adapter = new FoodAdapter(getParentFragment().getContext(), R.layout.layout_food_item,listFoods);
        addFood();
        setListAdapter(adapter);
        View view = inflater.inflate(R.layout.fragment_foods_viewpaper,container,false);
        return view;
    }
    public void addFood(){
        listFoods.add(new Food("a","aa",67000,"Đang bán",""));
        listFoods.add(new Food("b","bb",70000,"Đang bán",""));
        listFoods.add(new Food("c","cc",50000,"Đang bán",""));
        listFoods.add(new Food("d","đ",80000,"Đang bán",""));
        listFoods.add(new Food("e","ê",32000,"Đang bán",""));
    }
}
