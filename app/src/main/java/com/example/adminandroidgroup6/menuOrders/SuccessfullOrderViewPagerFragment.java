package com.example.adminandroidgroup6.menuOrders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FireBaseHelperOrder;
import com.google.firebase.database.FirebaseDatabase;

public class SuccessfullOrderViewPagerFragment extends Fragment {
    @Nullable
private View view;
    private RecyclerView recyclerView;
    private FireBaseHelperOrder helper;
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_successfull_viewpager,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewSuccessfullOrder);
        helper = new FireBaseHelperOrder(getActivity(), FirebaseDatabase.getInstance().getReference(), recyclerView,"successfull");
        return view;
    }
}
