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

public class ConfirmActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.confirm_username_tv)
    TextView confirmUsernameTv;//用户名
    @BindView(R.id.confirm_phone_tv)
    TextView confirmPhoneTv;//手机号
    @BindView(R.id.confirm_address_tv)
    TextView confirmAddressTv;  //地址
    @BindView(R.id.confirm_pic_iv)
    ImageView confirmPicIv;//商品图片
    @BindView(R.id.confirm_name_tv)
    TextView confirmNameTv;//商品名字
    @BindView(R.id.confirm_type_tv)
    TextView confirmTypeTv;//商品类型
    @BindView(R.id.confirm_standard_tv)
    TextView confirmStandardTv;//商品规格
    @BindView(R.id.confirm_purchase_price_tv)
    TextView confirmPurchasePriceTv;//商品价格
    @BindView(R.id.confirm_price_tv)
    TextView confirmPriceTv;//商品原价
    @BindView(R.id.confirm_num_tv)
    TextView confirmNumTv;//购买数量
    @BindView(R.id.confirm_total_tv)
    TextView confirmTotalTv; //总价
    @BindView(R.id.confirm_tuijianren_et)
    EditText confirmTuijianrenEt;//推荐人号码
    @BindView(R.id.confirm_total_price_tv)
    TextView confirmTotalPriceTv;  //折后总价

    @Override
    protected int getContentView() {
        return R.layout.activity_confirm;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvToolbarTitle.setText("确认订单");
        toolbar_left_iv.setVisibility(View.VISIBLE);

    }


    @OnClick({R.id.confirm_user_rl, R.id.confirm_payment_tv, R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_user_rl:
                break;
            case R.id.confirm_payment_tv:
                startActivity(new Intent(this,PaymentActivity.class));
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
        }
    }
}
