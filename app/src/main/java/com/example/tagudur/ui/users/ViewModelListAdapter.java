package com.example.tagudur.ui.users;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagudur.presenters.entitiyes.PresentUser;
import com.example.tagudur.testlistpictureapp.R;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class ViewModelListAdapter extends RecyclerView.Adapter<ViewModelListAdapter.UserViewHolder> {

    private OnItemClickListener listener;
    private List<PresentUser> usersList;

    public ViewModelListAdapter(List<PresentUser> usersVMList, OnItemClickListener listener) {
        this.usersList = usersVMList;
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        UserViewHolder holder = new UserViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        final PresentUser user = usersList.get(position);
        holder.name.setText(user.getFirstName() + " " + user.getLastName());
        holder.url.setText(user.getUrlPicture());
        holder.personPhoto.setImageBitmap(user.getPicture());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(user.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }



    public void setData(List<PresentUser> users) {
        this.usersList = users;
    }



    public static class UserViewHolder extends RecyclerView.ViewHolder {
        View parent;
        TextView name;
        TextView url;
        ImageView personPhoto;

        UserViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
            name = (TextView)itemView.findViewById(R.id.tv_name_user_item);
            url = (TextView)itemView.findViewById(R.id.tv_url_user_item);
            personPhoto = (ImageView)itemView.findViewById(R.id.iv_picture_user_item);
        }


    }

    public static interface OnItemClickListener {

        public void onItemClickListener(int user_id);

    }
}
