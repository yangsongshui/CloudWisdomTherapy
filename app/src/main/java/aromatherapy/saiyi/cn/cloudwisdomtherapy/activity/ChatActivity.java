package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.APPConfig;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.HxEaseuiHelper;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.SharedPreferencesUtils;
import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity implements EMCallBack, EaseChatFragment.EaseChatFragmentHelper {

    private NotificationManager notificationManager;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.char_msg_et)
    EditText charMsgEt;
    @BindView(R.id.message_list)
    EaseChatMessageList messageList;

    SwipeRefreshLayout swipeRefreshLayout;
    protected ListView listView;
    protected boolean haveMoreData = true;
    protected boolean isloading;
    protected EMConversation conversation;
    protected int pagesize = 20;
    String ToChatUsername;
    String name;

    private static Handler handler;
    private static Runnable myRunnable;

    @Override
    protected int getContentView() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ToChatUsername = getIntent().getStringExtra("ToChatUsername");

        EaseUser easeUser = HxEaseuiHelper.getInstance().getUserInfo(ToChatUsername);
        name = easeUser.getNickname();
        Log.e("ChatActivity",easeUser.getUsername()+" "+ easeUser.getAvatar()+" "+ easeUser.getUsername());
        MyApplication.newInstance().setToChatUsername(ToChatUsername);
        toolbar_left_iv.setVisibility(View.VISIBLE);
        if (name != null) {
            tv_toolbar_title.setText(name);
        } else {
            name = getIntent().getStringExtra("Username");
            tv_toolbar_title.setText(name);
        }
        messageList.init(ToChatUsername, 1, null);
        EMClient.getInstance().chatManager().addMessageListener(Listener);
        listView = messageList.getListView();
        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(com.hyphenate.easeui.R.color.holo_blue_bright, com.hyphenate.easeui.R.color.holo_green_light,
                com.hyphenate.easeui.R.color.holo_orange_light, com.hyphenate.easeui.R.color.holo_red_light);
        onConversationInit();
        setRefreshLayoutListener();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        handler = new Handler();
        myRunnable = new Runnable() {
            @Override
            public void run() {
                messageList.refresh();
            }
        };

    }


    @OnClick({R.id.char_msg_et, R.id.char_send_tv, R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.char_msg_et:
                break;
            case R.id.char_send_tv:
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(charMsgEt.getText().toString(), ToChatUsername);
                //发送消息
                sendMessage(message);
                charMsgEt.setText("");
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
        }
    }

    protected void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }
        //设置要发送扩展消息用户昵称
        message.setAttribute(Constant.USER_NAME, (String) SharedPreferencesUtils.getParam(this.getApplicationContext(), APPConfig.USER_NAME, "nike"));
        //设置要发送扩展消息用户头像
        message.setAttribute(Constant.HEAD_IMAGE_URL, (String) SharedPreferencesUtils.getParam(this.getApplicationContext(), APPConfig.USER_HEAD_IMG, ""));
        message.setMessageStatusCallback(this);
        //send message
        EMClient.getInstance().chatManager().sendMessage(message);
        //refresh ui
        messageList.refreshSelectLast();

    }


    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
                            List<EMMessage> messages;
                            try {

                                messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
                                        pagesize);

                            } catch (Exception e1) {
                                swipeRefreshLayout.setRefreshing(false);
                                return;
                            }
                            if (messages.size() > 0) {
                                messageList.refreshSeekTo(messages.size() - 1);
                                if (messages.size() != pagesize) {
                                    haveMoreData = false;
                                }
                            } else {
                                haveMoreData = false;
                            }

                            isloading = false;

                        } else {
                            Toast.makeText(ChatActivity.this, getResources().getString(com.hyphenate.easeui.R.string.no_more_messages),
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 600);
            }
        });
    }

    protected void onConversationInit() {
        conversation = EMClient.getInstance().chatManager().getConversation(ToChatUsername, EaseCommonUtils.getConversationType(EaseConstant.CHATTYPE_SINGLE), true);
        conversation.markAllMessagesAsRead();
        // the number of messages loaded into conversation is getChatOptions().getNumberOfMessagesLoaded
        // you can change this number
        final List<EMMessage> msgs = conversation.getAllMessages();
        int msgCount = msgs != null ? msgs.size() : 0;
        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
            String msgId = null;
            if (msgs != null && msgs.size() > 0) {
                msgId = msgs.get(0).getMsgId();
            }
            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
        }

    }

    EMMessageListener Listener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            messageList.refresh();

            Log.e("ChatActivity", "收到消息");
            //simpleNotification();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            Log.e("ChatActivity", "收到透传消息");

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };


    @Override
    public void onSuccess() {
        Log.d("ChatActivity", "发送成功");


    }

    @Override
    public void onError(int i, String s) {
        Log.d("ChatActivity", i + " " + s);
    }

    @Override
    public void onProgress(int i, String s) {
        Log.d("ChatActivity", i + " " + s);
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        //设置要发送扩展消息用户昵称
        message.setAttribute(Constant.USER_NAME, (String) SharedPreferencesUtils.getParam(this.getApplicationContext(), APPConfig.USER_NAME, "nikeName"));
        //设置要发送扩展消息用户头像
        message.setAttribute(Constant.HEAD_IMAGE_URL, (String) SharedPreferencesUtils.getParam(this.getApplicationContext(), APPConfig.USER_HEAD_IMG, ""));
    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.newInstance().setToChatUsername("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.newInstance().setToChatUsername("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(myRunnable, 2000);
    }
}
