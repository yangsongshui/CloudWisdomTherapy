package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.forget_phone_et)
    EditText forgetPhoneEt;
    @BindView(R.id.forget_coed_et)
    EditText forgetCoedEt;
    @BindView(R.id.forget_psw_et)
    EditText forgetPswEt;
    @BindView(R.id.forget_psw2_et)
    EditText forgetPsw2Et;
    @BindView(R.id.forget_get_coed_tv)
    TextView forget_get_coed_tv;

    @BindView(R.id.forget_phone_tv)
    TextView forgetPhoneTv;
    private String CODE = "";
    Toastor toasr;
    private int type = -1;
    Map<String, String> map;
    private CountDownTimer timer;


    @Override
    protected int getContentView() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map = new HashMap<>();
        toasr = new Toastor(this);
        initToolbar();
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                forget_get_coed_tv.setText(millisUntilFinished / 1000 + "s后重新发送短信");
            }

            @Override
            public void onFinish() {
                forget_get_coed_tv.setText("获取短信验证码");
                forget_get_coed_tv.setEnabled(true);
            }
        };
    }

    private void initToolbar() {
        type = getIntent().getIntExtra("type", -1);
        if (type == 1) {
            tvToolbarTitle.setText(getResources().getString(R.string.login_change));
            forgetPhoneTv.setVisibility(View.VISIBLE);
            forgetPhoneEt.setVisibility(View.GONE);
        } else {
            tvToolbarTitle.setText(getResources().getString(R.string.login_forget_password));
            forgetPhoneTv.setVisibility(View.GONE);
            forgetPhoneEt.setVisibility(View.VISIBLE);
        }

        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @OnClick({R.id.forget_get_coed_tv, R.id.forget_complete_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_get_coed_tv:
                //获取验证码
                getCode();
                break;
            case R.id.forget_complete_tv:
                //点击完成
                comlete();
                break;
        }
    }

    private void comlete() {
        String phone;
        if (type == 1) {
            phone = forgetPhoneTv.getText().toString().trim();
        } else {
            phone = forgetPhoneEt.getText().toString().trim();
        }
        String psw = forgetPswEt.getText().toString().trim();
        String psw2 = forgetPsw2Et.getText().toString().trim();
        String code = forgetCoedEt.getText().toString().trim();
        if (phone.length() == 11) {
            if (CODE.equals(code)) {
                if (psw.length() >= 6 && psw.length() <= 16) {
                    if (psw.equals(psw2)) {
                        //用户注册
                        map.clear();
                        map.put("phoneNumber", phone);
                        map.put("passWord", psw);
                        NetworkRequests.GetRequests(this, Constant.REGISTER, map, new JsonDataReturnListener() {
                            @Override
                            public void jsonListener(JSONObject jsonObject) {
                                Log.e("jsonListener", jsonObject.toString());

                            }
                        });


                    } else
                        toasr.showSingletonToast("两次密码输入不一致");
                } else
                    toasr.showSingletonToast("密码长度不得低于6位或大于十六位");
            } else
                toasr.showSingletonToast("验证码输入错误");
        } else {
            toasr.showSingletonToast("手机号码长度不正确");
        }
    }

    private void getCode() {
        String phone;
        if (type == 1) {
            phone = forgetPhoneTv.getText().toString().trim();
        } else {
            phone = forgetPhoneEt.getText().toString().trim();
        }
        if (phone.length() == 11) {
            timer.start();

            map.clear();
            map.put("type", "1");
            map.put("phoneNumber", phone);
            timer.start();
            NetworkRequests.GetRequests(this, Constant.GETIDENTIFY, map, new JsonDataReturnListener() {
                @Override
                public void jsonListener(JSONObject jsonObject) {
                    Log.e("User", jsonObject.toString());

                    toasr.showSingletonToast(jsonObject.optString("resMessage"));
                    if (jsonObject.optInt("resCode") == 0) {
                        CODE = jsonObject.optJSONObject("resBody").optString("identify");
                    }
                }
            });
        } else {
            toasr.showSingletonToast("手机号码长度不正确");
        }


    }

}
