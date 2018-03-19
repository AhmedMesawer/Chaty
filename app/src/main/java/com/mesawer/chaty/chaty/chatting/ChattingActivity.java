package com.mesawer.chaty.chaty.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.base.BaseActivity;
import com.mesawer.chaty.chaty.data.Message;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mesawer.chaty.chaty.main.MainActivity.CURRENT_USER_INTENT_KEY;
import static com.mesawer.chaty.chaty.utils.StringUtil.notNullOrEmpty;

public class ChattingActivity extends BaseActivity implements ChattingContract.View {

    public static final String FRIEND_INTENT_KEY = "friend";
    @BindView(R.id.messages_rv)
    RecyclerView messagesRv;
    @BindView(R.id.write_message_edit_text)
    EditText writeMessageEditText;
    @BindView(R.id.send_button)
    ImageButton sendButton;
    @BindView(R.id.load_img_button)
    ImageButton loadImgButton;
    @BindView(R.id.messages_layout)
    ConstraintLayout messagesLayout;
    private ChattingContract.Presenter chattingPresenter;
    private User current;
    private User friend;
    private MessagesAdapter messagesAdapter;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ButterKnife.bind(this);
        super.view = messagesLayout;
        chattingPresenter = new ChattingPresenter(this,
                Injection.provideFirebaseChattingRepository());
        Intent intent = getIntent();
        if (intent != null) {
            current = intent.getParcelableExtra(CURRENT_USER_INTENT_KEY);
            friend = intent.getParcelableExtra(FRIEND_INTENT_KEY);
        }
        setupMessagesRecyclerView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (current != null && friend != null)
            chattingPresenter.getChat(current, friend);
    }

    @Override
    public void showMessages(Message message) {
        messagesAdapter.add(message);
    }

    @Override
    public void sendMessage() {
        Message message = new Message(current.getUserId());
        String text = writeMessageEditText.getText().toString();
        if (notNullOrEmpty(text)) {
            message.setMessage(text);
            chattingPresenter.newMessage(current, friend, message);
            writeMessageEditText.setText("");
        }
    }

    private void setupMessagesRecyclerView() {
        messagesAdapter = new MessagesAdapter(new ArrayList<>(), current, friend);
        llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        messagesRv.setLayoutManager(llm);
        messagesRv.setAdapter(messagesAdapter);
    }

    @OnClick({R.id.send_button, R.id.load_img_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_button:
                sendMessage();
                messagesRv.smoothScrollToPosition(messagesAdapter.getItemCount());
                break;
            case R.id.load_img_button:
                break;
        }
    }
}
