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
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBaseHelperOrder {
    private Context context;
    private DatabaseReference db;
    private ArrayList<Order> listOrderNew;
    private ArrayList<Order> listOrderHandle;
    private ArrayList<Order> listOrderSuccessfull;
    private Map<String, User> mapUsers;
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private LoadingDialog loadingDialog;
    private boolean saved;

    public FireBaseHelperOrder(Context context, DatabaseReference db, RecyclerView recyclerView, String type) {
        this.context = context;
        this.db = db;
        this.recyclerView = recyclerView;
        loadingDialog = new LoadingDialog(this.context);
        loadingDialog.startLoadingDialog();
        listOrderNew = new ArrayList<>();
        listOrderHandle = new ArrayList<>();
        listOrderSuccessfull = new ArrayList<>();
        mapUsers = new HashMap<>();
        switch (type) {
            case "new":
                adapter = new OrderAdapter(this.context, listOrderNew, mapUsers);
                break;
            case "handle":
                adapter = new OrderAdapter(this.context, listOrderHandle, mapUsers);
                break;
            case "successfull":
                adapter = new OrderAdapter(this.context, listOrderSuccessfull, mapUsers);
                break;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context, RecyclerView.VERTICAL, false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                Order order;
                switch (type) {
                    case "new":
                       order = listOrderNew.get(position);
                        break;
                    case "handle":
                        order = listOrderHandle.get(position);
                        break;
                    case "successfull":
                        order = listOrderSuccessfull.get(position);
                        break;
                    default:
                       order=null;
                }
                bundle.putString("type", type);
                bundle.putSerializable("order", order);
                bundle.putSerializable("user", mapUsers.get(order.getIdUser()));
                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
            }
        });
        retrieve();
    }

    public FireBaseHelperOrder(Context context, DatabaseReference db) {
        this.context = context;
        this.db = db;
        loadingDialog = new LoadingDialog(this.context);
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
        listOrderHandle.clear();
        listOrderSuccessfull.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Order order = ds.getValue(Order.class);
            if (order.getStatus().equals("Chờ xác nhận"))
                listOrderNew.add(order);
            if (order.getStatus().equals("Đang xử lý"))
                listOrderHandle.add(order);
            if (order.getStatus().equals("Thành công"))
                listOrderSuccessfull.add(order);
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
    public boolean updateStatus(String id,String status) {
        loadingDialog.startLoadingDialog();
        if (id == null) saved = false;
        else {
            try {
                db.child("Order").child(id).child("status").setValue(status);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        loadingDialog.dismissDialog();
        return saved;
    }
    public boolean delete(String id) {
        loadingDialog.startLoadingDialog();
        if (id == null) saved = false;
        else {
            try {
                db.child("Order").child(id).removeValue();
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        loadingDialog.dismissDialog();
        return saved;
    }
}
