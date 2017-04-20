package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.NewFriendAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnViewClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.NewUser;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MyDecoration;
import butterknife.BindView;

public class NewFriendActivity extends BaseActivity implements OnItemClickListener, OnViewClickListener {
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.new_friend_rv)
    RecyclerView newFriendRv;
    List<NewUser> mList;
    NewFriendAdapter adapter;
    Map<String, String> map;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_new_friend;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map = new HashMap<>();
        toastor = new Toastor(this);
        tv_toolbar_title.setText(getResources().getString(R.string.new_friend_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newFriendRv.setLayoutManager(layoutManager);
        adapter = new NewFriendAdapter(mList, this);
        adapter.setOnbuttonClickListener(this);
        newFriendRv.setAdapter(adapter);
        newFriendRv.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
    }

    @Override
    public void onItemClick(View holder, int position) {
        startActivity(new Intent(this, MyInformationActivity.class).putExtra("user", mList.get(position)).putExtra("type", 1));
    }

    private void getUser() {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("type", "1");
         NetworkRequests.getInstance().initViw(this).GetRequests( Constant.FINDFRIENDS, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    getItem(jsonObject.optJSONObject("resBody").optJSONArray("lists"));
                }
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }

    private void getItem(JSONArray jsonArray) {
        mList.clear();
        if (jsonArray.length() > 0)
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                NewUser user = new NewUser();
                user.setName(jsonObject.optString("nickName"));
                user.setPhone(jsonObject.optString("phoneNumber"));
                user.setSex(jsonObject.optString("sex"));
                user.setAddress(jsonObject.optString("city"));
                user.setBirthday(jsonObject.optString("birthday"));
                user.setHeight(jsonObject.optString("height"));
                user.setWidth(jsonObject.optString("weight"));
                user.setPic(jsonObject.optString("headPic"));
                user.setState(jsonObject.optInt("statu"));
                if (jsonObject.optInt("role") == 1) {
                    user.setType(1);
                    user.setHospital(jsonObject.optString("hospital"));
                    user.setDepartment(jsonObject.optString("consultingRoom"));
                } else {
                    user.setType(0);
                }
                mList.add(user);
            }
        adapter.setmList(mList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
    }

    @Override
    public void OnViewClick(View view, int position, int type) {
        if (type == 0) {
            getUser(type, mList.get(position).getPhone());
        }
    }

    private void getUser(int type, String friendID) {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("friendID", friendID);
        map.put("statu", type + "");
         NetworkRequests.getInstance().initViw(this).GetRequests( Constant.UPDATEFRIENDSTATUS, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    getUser();
                }else
                    toastor.showSingletonToast(jsonObject.optString("resMessage"));
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }

}
