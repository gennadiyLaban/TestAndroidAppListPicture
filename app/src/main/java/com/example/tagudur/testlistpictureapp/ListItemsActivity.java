package com.example.tagudur.testlistpictureapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.tagudur.model.Core;
import com.example.tagudur.model.entityes.User;
import com.example.tagudur.model.web.LoadHandler;
import com.example.tagudur.viewmodel.ListUserViewModel;
import com.example.tagudur.viewmodel.abstractions.IListUserViewModel;
import com.example.tagudur.viewmodel.entityes.UserViewModel;
import com.example.tagudur.viewmodel.listeners.IListItemsActionListener;
import com.example.tagudur.viewmodel.listeners.IListItemsOpenDetailsActivityListener;
import com.example.tagudur.viewmodel.listeners.IListItemsViewModelListeners;

import java.util.ArrayList;
import java.util.List;

public class ListItemsActivity extends Activity {

    private RecyclerView itemList;
    private ViewModelListAdapter adapter;
    private IListItemsActionListener actionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liast_items);

        Core core = new Core(new LoadHandler());
        IListUserViewModel viewModel = new ListUserViewModel(core);
        actionListener = viewModel.getActionListener();
        viewModel.registrateOpenDetailsActivityListener(new IListItemsOpenDetailsActivityListener() {
            @Override
            public void onOpenDetailActivity(int user_id) {
                Log.d("ListItemsActivity", "onOpenDetailActivity:user_id:" + user_id);
            }
        });

        itemList = (RecyclerView)findViewById(R.id.rv_list_items);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        itemList.setLayoutManager(llm);
        adapter = new ViewModelListAdapter(new ArrayList<UserViewModel>(), new OnItemClickListener() {
            @Override
            public void onItemClickListener(int user_id) {
                actionListener.onItemClick(user_id);
            }
        });

        viewModel.registrateListVMListeners(new IListItemsViewModelListeners() {
            @Override
            public void onDataChanged(List<UserViewModel> users) {
                adapter.setData(users);
                itemList.setAdapter(adapter);
            }

            @Override
            public void onDataFailed(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
            }
        });


    }


}
