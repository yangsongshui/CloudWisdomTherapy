package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

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
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseChatMessageList;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity implements EMCallBack {


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

    @Override
    protected int getContentView() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toolbar_left_iv.setVisibility(View.VISIBLE);
        tv_toolbar_title.setText("张师");
        messageList.init("12345678", 1, null);
        listView = messageList.getListView();
        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(com.hyphenate.easeui.R.color.holo_blue_bright, com.hyphenate.easeui.R.color.holo_green_light,
                com.hyphenate.easeui.R.color.holo_orange_light, com.hyphenate.easeui.R.color.holo_red_light);
        onConversationInit();
        setRefreshLayoutListener();
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }


    @OnClick({R.id.char_msg_et, R.id.char_send_tv, R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.char_msg_et:
                break;
            case R.id.char_send_tv:
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(charMsgEt.getText().toString(), "12345678");
                message.setMessageStatusCallback(this);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);
                charMsgEt.setText("");
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
        }
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
        conversation = EMClient.getInstance().chatManager().getConversation("12345678", EaseCommonUtils.getConversationType(EaseConstant.CHATTYPE_SINGLE), true);
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

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            messageList.refresh();
//               for (int i=0;i<messages.size();i++){
//                   messages.get(i).
//               }
            Log.e("ChatActivity", "收到消息");
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
        messageList.refresh();


    }

    @Override
    public void onError(int i, String s) {
        Log.d("ChatActivity", i + " " + s);
    }

    @Override
    public void onProgress(int i, String s) {
        Log.d("ChatActivity", i + " " + s);
    }


}
