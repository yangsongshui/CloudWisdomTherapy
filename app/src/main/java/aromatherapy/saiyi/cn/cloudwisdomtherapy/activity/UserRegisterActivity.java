package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.graphics.Color;
import android.os.Bundle;
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

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;

    Toastor toasr;
    private int type = -1;
    Map<String, String> map;

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
        if (type == 0) {
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
                    NetworkRequests.GetRequests(this, Constant.GETIDENTIFY, map, new JsonDataReturnListener() {
                        @Override
                        public void jsonListener(JSONObject jsonObject) {
                            Log.e("User", jsonObject.toString());
                        }
                    });
                }else {
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
