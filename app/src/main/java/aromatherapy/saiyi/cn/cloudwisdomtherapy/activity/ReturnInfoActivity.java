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

public class ReturnInfoActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;

    @BindView(R.id.returnInfo_state_tv)
    TextView returnInfoStateTv;
    @BindView(R.id.returnInfo_examine_tv)
    TextView returnInfoExamineTv;
    @BindView(R.id.returnInfo_type_tv)
    TextView returnInfoTypeTv;
    @BindView(R.id.returnInfo_money_tv)
    TextView returnInfoMoneyTv;
    @BindView(R.id.returnInfo_reason_tv)
    TextView returnInfoReasonTv;
    @BindView(R.id.returnInfo_explain_tv)
    TextView returnInfoExplainTv;
    @BindView(R.id.returnInfo_time_tv)
    TextView returnInfoTimeTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_return_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tv_toolbar_title.setText(getResources().getString(R.string.return_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.returnInfo_examine_tv, R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.returnInfo_examine_tv:
                startActivity(new Intent(this,LogisticsActivity.class));
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
        }
    }
}
