package com.example.adminandroidgroup6.database;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.manageContact.ManageContactAdapter;
import com.example.adminandroidgroup6.manageContact.ManageContactDetailActivity;
import com.example.adminandroidgroup6.model.Contact;
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

public class FireBaseHelperContact {
    private Context context;
    private DatabaseReference db;
    private ArrayList<Contact> listContact;
    private Map<String, User> mapUsers;
    private LoadingDialog loadingDialog;
    private RecyclerView recyclerView;
    private ManageContactAdapter adapter;
    private boolean saved;

    public FireBaseHelperContact(Context context, DatabaseReference db, RecyclerView recyclerView) {
        this.context = context;
        this.db = db;
        this.recyclerView = recyclerView;
        loadingDialog = new LoadingDialog(this.context);
        loadingDialog.startLoadingDialog();
        listContact = new ArrayList<>();
        mapUsers = new HashMap<>();
        adapter = new ManageContactAdapter(this.context,listContact,mapUsers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context,RecyclerView.VERTICAL,false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ManageContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, ManageContactDetailActivity.class);
                Bundle bundle = new Bundle();
                Contact contact =listContact.get(position);
                bundle.putSerializable("contact",contact);
                bundle.putSerializable("user",mapUsers.get(contact.getIdUser()));
                intent.putExtra("bundle",bundle);
                context.startActivity(intent);
            }
        });
        retrieve();
    }

    public FireBaseHelperContact(Context context, DatabaseReference db) {
        this.context = context;
        this.db = db;
    }

    public void retrieve() {
        db.child("Contact").addValueEventListener(new ValueEventListener() {
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
        listContact.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Contact contact = ds.getValue(Contact.class);
            listContact.add(contact);
            if(!mapUsers.containsKey(contact.getIdUser())) getUserDB(contact.getIdUser());
        }
        if(adapter!=null) adapter.notifyDataSetChanged();
        loadingDialog.dismissDialog();
    }
    public void getUserDB(String id){
        db.child("User").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mapUsers.put(id,snapshot.getValue(User.class));
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public boolean updateStatus(String id,String status) {
        if (id == null) saved = false;
        else {
            try {
                db.child("Contact").child(id).child("status").setValue(status);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
    public boolean delete(String id) {
        if (id == null) saved = false;
        else {
            try {
                db.child("Contact").child(id).removeValue();
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
}
