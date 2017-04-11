package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    String type="";

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initToolbar();
    }

    private void initToolbar() {
        tvToolbarTitle.setText(getResources().getString(R.string.login_register));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        type = getIntent().getStringExtra("type");
    }


    @OnClick({R.id.register_user_iv, R.id.register_doctor_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_user_iv:
                startActivity(new Intent(this, UserRegisterActivity.class).putExtra("type", type+2));
                finish();
                break;
            case R.id.register_doctor_iv:
                startActivity(new Intent(this, UserRegisterActivity.class).putExtra("type", type+1));
                finish();
                break;
        }

    }
}
