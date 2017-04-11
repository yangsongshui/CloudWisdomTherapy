package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
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


    Map<String, String> map;
    Toastor toastor;
    String orderNo;

    @Override
    protected int getContentView() {
        return R.layout.activity_payment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map = new HashMap<>();
        toastor = new Toastor(this);
        orderNo = getIntent().getStringExtra("orderNo");
        tvToolbarTitle.setText("支付方式");
        toolbar_left_iv.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.payment_zhifubao_ll, R.id.payment_weixin_ll, R.id.payment_tv, R.id.toolbar_left_iv})
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
                payment();
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;

        }
    }

    private void payment() {
        map.clear();
        map.put("orderNo", orderNo);
        map.put("orderStatus", "1");
        NetworkRequests.GetRequests(this, Constant.UPDATEORDERSTATU, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    finish();
                }
                Log.e("PaymentActivity", jsonObject.toString());

            }
        });
    }
}
