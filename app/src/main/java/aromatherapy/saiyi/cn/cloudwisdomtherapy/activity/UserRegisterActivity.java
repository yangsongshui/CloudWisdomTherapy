package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.MD5;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class UserRegisterActivity extends BaseActivity {

    @BindView(R.id.registered_name_et)
    EditText registeredNameEt;
    @BindView(R.id.registered_phone_et)
    EditText registered_phone_et;
    @BindView(R.id.registered_coed_et)
    EditText registeredCoedEt;
    @BindView(R.id.registered_psw_et)
    EditText registeredPswEt;
    @BindView(R.id.registered_psw2_et)
    EditText registeredPsw2Et;
    @BindView(R.id.register_verification_tv)
    TextView registerVerificationTv;
    @BindView(R.id.register_add_iv1)
    ImageView registerAddIv1;
    @BindView(R.id.register_add_iv2)
    ImageView registerAddIv2;
    @BindView(R.id.register_add_iv3)
    ImageView registerAddIv3;
    @BindView(R.id.register_verification_ll)
    LinearLayout registerVerificationLl;
    @BindView(R.id.registered_getcoed_tv)
    TextView registered_getcoed_tv;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    private String CODE = "";
    Toastor toasr;
    private int type = -1;
    Map<String, String> map;
    private CountDownTimer timer;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map = new HashMap<>();
        toasr = new Toastor(this);
        type = getIntent().getIntExtra("type", -1);
        initToolbar();
        initView();
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                registered_getcoed_tv.setText(millisUntilFinished / 1000 + "s后重新发送短信");
            }

            @Override
            public void onFinish() {
                registered_getcoed_tv.setText("获取短信验证码");
                registered_getcoed_tv.setEnabled(true);
            }
        };

    }

    private void initToolbar() {
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        if (type == 2) {
            tvToolbarTitle.setText(getResources().getString(R.string.register_user));
            registerVerificationLl.setVisibility(View.GONE);
        } else if (type == 1) {
            tvToolbarTitle.setText(getResources().getString(R.string.register_doctor));
            registerVerificationLl.setVisibility(View.VISIBLE);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(getResources().getString(R.string.register_doctor_verification));
        //设置字体大小
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(50);
        //设置需要改变字体大小的位置
        spannableStringBuilder.setSpan(absoluteSizeSpan, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //设置字体颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#333333"));
        //设置需要改变字体颜色的位置
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        registerVerificationTv.setText(spannableStringBuilder);

    }

    @OnClick({R.id.registered_getcoed_tv, R.id.register_add_iv4, R.id.register_complete_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registered_getcoed_tv:

                String phone = registered_phone_et.getText().toString().trim();
                if (phone.length() == 11) {
                    map.clear();
                    map.put("type", "0");
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

                break;
            case R.id.register_add_iv4:
                break;
            case R.id.register_complete_tv:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String psw = registeredPswEt.getText().toString().trim();
        String psw2 = registeredPsw2Et.getText().toString().trim();
        String phone = registered_phone_et.getText().toString().trim();
        String name = registeredNameEt.getText().toString().trim();
        String code = registeredCoedEt.getText().toString().trim();
        if (phone.length() == 11) {
            if (CODE.equals(code)) {
                if (psw.length() >= 6 && psw.length() <= 16) {
                    if (psw.equals(psw2)) {
                        if (name.length() > 0) {
                            if (type == 2) {
                                //用户注册
                                map.clear();
                                map.put("phoneNumber", phone);
                                map.put("passWord", MD5.getMD5(psw));
                                map.put("flag", type + "");
                                map.put("nickName", name);

                                NetworkRequests.GetRequests(this, Constant.REGISTER, map, new JsonDataReturnListener() {
                                    @Override
                                    public void jsonListener(JSONObject jsonObject) {
                                        Log.e("jsonListener", jsonObject.toString());

                                    }
                                });
                            } else {
                                //医生注册
                            }
                        } else
                            toasr.showSingletonToast("昵称不能为空");
                    } else
                        toasr.showSingletonToast("两次密码输入不一致");
                } else
                    toasr.showSingletonToast("密码长度不得低于6位或大于十六位");
            } else
                toasr.showSingletonToast("验证码输入错误");
        } else {
            toasr.showSingletonToast("手机号码长度不正确");
        }
      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(registeredNameEt.getText().toString().trim(), registeredPswEt.getText().toString().trim());
                } catch (HyphenateException e) {
                    e.printStackTrace();

                    Log.d("HyphenateException", e.getMessage());

                }
            }
        }).start();*/
    }
}
