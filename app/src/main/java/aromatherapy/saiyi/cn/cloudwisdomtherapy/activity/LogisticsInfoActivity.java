package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class LogisticsInfoActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.logistic_info_firm_et)
    EditText logisticInfoFirmEt;
    @BindView(R.id.logistic_info_numbers_et)
    EditText logisticInfoNumbersEt;
    @BindView(R.id.logistic_info_phone_et)
    EditText logisticInfoPhoneEt;
    @BindView(R.id.logistic_info_explain_et)
    EditText logisticInfoExplainEt;

    @Override
    protected int getContentView() {
        return R.layout.activity_logistics_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tv_toolbar_title.setText(getResources().getString(R.string.after_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick({R.id.logistics_button_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logistics_button_tv:
                startActivity(new Intent(this, LogisticsActivity.class));
                break;

        }
    }
}
