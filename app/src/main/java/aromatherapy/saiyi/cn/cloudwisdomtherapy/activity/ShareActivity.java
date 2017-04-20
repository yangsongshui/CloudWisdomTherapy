package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.FriendAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.User;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.APPConfig;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.SharedPreferencesUtils;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MyDecoration;
import butterknife.BindView;
import butterknife.OnClick;

public class ShareActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.toolbar_left_white_iv)
    ImageView toolbarLeftWhiteIv;
    @BindView(R.id.tv_toolbar_white_title)
    TextView tvToolbarWhiteTitle;

    @BindView(R.id.recyclerMagicView)
    RecyclerView recyclerMagicView;


    List<User> mList;
    FriendAdapter adapter;
    Map<String, String> map;
    Toastor toastor;
    String filePath;

    @Override
    protected int getContentView() {
        return R.layout.activity_share;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        filePath = getIntent().getStringExtra("name");
        tvToolbarWhiteTitle.setText(getResources().getString(R.string.mall_friend));
        toolbarLeftWhiteIv.setVisibility(View.VISIBLE);
        mList = new ArrayList<>();
        map = new HashMap<>();
        toastor = new Toastor(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMagicView.setLayoutManager(layoutManager);
        adapter = new FriendAdapter(mList);
        adapter.setOnItemClickListener(this);
        recyclerMagicView.setAdapter(adapter);
        recyclerMagicView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
    }

    @OnClick(R.id.toolbar_left_white_iv)
    public void onClick() {
        finish();
    }

    @Override
    public void onItemClick(View holder, int position) {
        showSexDialog(mList.get(position).getPhone());
    }

    private void getUser() {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("type", "0");
        NetworkRequests.getInstance().initViw(this).GetRequests(Constant.FINDFRIENDS, map, new JsonDataReturnListener() {
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

    @Override
    public void onResume() {
        super.onResume();
        getUser();
        Log.e("onResume", "onResume:");
    }

    private void getItem(JSONArray jsonArray) {
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

    private void showSexDialog(final String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否分享给该好友");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ((new File(Environment.getExternalStorageDirectory() + "/saving_picture/" + filePath)).exists()) {
                    EMMessage message = EMMessage.createImageSendMessage(Environment.getExternalStorageDirectory() + "/saving_picture/" + filePath, false, phone);
                    sendMessage(message, phone);


                } else {
                    Log.e("filePath", filePath);
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


    protected void sendMessage(EMMessage message, final String phone) {
        if (message == null) {
            Log.d("ChatActivity", phone);
            return;
        }
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("ChatActivity", "发送成功");

                startActivity(new Intent(ShareActivity.this, ChatActivity.class).putExtra("ToChatUsername", phone));
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.d("ChatActivity", i + " " + s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.d("ChatActivity", i + " " + s);
            }
        });
        //设置要发送扩展消息用户昵称
        message.setAttribute(Constant.USER_NAME, (String) SharedPreferencesUtils.getParam(this.getApplicationContext(), APPConfig.USER_NAME, "nike"));
        //设置要发送扩展消息用户头像
        message.setAttribute(Constant.HEAD_IMAGE_URL, (String) SharedPreferencesUtils.getParam(this.getApplicationContext(), APPConfig.USER_HEAD_IMG, ""));
        //send message
        EMClient.getInstance().chatManager().sendMessage(message);
    }
}
