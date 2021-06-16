package com.example.adminandroidgroup6.database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.adminandroidgroup6.model.Food;
import com.example.adminandroidgroup6.model.User;
import com.example.adminandroidgroup6.model.UserLogin;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseHelperUser {
    private Context context;
    private DatabaseReference db;
    private ArrayList<User> listUsers;
    private boolean isLoadDone=false;

    public FireBaseHelperUser(Context context,DatabaseReference db) {
        this.context =context;
        this.db = db;
        listUsers = new ArrayList<>();
        retrieve();
    }
    public void retrieve() {
        db.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fetchData(snapshot);
                System.out.println(snapshot.getValue().toString());
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
        Log.d("AAA",""+listUsers.size());
//        adapter.notifyDataSetChanged();
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
