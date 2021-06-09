package com.example.adminandroidgroup6.menuFoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;


import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FirebaseHelper;
import com.example.adminandroidgroup6.model.Food;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoodsViewPaperFragment extends ListFragment {
    FirebaseHelper helper;
    ListView lvFood;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foods_viewpaper,container,false);
        lvFood = view.findViewById(android.R.id.list);
        helper = new FirebaseHelper(lvFood,FirebaseDatabase.getInstance().getReference(),getParentFragment().getContext(),false);
        return view;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(),AddFoodActivity.class);
        Bundle bundle = new Bundle();
        Food food = helper.getFoodByPosition(position);
        bundle.putString("action","update");
        bundle.putSerializable("food",food);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
