package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;

    @BindView(R.id.payment_zhifubao_eb)
    RadioButton paymentZhifubaoEb;
    @BindView(R.id.payment_weixin_rb)
    RadioButton paymentWeixinRb;
    @BindView(R.id.payment_total_price_tv)
    TextView paymentTotalPriceTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_payment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvToolbarTitle.setText("支付方式");
        toolbar_left_iv.setVisibility(View.VISIBLE);
    }



    @OnClick({R.id.payment_zhifubao_ll, R.id.payment_weixin_ll, R.id.payment_tv,R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.payment_zhifubao_ll:
                paymentZhifubaoEb.setChecked(true);
                paymentWeixinRb.setChecked(false);
                break;
            case R.id.payment_weixin_ll:
                paymentZhifubaoEb.setChecked(false);
                paymentWeixinRb.setChecked(true);
                break;
            case R.id.payment_tv:
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;

        }
    }
}
