package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.ChatActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.EaseConversationAdapater;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {
    @BindView(R.id.listView)
    ListView listView;

    private List<EMConversation> conversationList = new ArrayList<EMConversation>();
    private EaseConversationAdapater adapter;

    Map<String, String> map;
    Toastor toastor;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        toastor = new Toastor(getActivity());
        map = new HashMap<>();

        initData();

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_news;
    }

    private void initData() {
        conversationList.addAll(loadConversationList());

        adapter = new EaseConversationAdapater(getActivity(), 1, conversationList);
        listView.setAdapter(adapter);
        final String st2 = getResources().getString(R.string.Cant_chat_with_yourself);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = (EMConversation) adapter.getItem(position);
                String username = conversation.conversationId();
                if (username.equals(MyApplication.newInstance().getUser().getPhone()))
                    Toast.makeText(getActivity(), st2, Toast.LENGTH_SHORT).show();
                else {
                    // 进入聊天页面
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("ToChatUsername", username);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 获取会话列表
     *
     * @param
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // 获取所有会话，包括陌生人
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        // 过滤掉messages size为0的conversation
        /**
         * 如果在排序过程中有新消息收到，lastMsgTime会发生变化 影响排序过程，Collection.sort会产生异常
         * 保证Conversation在Sort过程中最后一条消息的时间不变 避免并发问题
         */
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    Log.e("loadConversationList", conversation.getLastMessage().toString());
                    sortList.add(
                            new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));

                }
            }
        }
        try {
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);

        }
        return list;
    }

    /**
     * 根据最后一条消息的时间排序
     *
     * @param
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first == con2.first) {
                    return 0;
                } else if (con2.first > con1.first) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            NetworkRequests.getInstance().isShow=false;
            conversationList.clear();
            conversationList.addAll(loadConversationList());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        conversationList.clear();
        conversationList.addAll(loadConversationList());
        adapter.notifyDataSetChanged();

    }

}
