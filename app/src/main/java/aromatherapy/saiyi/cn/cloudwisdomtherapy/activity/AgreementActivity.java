package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;

public class AgreementActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;

    @Override
    protected int getContentView() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvToolbarTitle.setText("用户协议");
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
