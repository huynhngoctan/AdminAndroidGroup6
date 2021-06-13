package com.example.adminandroidgroup6.menuOrders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class OrdersPagerAdapter extends FragmentPagerAdapter {
    private int tabsNumber;
    private ArrayList<Fragment> listFragments=new ArrayList<>();
    private ArrayList<String> listTitles= new ArrayList<>();

    public OrdersPagerAdapter(@NonNull  FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitles.get(position);
    }
    public void addFragment(Fragment fragment, String title){
        listTitles.add(title);
        listFragments.add(fragment);
    }
}
