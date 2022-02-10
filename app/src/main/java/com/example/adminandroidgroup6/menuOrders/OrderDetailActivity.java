package com.example.adminandroidgroup6.menuOrders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FireBaseHelperOrder;
import com.example.adminandroidgroup6.database.FireBaseHelperOrderDetail;
import com.example.adminandroidgroup6.model.Order;
import com.example.adminandroidgroup6.model.OrderDetail;
import com.example.adminandroidgroup6.model.User;
import com.example.adminandroidgroup6.support.TimeCurrent;
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
    int REQUEST_CODE = 1234;

    //Firebase
    DatabaseReference db;
    FireBaseHelperOrderDetail helper;
    FireBaseHelperOrder helperOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Chi tiết đơn hàng");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionDelete();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSubmit();
            }
        });
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionCall();
            }
        });
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
            case "successfull":
                btnSubmit.setVisibility(View.INVISIBLE);
                btnCancel.setText("Xóa");
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
        helperOrder = new FireBaseHelperOrder(this,db);
    }
    public void actionDelete(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Bạn có chắc là muốn hủy đơn hàng này, đơn hàng này sẽ bị xóa");
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteInDB();
            }
        });
        alertDialog.show();
    }
    public void deleteInDB(){
        if(helperOrder.delete(order.getIdOrder())){
            Toast.makeText(this,"Hủy thành công",Toast.LENGTH_SHORT).show();
            finish();
        }else Toast.makeText(this,"Hủy thất bại",Toast.LENGTH_SHORT).show();
    }
    public void actionSubmit(){
        if(type.equals("new")){
            saveToDB("Đang xử lý");
            saveDateDelivery();
        }else if(type.equals("handle")){
            saveToDB("Thành công");
        }
    }
    public void saveDateDelivery(){
        helperOrder.updateDateDelivery(order.getIdOrder(), TimeCurrent.date());
    }
    public void saveToDB(String status){
        if(helperOrder.updateStatus(order.getIdOrder(),status)){
            Toast.makeText(this,"lưu thành công",Toast.LENGTH_SHORT).show();
            finish();
        }else Toast.makeText(this,"Lưu thất bại",Toast.LENGTH_SHORT).show();
    }
    public void actionCall(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        if(requestCode==REQUEST_CODE
                &&grantResults.length>0
                &&grantResults[0]==getPackageManager().PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+user.getPhone()));
            startActivity(intent);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}