package com.example.adminandroidgroup6.menuOrders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adminandroidgroup6.R;

public class SuccessfullOrderViewPagerFragment extends Fragment {
    @Nullable

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_successfull_viewpager,container,false);
    }
}
