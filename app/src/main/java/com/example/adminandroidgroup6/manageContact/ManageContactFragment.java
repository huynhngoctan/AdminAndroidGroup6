package com.example.adminandroidgroup6.manageContact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.database.FireBaseHelperContact;
import com.google.firebase.database.FirebaseDatabase;

public class ManageContactFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    FireBaseHelperContact helper;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_contact,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewManageContact);
        helper = new FireBaseHelperContact(getActivity(), FirebaseDatabase.getInstance().getReference(), recyclerView);
        return view;
    }

}
