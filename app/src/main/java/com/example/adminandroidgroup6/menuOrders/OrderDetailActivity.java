package com.example.adminandroidgroup6.menuOrders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.model.OrderDetail;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    OrderDetailAdapter adapter;
    ArrayList<OrderDetail> listOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Chi tiết đơn hàng");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewFoodActivityOrderDetail);
        listOrderDetails = new ArrayList<>();
        addList();
        adapter = new OrderDetailAdapter(this,listOrderDetails);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
            default:return super.onOptionsItemSelected(item);
        }
    }
    public void addList(){
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
        listOrderDetails.add(new OrderDetail("a",12,23));
    }
}