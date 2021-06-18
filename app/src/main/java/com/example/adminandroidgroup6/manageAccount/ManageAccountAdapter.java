package com.example.adminandroidgroup6.manageAccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminandroidgroup6.R;
import com.example.adminandroidgroup6.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageAccountAdapter extends RecyclerView.Adapter<ManageAccountAdapter.ManageAccountViewHolder> {
    private Context context;
    private ArrayList<User> listUsers;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
       void onItemClick(int position) ;
    }

    public ManageAccountAdapter(Context context, ArrayList<User> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
    }
public void setOnItemClickListener(OnItemClickListener listener){
        mListener =listener;
}
    @NonNull
    @Override
    public ManageAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_item_manage_user, parent, false);
        return new ManageAccountViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageAccountAdapter.ManageAccountViewHolder holder, int position) {
        User user = listUsers.get(position);
        if(user==null) return;
        holder.tvUserName.setText(user.getUsername());
        holder.tvRole.setText(user.getRole());
        holder.tvStatus.setText(user.getStatus());
        if(user.getLinkImage()!=null)
        Picasso.with(this.context).load(user.getLinkImage()).fit().centerCrop().into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        if(listUsers!=null) return listUsers.size();
        return 0;
    }


    public static class ManageAccountViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivAvatar;
        private TextView tvUserName;
        private TextView tvRole;
        private TextView tvStatus;

        public ManageAccountViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.imageViewAvatarLayoutUserItemManageUser);
            tvUserName = itemView.findViewById(R.id.textViewUserNameLayoutUserItemManageUser);
            tvRole = itemView.findViewById(R.id.textViewRoleLayoutUserItemManageUser);
            tvStatus = itemView.findViewById(R.id.textViewStatusLayoutUserItemManageUser);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
