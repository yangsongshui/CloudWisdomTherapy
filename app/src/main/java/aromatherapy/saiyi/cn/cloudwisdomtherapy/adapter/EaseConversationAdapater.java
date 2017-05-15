package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.MyBaseAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.DateUtil;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.HxEaseuiHelper;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by yangsong on 2017/4/18.
 */

public class EaseConversationAdapater extends MyBaseAdapter<EMConversation> {
    private List<EMConversation> conversationList;
    private List<EMConversation> copyConversationList;

    private boolean notiyfyByFilter;


    public EaseConversationAdapater(List<EMConversation> list) {
        super(list);
    }

    public EaseConversationAdapater(Context context, int resource, List<EMConversation> objects) {
        super(objects);
        conversationList = objects;
        copyConversationList = new ArrayList<>();
        copyConversationList.addAll(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }
        // 获取与此用户/群组的会话
        EMConversation conversation = (EMConversation) getItem(position);

        // 获取用户username或者群组groupid
        EaseUser easeUser = HxEaseuiHelper.getInstance().getUserInfo(conversation.conversationId());
        Log.e("ChatActivity3", easeUser.getUsername() + " " + easeUser.getAvatar() + " " + easeUser.getNick());
        if (easeUser.getAvatar()!=null)
            MyApplication.newInstance().getmImageLoader().load(easeUser.getAvatar()).into(holder.news_pic_cv);
        else
            holder.news_pic_cv.setImageResource(R.drawable.zhuce_user_icon);
        holder.news_name_tv.setText(easeUser.getNickname());
        if (conversation.getUnreadMsgCount() > 0) {
            // 显示与此用户的消息未读数
            holder.news_num_tv.setText(String.valueOf(conversation.getUnreadMsgCount()));
            holder.news_num_tv.setVisibility(View.VISIBLE);
        } else {
            holder.news_num_tv.setVisibility(View.INVISIBLE);
        }
        if (conversation.getAllMsgCount() != 0) {
            // 把最后一条消息的内容作为item的message内容
            EMMessage lastMessage = conversation.getLastMessage();
            String data = DateUtil.dateToString(new Date(lastMessage.getMsgTime()), DateUtil.SHORT_DATE_FORMAT);
            if (lastMessage.getBody().toString().substring(0, 5).equals("image"))
                holder.news_msg_tv.setText("图片");
            else
                holder.news_msg_tv.setText(((EMTextMessageBody) lastMessage.getBody()).getMessage());
            Log.e("-----", lastMessage.getBody().toString());
            holder.news_time_tv.setText(data);


        }

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (!notiyfyByFilter) {
            copyConversationList.clear();
            copyConversationList.addAll(conversationList);
            notiyfyByFilter = false;
        }
    }

    public class ViewHolder {

        private CircleImageView news_pic_cv;
        private TextView news_msg_tv, news_name_tv, news_time_tv, news_num_tv;
        private LinearLayout news_ll;

        public ViewHolder(View itemView) {
            news_pic_cv = (CircleImageView) itemView.findViewById(R.id.news_pic_cv);
            news_name_tv = (TextView) itemView.findViewById(R.id.news_name_tv);
            news_time_tv = (TextView) itemView.findViewById(R.id.news_time_tv);
            news_msg_tv = (TextView) itemView.findViewById(R.id.news_msg_tv);
            news_num_tv = (TextView) itemView.findViewById(R.id.news_num_tv);
            news_ll = (LinearLayout) itemView.findViewById(R.id.news_ll);

        }
    }
}
