package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import butterknife.BindView;
import butterknife.OnClick;

public class ReturnInfoActivity extends BaseActivity {
    private static final int RESULT = 2;
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
    Indent indent;

    @Override
    protected int getContentView() {
        return R.layout.activity_return_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tv_toolbar_title.setText(getResources().getString(R.string.return_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        indent = (Indent) getIntent().getSerializableExtra("indent");
        initView();
    }

    @OnClick({R.id.returnInfo_examine_tv, R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.returnInfo_examine_tv:
                if (indent.getRelogisticsNo() != null && indent.getRelogisticsNo().trim().length() > 0)
                    startActivity(new Intent(this, LogisticsActivity.class).putExtra("logistics", indent.getRelogisticsNo()));
                else
                    startActivityForResult(new Intent(this, LogisticsInfoActivity.class).putExtra("logistics", indent.getOrderNo()), RESULT);
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
        }
    }

    private void initView() {
        if (indent.getState() == 4) {
            //退货中
            if (indent.getRelogisticsNo() != null && indent.getRelogisticsNo().trim().length() > 0) {
                returnInfoStateTv.setText(this.getString(R.string.aftermarket_state4));
                returnInfoExamineTv.setText(this.getString(R.string.returnInfo_examine));
            } else {
                returnInfoStateTv.setText(this.getString(R.string.aftermarket_state1));
                returnInfoExamineTv.setText(this.getString(R.string.returnInfo_examine2));
            }
        } else if (indent.getState() == 5) {
            //已确认收货
            returnInfoStateTv.setText(this.getString(R.string.aftermarket_state3));
            if (indent.getRelogisticsNo() != null && indent.getRelogisticsNo().trim().length() > 0)
                returnInfoExamineTv.setText(this.getString(R.string.returnInfo_examine));
            else
                returnInfoExamineTv.setVisibility(View.GONE);
        } else if (indent.getState() == 999) {
            //等待客户确认
            returnInfoStateTv.setText(this.getString(R.string.aftermarket_state2));
            returnInfoExamineTv.setVisibility(View.GONE);
        }
        returnInfoTypeTv.setText(indent.getType());
        returnInfoMoneyTv.setText(indent.getReimburse());
        returnInfoReasonTv.setText(indent.getReturnReson());
        returnInfoExplainTv.setText(indent.getRemark());
        returnInfoTimeTv.setText(indent.getDate());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT) {
            indent.setRelogisticsNo(data.getStringExtra("logisticsNo"));
            indent.setRelogisticsCompany(data.getStringExtra("logisticsCompany"));
            returnInfoStateTv.setText(this.getString(R.string.aftermarket_state4));
            returnInfoExamineTv.setText(this.getString(R.string.returnInfo_examine));
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
