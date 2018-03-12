package com.mesawer.chaty.chaty.add_friend;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.base.BaseActivity;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

import static com.mesawer.chaty.chaty.main.MainActivity.CURRENT_USER_INTENT_KEY;

public class AddFriendActivity extends BaseActivity implements AddFriendContract.View {

    @BindView(R.id.add_friend_rv)
    RecyclerView addFriendRv;
    @BindView(R.id.add_friend_layout)
    FrameLayout addFriendLayout;
    LinearLayoutManager llm;
    AddFriendsAdapter addFriendsAdapter;
    AddFriendContract.Presenter addFriendPresenter;
    User currentUser;
    PublishSubject<User> addFriendButtonObserver = PublishSubject.create();
    PublishSubject<User> friendRequestObservable = PublishSubject.create();

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
        if (currentUser != null)
            setupUsersRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentUser != null)
            addFriendPresenter.getUsers(currentUser);
        addFriendButtonObserver.subscribe(
                user -> addFriendPresenter.sendFriendRequest(currentUser, user));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
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
    public void showButtonAsFriendRequestSent(User user) {
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
