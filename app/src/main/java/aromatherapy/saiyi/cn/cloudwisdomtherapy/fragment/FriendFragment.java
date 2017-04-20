package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.domain.EaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.MyInformationActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.NewFriendActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.FriendAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.db.UserDao;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.User;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.SpUtils;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MyDecoration;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment implements OnItemClickListener {
    @BindView(R.id.news_num_tv)
    TextView newsNumTv;
    @BindView(R.id.recyclerMagicView)
    RecyclerView recyclerMagicView;
    List<User> mList;
    FriendAdapter adapter;
    Map<String, String> map;
    Toastor toastor;

    private static Handler handler;
    private static Runnable myRunnable;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mList = new ArrayList<>();
        map = new HashMap<>();
        toastor = new Toastor(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMagicView.setLayoutManager(layoutManager);
        adapter = new FriendAdapter(mList);
        adapter.setOnItemClickListener(this);
        recyclerMagicView.setAdapter(adapter);
        recyclerMagicView.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        handler = new Handler();
        myRunnable = new Runnable() {
            @Override
            public void run() {
                getNews();
                handler.postDelayed(this, 10000);

            }
        };

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friend;
    }


    @OnClick(R.id.friend_new_rl)
    public void onClick() {
        startActivity(new Intent(getActivity(), NewFriendActivity.class));
    }


    @Override
    public void onItemClick(View holder, int position) {
        startActivity(new Intent(getActivity(), MyInformationActivity.class).putExtra("user", mList.get(position)).putExtra("type", 2));
    }

    private void getUser() {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("type", "0");
        NetworkRequests.getInstance().initViw(getActivity()).GetRequests(Constant.FINDFRIENDS, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    getItem(jsonObject.optJSONObject("resBody").optJSONArray("lists"));
                } else {
                    toastor.showSingletonToast(jsonObject.optString("resMessage"));
                }
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }

    private void getItem(JSONArray jsonArray) {
        UserDao dao = new UserDao(getActivity());
        List<EaseUser> users = new ArrayList<EaseUser>();
        mList.clear();
        if (jsonArray.length() > 0)
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
                //存入db
                EaseUser easeUser = new EaseUser(user.getPhone());
                easeUser.setNick(user.getPhone());
                easeUser.setAvatar(user.getPic());
                easeUser.setNickname(user.getName());
                users.add(easeUser);
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
        dao.saveContactList(users);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("onHiddenChanged", "hidden:" + hidden);
        if (!hidden) {
            getUser();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUser();
        handler.post(myRunnable);
        Log.e("onResume", "onResume:");
    }

    private void getNews() {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("type", "1");
        NetworkRequests.getInstance().Requests(Constant.FINDFRIENDS, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    int news = jsonObject.optJSONObject("resBody").optJSONArray("lists").length() - SpUtils.getint(getActivity(), "news", 0);

                    if (news > 0) {
                        newsNumTv.setText(news + "");
                        newsNumTv.setVisibility(View.VISIBLE);
                        SpUtils.putString(getActivity(), "news", news);
                    } else {
                        newsNumTv.setText(news + "");
                        newsNumTv.setVisibility(View.GONE);
                    }
                }
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(myRunnable);
    }
}
