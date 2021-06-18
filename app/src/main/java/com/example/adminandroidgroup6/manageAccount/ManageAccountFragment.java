package com.example.adminandroidgroup6.manageAccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FireBaseHelperUser;
import com.example.adminandroidgroup6.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManageAccountFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    FireBaseHelperUser helper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_account, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewUserFragmentManageUser);
        helper = new FireBaseHelperUser(getActivity(), FirebaseDatabase.getInstance().getReference(), recyclerView);
        return view;
    }

}
