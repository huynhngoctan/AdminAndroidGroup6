package com.example.adminandroidgroup6.manageContact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.model.Contact;
import com.example.adminandroidgroup6.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageContactAdapter extends RecyclerView.Adapter<ManageContactAdapter.ManageContactViewHolder> {
private Context context;
private ArrayList<Contact>listContact;
private Map<String, User> mapUsers;

    public ManageContactAdapter(Context context, ArrayList<Contact> listContact, Map<String, User> mapUsers) {
        this.context = context;
        this.listContact = listContact;
        this.mapUsers = mapUsers;
    }

    @NonNull
    @Override
    public ManageContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contact_item, parent, false);
        return new ManageContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageContactAdapter.ManageContactViewHolder holder, int position) {
        Contact contact =listContact.get(position);
        User user = mapUsers.get(contact.getIdUser());
        if(contact==null||user==null)return;
        holder.tvUserName.setText(user.getUsername());
        holder.tvCreateDate.setText(contact.getDateCreate());
        holder.tvStatus.setText(contact.getStatus());
        if(user.getLinkImage()!=null)
            Picasso.with(this.context).load(user.getLinkImage()).fit().centerCrop().into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        if(listContact!=null)
        return listContact.size();
        return 0;
    }

    public class ManageContactViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivAvatar;
        private TextView tvUserName;
        private TextView tvCreateDate;
        private TextView tvStatus;

        public ManageContactViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.imageViewUserAvatarLayoutContactItem);
            tvUserName = itemView.findViewById(R.id.textViewUserNameLayoutContactItem);
            tvCreateDate = itemView.findViewById(R.id.textViewCreateDateLayoutContactItem);
            tvStatus = itemView.findViewById(R.id.textViewStatusLayoutContactItem);
        }
    }
}
