package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class ReturnGoodsActivity extends BaseActivity {

    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.return_cause_msg_tv)
    TextView returnCauseMsgTv;
    @BindView(R.id.return_money_et)
    EditText returnMoneyEt;
    @BindView(R.id.return_money_tv)
    TextView returnMoneyTv;
    @BindView(R.id.return_input_et)
    EditText returnInputEt;
    @BindView(R.id.return_add_iv1)
    ImageView returnAddIv1;
    @BindView(R.id.return_add_iv2)
    ImageView returnAddIv2;
    @BindView(R.id.return_add_iv3)
    ImageView returnAddIv3;
    @BindView(R.id.return_add_iv4)
    ImageView returnAddIv4;

    @Override
    protected int getContentView() {
        return R.layout.activity_return_goods;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tv_toolbar_title.setText(getResources().getString(R.string.return_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.return_cause_msg_ll, R.id.return_add_iv1, R.id.return_add_iv2, R.id.return_add_iv3, R.id.return_add_iv4,
            R.id.return_button_tv, R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_cause_msg_ll:
                break;
            case R.id.return_add_iv1:
                break;
            case R.id.return_add_iv2:
                break;
            case R.id.return_add_iv3:
                break;
            case R.id.return_add_iv4:
                break;
            case R.id.return_button_tv:
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
        }
    }
}
