package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.User;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.HxEaseuiHelper;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.MD5;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.login_phone_et)
    EditText loginPhoneEt;
    @BindView(R.id.login_psw_et)
    EditText loginPswEt;

    Map<String, String> map;
    private UMShareAPI mShareAPI;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initToolbar();
        if (MyApplication.newInstance().getUser() != null) {
            if (MyApplication.newInstance().getUser().getUid().length() > 1) {
                getPlatformInfd(MyApplication.newInstance().getUser().getUid());
            } else
                login(MyApplication.newInstance().getUser().getPhone(), MyApplication.newInstance().getUser().getPsw());
        }
    }

    private void initToolbar() {
        mShareAPI = UMShareAPI.get(this);
        toastor = new Toastor(this);
        tvToolbarTitle.setText(getResources().getString(R.string.login));
        map = new HashMap<>();
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        mShareAPI.get(LoginActivity.this).setShareConfig(config);

    }


    @OnClick({R.id.login_forget_password_tv, R.id.login_register_tv, R.id.login_tv, R.id.login_qq_iv, R.id.login_wechat_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_forget_password_tv:
                //忘记密码
                startActivity(new Intent(this, ForgetPasswordActivity.class).putExtra("type", 0));
                break;
            case R.id.login_register_tv:
                //注册
                startActivity(new Intent(this, RegisterActivity.class));

                break;
            case R.id.login_tv:
                final String phone = loginPhoneEt.getText().toString().trim();
                final String psw = loginPswEt.getText().toString().trim();
                //登陆
                if (phone.length() != 11 || psw.length() < 6) {
                    toastor.showSingletonToast("账号或密码输入错误");
                } else {
                    login(phone, psw);
                }


                break;
            case R.id.login_qq_iv:
                //QQ登陆
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.login_wechat_iv:
                //微信登陆
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
        }
    }


    private void login(final String phone, final String psw) {
        map.clear();
        map.put("phoneNumber", phone);
        map.put("passWord", MD5.getMD5(psw));
        NetworkRequests.getInstance().initViw(this).Ease(Constant.LOGIN, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("LoginActivity", jsonObject.toString());
                if (jsonObject.optInt("resCode") == 0) {
                    toastor.showSingletonToast(jsonObject.optString("resMessage"));
                    JSONObject object = jsonObject.optJSONObject("resBody");
                    User user = new User();
                    user.setAddress(object.optString("city"));
                    user.setSex(object.optString("sex"));
                    user.setBirthday(object.optString("birthday"));
                    user.setHeight(object.optString("height"));
                    user.setPic(object.optString("headPic"));
                    user.setWidth(object.optString("weight"));
                    user.setHospital(object.optString("hospital"));
                    user.setDepartment(object.optString("consultingRoom"));
                    user.setName(object.optString("nickName"));
                    user.setPsw(psw);
                    user.setPhone(phone);

                    if (object.optInt("checkStatu") == 1)
                        user.setType(1);
                    else
                        user.setType(2);
                    MyApplication.newInstance().setUser(user);

                    //Ease(phone, "123456");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    toastor.showSingletonToast(jsonObject.optString("resMessage"));
                    EMClient.getInstance().logout(true);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            // Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            for (String key : data.keySet()) {
                Log.e("-----", "QQ" + "key= " + key + " and value= " + data.get(key));
            }
            if (platform.equals(SHARE_MEDIA.QQ)) {
                if (data.get("uid") != null) {
                    Log.e("-----", data.get("openid"));
                    Log.e("-----", data.get("uid"));
                    getPlatformInfd(data.get("uid"));
                } else {
                    mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
                }

            } else if (platform.equals(SHARE_MEDIA.WEIXIN) || platform.equals(SHARE_MEDIA.WEIXIN_CIRCLE) || platform.equals(SHARE_MEDIA.WEIXIN_FAVORITE)) {

                if (data.get("uid") != null) {
                    getPlatformInfd(data.get("uid"));
                } else {
                    mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (platform.equals(SHARE_MEDIA.QQ)) {
                toastor.showSingletonToast("QQ登陆失败");

            } else {
                toastor.showSingletonToast("微信登陆失败");

            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            if (platform.equals(SHARE_MEDIA.QQ)) {
                toastor.showSingletonToast("QQ登陆取消");

            } else {
                toastor.showSingletonToast("微信登陆取消");

            }
        }
    };

    private void getPlatformInfd(final String uid) {
        map.clear();
        map.put("openID", uid);
        NetworkRequests.getInstance().initViw(this).GetRequests(Constant.THIRDLOGIN, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("LoginActivity", jsonObject.toString());
                if (jsonObject.optInt("resCode") == 0) {

                    JSONObject object = jsonObject.optJSONObject("resBody");
                    User user = new User();
                    user.setAddress(object.optString("city"));
                    user.setSex(object.optString("sex"));
                    user.setBirthday(object.optString("birthday"));
                    user.setHeight(object.optString("height"));
                    user.setPhone(object.optString("phoneNumber"));
                    user.setPic(object.optString("headPic"));
                    user.setWidth(object.optString("weight"));
                    user.setHospital(object.optString("hospital"));
                    user.setDepartment(object.optString("consultingRoom"));
                    user.setName(object.optString("nickName"));
                    user.setPsw("123456");
                    user.setUid(uid);
                    if (object.optInt("checkStatu") == 1)
                        user.setType(1);
                    else
                        user.setType(2);

                    MyApplication.newInstance().setUser(user);
                    Ease(object.optString("phoneNumber"), "123456");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    toastor.showSingletonToast("登录成功");

                } else {
                    //注册
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class).putExtra("type", "3").putExtra("openID", uid));
                }
            }
        });
    }

    private void Ease(final String phone, final String psw) {

        if (!HxEaseuiHelper.getInstance().isLoggedIn())
            EMClient.getInstance().login(phone, psw, new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    Log.d("main", "登录聊天服务器成功！");


                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    Log.d("main", "登录聊天服务器失败！");
                    Log.e("code", message);
                    Ease(phone, psw);

                }
            });
        else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }


    }
}
