package com.example.tagudur.ui.users;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagudur.presenters.users.UserVM;
import com.example.tagudur.testlistpictureapp.R;
import com.example.tagudur.ui.details.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Tagudur on 15.02.2018.
 */

public class ViewModelListAdapter extends RecyclerView.Adapter<ViewModelListAdapter.UserViewHolder> {

    private OnItemClickListener listener;
    private List<UserVM> usersList;

    public ViewModelListAdapter(List<UserVM> usersVMList, OnItemClickListener listener) {
        this.usersList = usersVMList;
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        UserViewHolder holder = new UserViewHolder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        final UserVM user = usersList.get(position);
       holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }



    public void setData(List<UserVM> users) {
        this.usersList = users;
    }



    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private View parent;
        private TextView name;
        private TextView url;
        private ImageView personPhoto;
        private OnItemClickListener listener;

        UserViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            parent = itemView;
            name = (TextView)itemView.findViewById(R.id.tv_name_user_item);
            url = (TextView)itemView.findViewById(R.id.tv_url_user_item);
            personPhoto = (ImageView)itemView.findViewById(R.id.iv_picture_user_item);
            this.listener = listener;
        }

        public void bind(final UserVM userVM) {
            name.setText(userVM.getFirstName() + " " + userVM.getLastName());
            url.setText(userVM.getUrlPicture());
            Picasso.with(parent.getContext()).load(userVM.getUrlPicture())
                    .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(personPhoto);
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(userVM.getId());
                }
            });
        }
    }

    public static interface OnItemClickListener {

        public void onItemClickListener(int user_id);

    }
}
