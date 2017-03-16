package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.login_phone_et)
    EditText loginPhoneEt;
    @BindView(R.id.login_psw_et)
    EditText loginPswEt;


    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initToolbar();
    }

    private void initToolbar() {
        tvToolbarTitle.setText(getResources().getString(R.string.login));

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
                //login();

                break;
            case R.id.login_qq_iv:
                //QQ登陆
                break;
            case R.id.login_wechat_iv:
                //微信登陆
                break;
        }
    }

/*    private void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login(loginPhoneEt.getText().toString().trim(), loginPswEt.getText().toString().trim(), new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                    }
                });
            }
        }).start();
    }*/
}
