package com.example.adminandroidgroup6.database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.manageAccount.ManageAccountAdapter;
import com.example.adminandroidgroup6.manageAccount.ManageUserDetailActivity;
import com.example.adminandroidgroup6.model.Food;
import com.example.adminandroidgroup6.model.User;
import com.example.adminandroidgroup6.model.UserLogin;
import com.example.adminandroidgroup6.support.LoadingDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseHelperUser {
    private Context context;
    private DatabaseReference db;
    private ArrayList<User> listUsers;
    private boolean isLoadDone=false;
    private LoadingDialog loadingDialog;
    private boolean saved;
    private RecyclerView recyclerView;
    private ManageAccountAdapter adapter;

    public FireBaseHelperUser(Context context,DatabaseReference db) {
        this.context =context;
        this.db = db;
        listUsers = new ArrayList<>();
        loadingDialog = new LoadingDialog(context);
        loadingDialog.startLoadingDialog();
        retrieve();
    }

    public FireBaseHelperUser(Context context, DatabaseReference db, RecyclerView recyclerView) {
        this.context = context;
        this.db = db;
        this.recyclerView = recyclerView;
        listUsers = new ArrayList<>();
        adapter = new ManageAccountAdapter(this.context,listUsers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        loadingDialog = new LoadingDialog(context);
        adapter.setOnItemClickListener(new ManageAccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, ManageUserDetailActivity.class);
                intent.putExtra("user",listUsers
                .get(position));
                context.startActivity(intent);
            }
        });
        loadingDialog.startLoadingDialog();
        retrieve();
    }

    public boolean update(User user) {
        if (user == null) saved = false;
        else {
            try {
                db.child("User").child(user.getIdUser()).setValue(user);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
    public boolean updateStatus(String id,String status) {
        if (id == null) saved = false;
        else {
            try {
                db.child("User").child(id).child("status").setValue(status);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
    public boolean save(User user) {
        if (user == null) saved = false;
        else {
            try {
                String childID = db.child("User").push().getKey();
                user.setIdUser(childID);
                db.child("User").child(childID).setValue(user);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
    public void retrieve() {
        db.child("User").addValueEventListener(new ValueEventListener() {
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
        listUsers.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            User user = ds.getValue(User.class);
            listUsers.add(user);
        }
        isLoadDone=true;
        if(adapter!=null) adapter.notifyDataSetChanged();
        loadingDialog.dismissDialog();
    }
    public boolean checkUserName(String userName){
        for(User u : listUsers){
            if(u.getUsername().equals(userName)){
                return false;
            }
        }
        return true;
    }
    public boolean checkLogin(String userName, String pass){
        for(User u :listUsers){
            if(u.getUsername().equals(userName)){
                if(u.getPassword().equals(pass)){
                    if(u.getStatus().equals("Bị khóa")) {
                        Toast.makeText(context, "Tài khoản đã bị khóa", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if(!u.getRole().equals("Admin")) {
                        Toast.makeText(context, "Tài khoản này không có quyền admin", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    UserLogin userLogin = UserLogin.getInstance();
                    userLogin.setUser(u);
                    return true;
                }
            }
        }
        Toast.makeText(context, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean isLoadDone() {
        return isLoadDone;
    }

    public void setLoadDone(boolean loadDone) {
        isLoadDone = loadDone;
    }
}
