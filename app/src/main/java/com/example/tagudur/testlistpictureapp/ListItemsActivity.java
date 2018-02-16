package com.example.tagudur.testlistpictureapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.tagudur.builders.abstractions.IListVMRepository;
import com.example.tagudur.model.Core;
import com.example.tagudur.model.web.LoadHandler;
import com.example.tagudur.viewmodel.ListUserViewModel;
import com.example.tagudur.viewmodel.abstractions.IListUserVM;
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
    private IListVMRepository vmRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liast_items);
        initializeVMRepository();

        itemList = (RecyclerView)findViewById(R.id.rv_list_items);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        itemList.setLayoutManager(llm);

        IListUserVM viewModel = vmRepository.getListUserVM();
        actionListener = viewModel.getActionListener();
        adapter = initializeAdapter(new ArrayList<UserViewModel>(), actionListener);

        viewModel.registrateOpenDetailsActivityListener(new IListItemsOpenDetailsActivityListener() {
            @Override
            public void onOpenDetailActivity(int user_id) {
                Log.d("ListItemsActivity", "onOpenDetailActivity:user_id:" + user_id);
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
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vmRepository.getListUserVM().unregitrateVMListener();
        vmRepository.getListUserVM().unregistrateOpenDetailsActivityListener();
    }

    private void initializeVMRepository() {
        vmRepository = (IListVMRepository) getApplication();
    }

    private ViewModelListAdapter initializeAdapter(List<UserViewModel> userList,
                                                   final IListItemsActionListener listener) {
        ViewModelListAdapter adapter = new ViewModelListAdapter(userList, new OnItemClickListener() {
            @Override
            public void onItemClickListener(int user_id) {
                listener.onItemClick(user_id);
            }
        });
        return adapter;
    }
}
