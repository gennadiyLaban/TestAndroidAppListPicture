package com.example.tagudur.ui.details;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagudur.ui.application.VMRepository;
import com.example.tagudur.presenters.details.IDetailsUserVM;
import com.example.tagudur.presenters.entitiyes.PresentUser;
import com.example.tagudur.presenters.details.IDetailsScreenActionListener;
import com.example.tagudur.presenters.details.IDetailsScreenVMListeners;
import com.example.tagudur.presenters.utils.IConstantsVM;
import com.example.tagudur.testlistpictureapp.R;

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

        user_id = getIntent().getIntExtra(IConstantsVM.USER_ID_EXTRA, -1);
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

    private void registrateDetailsVMListener(IDetailsUserVM detailsUserVM) {
        detailsUserVM.registrateVMlListeners(new IDetailsScreenVMListeners() {
            @Override
            public void onDataChanged(PresentUser user) {
                imageView.setImageBitmap(user.getPicture());
                name.setText(user.getLastName() + " " + user.getFirstName());
                url.setText(user.getUrlPicture());
            }

            @Override
            public void onDataFailed(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
