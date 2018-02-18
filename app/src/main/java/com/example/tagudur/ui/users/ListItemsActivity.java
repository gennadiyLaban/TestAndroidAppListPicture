package com.example.tagudur.ui.users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.tagudur.ui.application.VMRepository;
import com.example.tagudur.presenters.users.list.IUsersScreenVM;
import com.example.tagudur.presenters.users.UserVM;
import com.example.tagudur.presenters.users.list.IUsersScreenActionListener;
import com.example.tagudur.presenters.users.list.OpenDetailsListener;
import com.example.tagudur.presenters.users.list.IUsersScreenVMlListeners;
import com.example.tagudur.presenters.utils.ConstantsVM;
import com.example.tagudur.testlistpictureapp.R;
import com.example.tagudur.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ListItemsActivity extends Activity {

    private RecyclerView itemList;
    private ViewModelListAdapter adapter;
    private IUsersScreenActionListener actionListener;
    private VMRepository vmRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liast_items);
        initializeVMRepository();

        itemList = (RecyclerView)findViewById(R.id.rv_list_items);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        itemList.setLayoutManager(llm);
    }

    @Override
    protected void onStart() {
        super.onStart();

        IUsersScreenVM screenVM = vmRepository.getUsersScreenVM();
        actionListener = screenVM.getActionListener();
        adapter = initializeAdapter(new ArrayList<UserVM>(), actionListener);

        registrateOpenDetailsListener(screenVM);
        registrateUsersScreenVMlListeners(screenVM);
    }

    @Override
    protected void onStop() {
        super.onStop();
        actionListener.onStop();
    }

    private void initializeVMRepository() {
        vmRepository = (VMRepository) getApplication();
    }

    private ViewModelListAdapter initializeAdapter(List<UserVM> userList,
                                                   final IUsersScreenActionListener listener) {
        ViewModelListAdapter adapter =
                new ViewModelListAdapter(userList, new ViewModelListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int user_id) {
                listener.onItemClick(user_id);
            }
        });
        return adapter;
    }

    private void registrateOpenDetailsListener(IUsersScreenVM screenVM) {
        screenVM.registrateOpenDetailsListener(new OpenDetailsListener() {
            @Override
            public void onOpenDetailActivity(int user_id) {
                Log.d("ListItemsActivity", "onOpenDetailActivity:user_id:" + user_id);
                Intent intent = new Intent(ListItemsActivity.this, DetailsActivity.class);
                intent.putExtra(ConstantsVM.USER_ID_EXTRA, user_id);
                startActivity(intent);
            }
        });
    }

    private void registrateUsersScreenVMlListeners(IUsersScreenVM screenVM) {
        screenVM.registrateVMlListeners(new IUsersScreenVMlListeners() {
            @Override
            public void onDataChanged(final List<UserVM> users) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateData(users);
                    }
                });
            }

            @Override
            public void onDataFailed(String message) {
                updateDataFail(message);
            }
        });
    }

    private void updateData(List<UserVM> users) {
        adapter.setData(users);
        itemList.setAdapter(adapter);
    }

    private void updateDataFail(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
