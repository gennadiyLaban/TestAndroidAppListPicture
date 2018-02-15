package com.example.tagudur.testlistpictureapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tagudur.model.Core;
import com.example.tagudur.model.listeners.IChangeDataListener;
import com.example.tagudur.model.abstractions.IErrorDataMassage;
import com.example.tagudur.model.entityes.User;
import com.example.tagudur.model.web.LoadHandler;

import java.util.List;

public class ListItemsActivity extends Activity {

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

        Core core = new Core(new LoadHandler());
        core.updateData();
        core.registrationChangeDataListener(new IChangeDataListener() {
            @Override
            public void onDataChanged(List<User> users) {
                for (User user: users)
                    Log.d("ListItemActivity",
                            user.getId() + " " + user.getFirstName() + " " + user.getLastName() + " " +
                            user.getUrlPicture() + " " + user.getPicture().length);
            }

            @Override
            public void onFailed(IErrorDataMassage massage) {
                Log.d("ListItemActivity", massage.getMassage());
            }
        });

    }


}
