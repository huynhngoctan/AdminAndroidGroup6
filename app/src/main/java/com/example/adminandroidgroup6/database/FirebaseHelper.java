package com.example.adminandroidgroup6.database;

import android.content.Context;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.menuFoods.FoodAdapter;
import com.example.adminandroidgroup6.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {
    ListView listView;
    FoodAdapter adapter;
    DatabaseReference db;
    boolean saved;
    boolean deleted;
    ArrayList<Food> listFoods = new ArrayList<>();
    ArrayList<Food> listToppings = new ArrayList<>();
    Context context;

    public FirebaseHelper(ListView listView, DatabaseReference db, Context context, boolean isTopping) {
        this.listView = listView;
        this.db = db;
        this.context = context;
        adapter = new FoodAdapter(this.context, R.layout.layout_food_item, isTopping?listToppings:listFoods);
        this.listView.setAdapter(adapter);
        retrieve();
    }

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public boolean save(Food food) {
        if (food == null) saved = false;
        else {
            try {
                String childID = db.child("Food").push().getKey();
                food.setId(childID);
                db.child("Food").child(childID).setValue(food);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
    public boolean update(Food food) {
        if (food == null) saved = false;
        else {
            try {
                db.child("Food").child(food.getId()).setValue(food);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }
    public boolean delete(String id){
        if (id == null) deleted = false;
        else {
            try {
                db.child("Food").child(id).removeValue();
                deleted = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                deleted = false;
            }
        }
        return deleted;
    }
    public void retrieve() {
        db.child("Food").addValueEventListener(new ValueEventListener() {
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
        listFoods.clear();
        listToppings.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Food food = ds.getValue(Food.class);
            if (food.isTopping()) {
                listToppings.add(food);
            } else
                listFoods.add(food);
        }
        adapter.notifyDataSetChanged();
    }

  public Food getFoodByPosition(int position){
        return  listFoods.get(position);
  }
}
