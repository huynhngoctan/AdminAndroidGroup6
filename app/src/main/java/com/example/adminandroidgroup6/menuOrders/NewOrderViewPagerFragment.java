package com.example.adminandroidgroup6.menuOrders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.model.Order;

import java.util.ArrayList;

public class NewOrderViewPagerFragment extends Fragment {
    @Nullable
private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> listOrder;
    private View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_order_new_viewpager,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewNewOrder);
        listOrder  = new ArrayList<>();
        addOrder();
        orderAdapter = new OrderAdapter(getActivity()
                ,listOrder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderAdapter);
        return view;
    }
    public void addOrder(){
        listOrder.add(new Order("a","b","c"));
        listOrder.add(new Order("a","b","c"));
        listOrder.add(new Order("a","b","c"));
        listOrder.add(new Order("a","b","c"));
        listOrder.add(new Order("a","b","c"));
        listOrder.add(new Order("a","b","c"));
        listOrder.add(new Order("a","b","c"));
        listOrder.add(new Order("a","b","c"));
        listOrder.add(new Order("a","b","c"));
    }
}
