package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class InstallActivity extends BaseActivity {

    @BindView(R.id.toolbar_left_white_iv)
    ImageView toolbarLeftWhiteIv;
    @BindView(R.id.tv_toolbar_white_title)
    TextView tvToolbarWhiteTitle;

    @Override
    protected int getContentView() {
        return R.layout.activity_install;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toolbarLeftWhiteIv.setVisibility(View.VISIBLE);
        tvToolbarWhiteTitle.setText(getResources().getString(R.string.me_install));
    }


    @OnClick({R.id.toolbar_left_white_iv, R.id.install_change_psw, R.id.install_suggest, R.id.install_about, R.id.install_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_white_iv:
                finish();
                break;
            case R.id.install_change_psw:
                startActivity(new Intent(this, ForgetPasswordActivity.class).putExtra("type", 1));
                break;
            case R.id.install_suggest:
                startActivity(new Intent(this, SuggestActivity.class));
                break;
            case R.id.install_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.install_out:
                MyApplication.newInstance().outLogin();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}
