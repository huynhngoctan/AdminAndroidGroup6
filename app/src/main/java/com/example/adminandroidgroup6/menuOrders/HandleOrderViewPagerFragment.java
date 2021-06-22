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

public class HandleOrderViewPagerFragment extends Fragment {
    @Nullable
    private RecyclerView recyclerView;
    private View view;
    private FireBaseHelperOrder helper;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_handle_viewpager,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewHandleOrder);
        helper = new FireBaseHelperOrder(getActivity(), FirebaseDatabase.getInstance().getReference(),recyclerView,"handle");
        return view;
    }
}
