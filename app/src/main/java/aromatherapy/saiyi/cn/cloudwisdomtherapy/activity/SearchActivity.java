package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.FriendAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.User;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements OnItemClickListener {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.friend_search_tv)
    EditText friendSearchTv;
    @BindView(R.id.search_friends_rv)
    RecyclerView searchFriendsRv;
    @BindView(R.id.search_no_ll)
    LinearLayout searchNoLl;

    List<User> mList;
    FriendAdapter adapter;
    Map<String, String> map;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        map = new HashMap<>();
        toastor = new Toastor(this);
        tvToolbarTitle.setText(getResources().getString(R.string.compile_cancel2));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchFriendsRv.setLayoutManager(layoutManager);
        adapter = new FriendAdapter(mList);
        adapter.setOnItemClickListener(this);
        searchFriendsRv.setAdapter(adapter);
    }


    @OnClick(R.id.search_cancel_tv)
    public void onClick() {
        if (friendSearchTv.getText().toString().trim().length() > 0)
            getUser(friendSearchTv.getText().toString().trim());
    }

    private void getUser(String msg) {
        map.clear();
        map.put("value", msg + "");
        NetworkRequests.getInstance().initViw(this).GetRequests(Constant.FINDVAGUE, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    getItem(jsonObject.optJSONObject("resBody").optJSONObject("lists").optJSONArray("users"));
                } else {
                    toastor.showSingletonToast(jsonObject.optString("resMessage"));
                }
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }

    private void getItem(JSONArray jsonArray) {
        mList.clear();
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                User user = new User();
                user.setName(jsonObject.optString("nickName"));
                user.setPhone(jsonObject.optString("phoneNumber"));
                user.setSex(jsonObject.optString("sex"));
                user.setAddress(jsonObject.optString("city"));
                user.setBirthday(jsonObject.optString("birthday"));
                user.setHeight(jsonObject.optString("height"));
                user.setWidth(jsonObject.optString("weight"));
                user.setPic(jsonObject.optString("headPic"));

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
            searchNoLl.setVisibility(View.GONE);
            searchFriendsRv.setVisibility(View.VISIBLE);
        } else {
            searchNoLl.setVisibility(View.VISIBLE);
            searchFriendsRv.setVisibility(View.GONE);
        }


    }

    @Override
    public void onItemClick(View holder, int position) {
        startActivity(new Intent(this, MyInformationActivity.class).putExtra("user", mList.get(position)).putExtra("type", 3));
    }
}
