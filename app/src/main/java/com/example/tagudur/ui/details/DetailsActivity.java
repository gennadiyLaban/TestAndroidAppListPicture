package com.example.tagudur.ui.details;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagudur.ui.application.VMRepository;
import com.example.tagudur.presenters.users.details.IDetailsUserVM;
import com.example.tagudur.presenters.users.UserVM;
import com.example.tagudur.presenters.users.details.IDetailsScreenActionListener;
import com.example.tagudur.presenters.users.details.IDetailsScreenVMListeners;
import com.example.tagudur.presenters.utils.ConstantsVM;
import com.example.tagudur.testlistpictureapp.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends Activity {

    private ImageView imageView;
    private TextView name;
    private TextView url;

    private int user_id;
    private VMRepository vmRepository;
    private IDetailsScreenActionListener actionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        user_id = getIntent().getIntExtra(ConstantsVM.USER_ID_EXTRA, -1);
        if(user_id == -1) {
            this.finish();
        }
        initializeView();
        initializeVMRepository();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IDetailsUserVM detailsUserVM = vmRepository.getDetailsUserVm(user_id);
        actionListener = detailsUserVM.getActionListener();
        registrateDetailsVMListener(detailsUserVM);
    }

    @Override
    protected void onStop() {
        super.onStop();
        actionListener.onDestroy();
    }

    private void initializeView() {
        imageView = findViewById(R.id.iv_picture_user_details);
        name = findViewById(R.id.tv_name_user_details);
        url = findViewById(R.id.tv_url_user_details);
    }

    private void initializeVMRepository() {
        vmRepository = (VMRepository) getApplication();
    }

    private void registrateDetailsVMListener(final IDetailsUserVM detailsUserVM) {
        detailsUserVM.registrateVMlListeners(new IDetailsScreenVMListeners() {
            @Override
            public void onDataChanged(final UserVM user) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateData(user);
                    }
                });
            }

            @Override
            public void onDataFailed(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dataUpdateFail(message);
                    }
                });
            }
        });
    }

    private void updateData(UserVM user) {
        Picasso.with(DetailsActivity.this).load(user.getUrlPicture())
                .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
        name.setText(user.getLastName() + " " + user.getFirstName());
        url.setText(user.getUrlPicture());
    }

    private void dataUpdateFail(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
