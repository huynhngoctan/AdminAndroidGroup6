package com.example.adminandroidgroup6.database;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.manageContact.ManageContactAdapter;
import com.example.adminandroidgroup6.manageContact.ManageContactDetailActivity;
import com.example.adminandroidgroup6.menuOrders.OrderAdapter;
import com.example.adminandroidgroup6.menuOrders.OrderDetailActivity;
import com.example.adminandroidgroup6.model.Contact;
import com.example.adminandroidgroup6.model.Order;
import com.example.adminandroidgroup6.model.User;
import com.example.adminandroidgroup6.support.LoadingDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBaseHelperOrder {
    private Context context;
    private DatabaseReference db;
    private ArrayList<Order> listOrderNew;
    private Map<String, User> mapUsers;
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private LoadingDialog loadingDialog;

    public FireBaseHelperOrder(Context context, DatabaseReference db, RecyclerView recyclerView) {
        this.context = context;
        this.db = db;
        this.recyclerView = recyclerView;
        loadingDialog = new LoadingDialog(this.context);
        loadingDialog.startLoadingDialog();
        listOrderNew = new ArrayList<>();
        mapUsers = new HashMap<>();
        adapter = new OrderAdapter(this.context, listOrderNew, mapUsers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context, RecyclerView.VERTICAL, false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                Order order =listOrderNew.get(position);
                bundle.putString("type","new");
                bundle.putSerializable("order",order);
                bundle.putSerializable("user",mapUsers.get(order.getIdUser()));
                intent.putExtra("bundle",bundle);
                context.startActivity(intent);
            }
        });
        retrieve();
    }

    public void retrieve() {
        db.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fetchData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fetchData(DataSnapshot dataSnapshot) {
        mapUsers.clear();
        listOrderNew.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Order order = ds.getValue(Order.class);
            if(order.getStatus().equals("Chờ xác nhận"))
            listOrderNew.add(order);
            if (!mapUsers.containsKey(order.getIdUser())) getUserDB(order.getIdUser());
        }
        if (adapter != null) adapter.notifyDataSetChanged();
        loadingDialog.dismissDialog();
    }

    public void getUserDB(String id) {
        db.child("User").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mapUsers.put(id, snapshot.getValue(User.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
