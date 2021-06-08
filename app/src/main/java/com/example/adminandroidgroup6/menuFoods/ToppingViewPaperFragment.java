package com.example.adminandroidgroup6.menuFoods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FirebaseHelper;
import com.google.firebase.database.FirebaseDatabase;


public class ToppingViewPaperFragment extends ListFragment {
    FirebaseHelper helper;
    ListView lvTopping;
    @Nullable

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topping_viewpaper,container,false);
        lvTopping = view.findViewById(android.R.id.list);
        helper = new FirebaseHelper(lvTopping, FirebaseDatabase.getInstance().getReference(),getParentFragment().getContext(),true);
        return view;
    }
}
