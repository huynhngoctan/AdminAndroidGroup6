package com.example.adminandroidgroup6.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.menuOrders.OrderDetailAdapter;
import com.example.adminandroidgroup6.model.Food;
import com.example.adminandroidgroup6.model.OrderDetail;
import com.example.adminandroidgroup6.support.LoadingDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBaseHelperOrderDetail {
    private Context context;
    private DatabaseReference db;
    private RecyclerView recyclerView;
    private ArrayList<OrderDetail> listOrderDetail;
    private Map<String,Food> mapFoods;
    private LoadingDialog loadingDialog;
    private OrderDetailAdapter adapter;
    private String idOrder;

    public FireBaseHelperOrderDetail(Context context, DatabaseReference db, RecyclerView recyclerView,String idOrder) {
        this.context = context;
        this.db = db;
        this.recyclerView = recyclerView;
        this.idOrder = idOrder;
        loadingDialog = new LoadingDialog(this.context);
        loadingDialog.startLoadingDialog();
        listOrderDetail = new ArrayList<>();
        mapFoods = new HashMap<>();
        adapter = new OrderDetailAdapter(this.context,listOrderDetail, mapFoods);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context,RecyclerView.VERTICAL,false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.context,linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        this.recyclerView.setAdapter(adapter);
        retrieve();
    }
    public void retrieve() {
        db.child("OrderDetail").addValueEventListener(new ValueEventListener() {
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
        listOrderDetail.clear();
        mapFoods.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            OrderDetail orderDetail = ds.getValue(OrderDetail.class);
            if(orderDetail.getIdOrder().equals(idOrder)) {
                listOrderDetail.add(orderDetail);
                if(!mapFoods.containsKey(orderDetail.getIdFood()))
                getFoodDB(orderDetail.getIdFood());
            }
        }
        if (adapter != null) adapter.notifyDataSetChanged();
        loadingDialog.dismissDialog();
    }

    public void getFoodDB(String id) {
        db.child("Food").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mapFoods.put(id,snapshot.getValue(Food.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
