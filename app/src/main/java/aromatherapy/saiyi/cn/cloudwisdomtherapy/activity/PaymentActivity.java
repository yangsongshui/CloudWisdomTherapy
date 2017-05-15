package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;

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
    String prc;
    int prcice = 0;
    String id;
    boolean iszhifu = false;
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";

    @Override
    protected int getContentView() {
        return R.layout.activity_payment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map = new HashMap<>();
        toastor = new Toastor(this);
        orderNo = getIntent().getStringExtra("orderNo");
        prc = getIntent().getStringExtra("prcice");
        prcice = (int) (getIntent().getDoubleExtra("prcice", 0) * 100);
        tvToolbarTitle.setText("支付方式");
        toolbar_left_iv.setVisibility(View.VISIBLE);
        PingppLog.DEBUG = true;
        paymentTotalPriceTv.setText(prc);
    }

    String data;

    @OnClick({R.id.payment_zhifubao_ll, R.id.payment_weixin_ll, R.id.payment_tv, R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.payment_zhifubao_ll:
                paymentZhifubaoEb.setChecked(true);
                paymentWeixinRb.setChecked(false);
                iszhifu = false;
                break;
            case R.id.payment_weixin_ll:
                paymentZhifubaoEb.setChecked(false);
                paymentWeixinRb.setChecked(true);
                iszhifu = true;
                break;
            case R.id.payment_tv:
                if (iszhifu)
                    getData(CHANNEL_WECHAT);
                else
                    getData(CHANNEL_ALIPAY);
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;

        }
    }

    private void getData(final String type) {
        map.put("orderNo", orderNo);
        map.put("payType", type);
        NetworkRequests.getInstance().initViw(this).GetRequests(Constant.CREATECHARGE, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("PaymentActivity", jsonObject.toString());
                if (jsonObject.optInt("resCode") == 0) {
                    String data = jsonObject.optJSONObject("resBody").optJSONObject("charge").toString();
                    if (data == null) {
                        toastor.showSingletonToast("支付请求发起错误，请重试");
                        return;
                    }
                    Pingpp.createPayment(PaymentActivity.this, data);
                    id = jsonObject.optJSONObject("resBody").optJSONObject("charge").optString("id");
                }


            }
        });


    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                if (result.equals("success")) {
                    toastor.showSingletonToast("付款成功");
                    map.clear();
                    map.put("orderNo", orderNo);
                    map.put("orderStatus", "1");
                    map.put("chargeId", id);
                    NetworkRequests.getInstance().initViw(this).GetRequests(Constant.UPDATEORDERSTATU, map, new JsonDataReturnListener() {
                        @Override
                        public void jsonListener(JSONObject jsonObject) {
                            if (jsonObject.optInt("resCode") == 0) {
                                finish();
                            }
                            Log.e("PaymentActivity", jsonObject.toString());

                        }
                    });
                    String id = data.getExtras().getString("id");
                    Log.e("PaymentActivity", id);

                } else if (result.equals("cancel")) {
                    toastor.showSingletonToast("交易取消");
                } else if (result.equals("invalid")) {
                    toastor.showSingletonToast("相关支付软件未安装");
                } else {
                    /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                    toastor.showSingletonToast("交易失败");
                    String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                    String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                    //showMsg(result, errorMsg, extraMsg);
                }


            }
        }
    }

}
