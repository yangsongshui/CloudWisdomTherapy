package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.FriendAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.User;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MyDecoration;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment {
    @BindView(R.id.news_num_tv)
    TextView newsNumTv;
    @BindView(R.id.recyclerMagicView)
    RecyclerView recyclerMagicView;
    List<User> mList;
    FriendAdapter adapter;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mList.add(new User("华佗再世","http://img.my.csdn.net/uploads/201407/26/1406382922_6166.jpg","12365478901",1));
        mList.add(new User("华佗再世","http://img.my.csdn.net/uploads/201407/26/1406382923_8437.jpg","12365478901",0));
        mList.add(new User("华佗再世","http://img.my.csdn.net/uploads/201407/26/1406382900_2418.jpg","12365478901",1));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMagicView.setLayoutManager(layoutManager);
        adapter = new FriendAdapter(mList);
        recyclerMagicView.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        recyclerMagicView.setAdapter(adapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friend;
    }


    @OnClick(R.id.friend_new_rl)
    public void onClick() {
    }



}
