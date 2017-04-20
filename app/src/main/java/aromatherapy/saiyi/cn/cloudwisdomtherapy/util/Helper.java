package aromatherapy.saiyi.cn.cloudwisdomtherapy.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.ChatActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;

/**
 * Created by yangsong on 2017/4/18.
 */

public class Helper {

    private NotificationManager notificationManager;
    private static Helper instance = null;
    Context context;

    public synchronized static Helper getInstance() {
        if (instance == null) {
            instance = new Helper();
        }
        return instance;
    }

    private Helper() {
    }

    public void init(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }


    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息

            Log.e("Helper", "收到消息");
            for (EMMessage message : messages) {
                String username = null;

                Log.e("Helper", message.toString());
                if (username.equals(MyApplication.newInstance().getToChatUsername()) || message.getTo().equals(MyApplication.newInstance().getToChatUsername())) {
                    return;
                } else {
                    String mas = ((EMTextMessageBody) message.getBody()).getMessage();
                    simpleNotification(mas, message.getFrom());
                }
            }

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

    private void simpleNotification(String msg, String ToChatUsername) {
        PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0,
                new Intent(context, ChatActivity.class).putExtra("ToChatUsername", ToChatUsername), 0);
        // 通过Notification.Builder来创建通知，注意API Level
        // API16之后才支持
        Notification notify3 = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("您有新短消息，请注意查看！")
                .setContentTitle("您有来自好友新短消息")
                .setContentText(msg)
                .setContentIntent(pendingIntent3).setNumber(1).build(); // 需要注意build()是在API
        // level16及之后增加的，API11可以使用getNotificatin()来替代
        notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
        notificationManager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
    }

}
