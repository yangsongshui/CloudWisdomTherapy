package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
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
    }

    private void initToolbar() {
        mShareAPI = UMShareAPI.get(this);
        toastor = new Toastor(this);
        tvToolbarTitle.setText(getResources().getString(R.string.login));
        map = new HashMap<>();

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
                //登陆
                login();

                break;
            case R.id.login_qq_iv:
                //QQ登陆
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.login_wechat_iv:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                //微信登陆
                //mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
        }
    }

    private void login() {
        String phone = loginPhoneEt.getText().toString().trim();
        final String psw = loginPswEt.getText().toString().trim();
        map.clear();
        map.put("phoneNumber", phone);
        map.put("passWord", MD5.getMD5(psw));
        NetworkRequests.GetRequests(this, Constant.LOGIN, map, new JsonDataReturnListener() {
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
                    user.setPhone(object.optString("phoneNumber"));
                    user.setPic(object.optString("headPic"));
                    user.setWidth(object.optString("weight"));
                    user.setHospital(object.optString("hospital"));
                    user.setDepartment(object.optString("consultingRoom"));
                    user.setName(object.optString("nickName"));
                    user.setPsw(psw);
                    if (object.optInt("checkStatu") == 1)
                        user.setType(1);
                    else
                        user.setType(2);
                    MyApplication.newInstance().setUser(user);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            for (String key : data.keySet()) {
                Log.e("-----", "QQ" + "key= " + key + " and value= " + data.get(key));
            }
            if (platform.equals(SHARE_MEDIA.QQ)) {
                if (data.get("screen_name") != null) {

                    Log.e("-----", data.get("openid"));
                    Log.e("-----", data.get("screen_name"));

                } else {
                    mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
                }

            } else if (platform.equals(SHARE_MEDIA.WEIXIN) || platform.equals(SHARE_MEDIA.WEIXIN_CIRCLE) || platform.equals(SHARE_MEDIA.WEIXIN_FAVORITE)) {

                if (data.get("nickname") != null) {

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
}
