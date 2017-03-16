package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.NewFriendAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.NewUser;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MyDecoration;
import butterknife.BindView;

public class NewFriendActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.new_friend_rv)
    RecyclerView newFriendRv;
    List<NewUser> mList;
    NewFriendAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_new_friend;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tv_toolbar_title.setText(getResources().getString(R.string.new_friend_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mList = new ArrayList<>();
        mList.add(new NewUser("华佗再世", "http://img.my.csdn.net/uploads/201407/26/1406382922_6166.jpg", "12365478901", 1,0));
        mList.add(new NewUser("华佗再世", "http://img.my.csdn.net/uploads/201407/26/1406382923_8437.jpg", "12365478901", 0,2));
        mList.add(new NewUser("华佗再世", "http://img.my.csdn.net/uploads/201407/26/1406382900_2418.jpg", "12365478901", 1,1));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newFriendRv.setLayoutManager(layoutManager);
        adapter = new NewFriendAdapter(mList,this);
        adapter.setOnbuttonClickListener(this);
        newFriendRv.setAdapter(adapter);
        newFriendRv.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
    }

    @Override
    public void onItemClick(View holder, int position) {

    }
}
