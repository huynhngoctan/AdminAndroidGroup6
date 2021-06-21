package com.example.adminandroidgroup6.menuOrders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FireBaseHelperOrderDetail;
import com.example.adminandroidgroup6.model.Order;
import com.example.adminandroidgroup6.model.OrderDetail;
import com.example.adminandroidgroup6.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    String type;
    User user;
    Order order;
    TextView tvName, tvAddress, tvPhone, tvDateCreate, tvDateDelivery, tvTotalPrice;
    RecyclerView rvFoodDetail;
    Button btnSubmit, btnCancel;
    CircleImageView ivAvatar;

    //Firebase
    DatabaseReference db;
    FireBaseHelperOrderDetail helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Chi tiết đơn hàng");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void init() {
        tvName = findViewById(R.id.textViewCustomerNameActivityOrderDetail);
        tvAddress = findViewById(R.id.textViewAddressActivityOrderDetail);
        tvPhone = findViewById(R.id.textViewPhoneNumberActivityOrderDetail);
        tvDateCreate = findViewById(R.id.textViewDateCreateActivitiFoodDetail);
        tvDateDelivery = findViewById(R.id.textViewDateDeliveryActivityOrderDetail);
        tvTotalPrice =findViewById(R.id.textViewTotalPriceActivityOrderDetail);
        rvFoodDetail= findViewById(R.id.recyclerViewFoodActivityOrderDetail);
        btnCancel = findViewById(R.id.buttonCancelActivityOrderDetail);
        btnSubmit = findViewById(R.id.buttonSubmitActivityOrderDetail);
        ivAvatar = findViewById(R.id.imageViewAvatarActivityOrderDetail);
        recyclerView = findViewById(R.id.recyclerViewFoodActivityOrderDetail);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        type = bundle.getString("type");
        user = (User) bundle.getSerializable("user");
        order = (Order) bundle.getSerializable("order");
        switch (type){
            case "new":
                break;
            case "handle":
                btnSubmit.setText("Đã giao");
                btnCancel.setVisibility(View.INVISIBLE);
                break;
            case "success":
                btnSubmit.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);
                break;
        }
        tvName.setText(user.getName());
        tvAddress.setText(order.getAddress());
        tvPhone.setText(user.getPhone());
        tvDateCreate.setText(order.getDateCreate());
        tvDateDelivery.setText(order.getDateDelivery());
        tvTotalPrice.setText(""+order.getTotalPrice());
        if(user.getLinkImage()!=null) Picasso.with(this).load(user.getLinkImage()).fit().centerCrop().into(ivAvatar);

        //Firebase
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FireBaseHelperOrderDetail(this,db,recyclerView,order.getIdOrder());
    }
}