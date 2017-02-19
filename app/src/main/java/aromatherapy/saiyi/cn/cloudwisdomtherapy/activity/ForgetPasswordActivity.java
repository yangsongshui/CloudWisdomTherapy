package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
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
    int type = 0;
    @BindView(R.id.forget_phone_tv)
    TextView forgetPhoneTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initToolbar();
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
                break;
            case R.id.forget_complete_tv:
                //点击完成
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
