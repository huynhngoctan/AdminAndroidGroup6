package com.example.adminandroidgroup6.menuOrders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.adminandroidgroup6.R;
import com.google.android.material.tabs.TabLayout;

public class OrdersFragment  extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    View  view;
    @Nullable

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders,container,false);
        viewPager = view.findViewById(R.id.viewPagerOrders);
        tabLayout = view.findViewById(R.id.tabLayoutOrders);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable  Bundle savedInstanceState) {
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
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
        super.onActivityCreated(savedInstanceState);
    }
    public void setUpViewPager(ViewPager viewPager){
        OrdersPagerAdapter adapter = new OrdersPagerAdapter(getParentFragmentManager());
        adapter.addFragment(new NewOrderViewPagerFragment(),"Đơn mới");
        adapter.addFragment(new HandleOrderViewPagerFragment(),"Đã nhận");
        adapter.addFragment(new SuccessfullOrderViewPagerFragment(),"Thành công");

        viewPager.setAdapter(adapter);
    }
}
