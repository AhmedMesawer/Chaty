package com.mesawer.chaty.chaty.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.base.BaseActivity;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mesawer.chaty.chaty.main.MainActivity.CURRENT_USER_INTENT_KEY;
import static com.mesawer.chaty.chaty.utils.StringUtil.notNullOrEmpty;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {

    private static final int RC_PHOTO_PICKER = 2;
    @BindView(R.id.profile_image_view)
    ImageView profileImageView;
    @BindView(R.id.profile_name_text_view)
    TextView profileNameTextView;
    @BindView(R.id.profile_layout)
    ConstraintLayout profileLayout;
    private ProfileContract.Presenter profilePresenter;
    private String userId;
    private User current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        super.view = profileLayout;
        profilePresenter = new ProfilePresenter(this,
                Injection.provideFirebaseProfileDataSource());
        Intent intent = getIntent();
        if (intent != null)
            userId = intent.getStringExtra(CURRENT_USER_INTENT_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (notNullOrEmpty(userId))
            profilePresenter.getCurrentUser(userId)
                    .subscribe(user -> {
                        current = user;
                        profileNameTextView.setText(user.getUserName());
                        if (user.getPhotoUrl() != null)
                            Glide.with(ProfileActivity.this)
                                    .load(user.getPhotoUrl())
                                    .apply(RequestOptions.centerCropTransform())
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(profileImageView);
                    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            profilePresenter.uploadPhoto(data.getData(), current.getUserId());
        }
    }

    @OnClick(R.id.profile_image_view)
    public void onViewClicked() {
        choosePhoto();
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(
                Intent.createChooser(intent, "Choose profile photo"), RC_PHOTO_PICKER);
    }


    @Override
    public void changeProfilePhoto(String photoUrl) {
        current.setPhotoUrl(photoUrl);
        Glide.with(this)
                .load(photoUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(profileImageView);
    }
}
