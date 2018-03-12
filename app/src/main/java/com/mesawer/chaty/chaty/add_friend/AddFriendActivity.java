package com.mesawer.chaty.chaty.add_friend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.base.BaseActivity;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.main.MainActivity;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

import static com.mesawer.chaty.chaty.main.MainActivity.CURRENT_USER_INTENT_KEY;

public class AddFriendActivity extends BaseActivity implements AddFriendContract.View {

    public static final int ADD_FRIEND_REQUEST_CODE = 1;
    @BindView(R.id.add_friend_rv)
    RecyclerView addFriendRv;
    @BindView(R.id.add_friend_layout)
    FrameLayout addFriendLayout;
    LinearLayoutManager llm;
    AddFriendsAdapter addFriendsAdapter;
    AddFriendContract.Presenter addFriendPresenter;
    User currentUser;
    PublishSubject<Action> addFriendButtonObserver = PublishSubject.create();
    PublishSubject<User> friendRequestObservable = PublishSubject.create();
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        super.view = addFriendLayout;
        addFriendPresenter = new AddFriendPresenter(this,
                Injection.provideFirebaseAddFriendsRepository());
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        currentUser = getIntent().getParcelableExtra(CURRENT_USER_INTENT_KEY);
        if (currentUser != null) {
            setupUsersRecyclerView();
            addFriendPresenter.getUsers(currentUser);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

       disposable = addFriendButtonObserver.subscribe(
                action -> {
                    if (action.getAction().equals("add"))
                    addFriendPresenter.sendFriendRequest(currentUser, action.getUser());
                    else addFriendPresenter.cancelFriendRequest(currentUser, action.getUser());
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(CURRENT_USER_INTENT_KEY, currentUser);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

        disposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra(CURRENT_USER_INTENT_KEY, currentUser);
                setResult(RESULT_OK, intent);
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showUsers(User user) {
        addFriendsAdapter.add(user);
    }

    @Override
    public void changeAddFriendButtonText(User user) {
        friendRequestObservable.onNext(user);
    }

    private void setupUsersRecyclerView() {
        llm = new LinearLayoutManager(this);
        addFriendsAdapter =
                new AddFriendsAdapter(new ArrayList<>(), currentUser,
                        addFriendButtonObserver, friendRequestObservable);
        addFriendRv.setLayoutManager(llm);
        addFriendRv.setAdapter(addFriendsAdapter);
    }
}
