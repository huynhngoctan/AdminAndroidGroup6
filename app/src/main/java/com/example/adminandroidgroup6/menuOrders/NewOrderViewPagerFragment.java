package com.example.adminandroidgroup6.menuOrders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FireBaseHelperOrder;
import com.example.adminandroidgroup6.model.Order;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NewOrderViewPagerFragment extends Fragment {
    @Nullable
    private RecyclerView recyclerView;
    private View view;
    private FireBaseHelperOrder helper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_new_viewpager, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewNewOrder);
        helper = new FireBaseHelperOrder(getActivity(), FirebaseDatabase.getInstance().getReference(), recyclerView,"new");
        return view;
    }

}
