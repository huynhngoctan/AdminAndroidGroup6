package com.example.adminandroidgroup6.menuFoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.adminandroidgroup6.R;
import com.google.android.material.tabs.TabLayout;

public class FoodsFragment extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager pager;
    Button btnAdd;
    @Nullable

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_foods,container,false);
        pager = view.findViewById(R.id.viewPagerFoods);
        tabLayout = view.findViewById(R.id.tabLayoutFoods);
        btnAdd = view.findViewById(R.id.buttonAddFoods);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFoodActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable  Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(pager);
        tabLayout.setupWithViewPager(pager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager pager) {
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());

        adapter.addFragment(new FoodsViewPaperFragment(),"Thực đơn");
        adapter.addFragment(new ToppingViewPaperFragment(),"Topping");

        pager.setAdapter(adapter);
    }
}
