package com.example.tagudur.testlistpictureapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tagudur.model.IDataListener;
import com.example.tagudur.model.User;
import com.example.tagudur.model.web.LoadHandler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LiastItemsActivity extends Activity {

    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей", "Костя", "Игорь", "Анна", "Денис", "Андрей"  };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liast_items);

        ListView listView = (ListView) findViewById(R.id.lv_list_items_liactivity);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        // присваиваем адаптер списку
        listView.setAdapter(adapter);

        final LoadHandler loadHandler = new LoadHandler();
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(new Runnable() {
            @Override
            public void run() {
                loadHandler.getUserData(new IDataListener() {
                    @Override
                    public void onLoadData(List<User> users) {
                        for (User user: users)
                            Log.d("LoadHandler", user.getId() + " "
                                    + user.getFirstName() + " " + user.getLastName() + " "
                                    + user.getUrlPicture() + " " + user.getPicture().length);
                    }

                    @Override
                    public void onError() {
                        Log.d("LoadHandler", "ERROR");
                    }
                });
            }
        });
    }


}
