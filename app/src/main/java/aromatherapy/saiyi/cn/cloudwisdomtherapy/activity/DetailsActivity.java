package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import butterknife.BindView;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity {
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;

    @BindView(R.id.details_logistics_tv)
    TextView detailsLogisticsTv;//快递名称
    @BindView(R.id.details_number_tv)
    TextView detailsNumberTv;   //物流单号
    @BindView(R.id.details_logistics_rl)
    RelativeLayout detailsLogisticsRl;//快递信息栏
    @BindView(R.id.details_username_tv)
    TextView details_username_tv;//收件人名
    @BindView(R.id.details_phone_tv)
    TextView detailsPhoneTv;//收件人电话
    @BindView(R.id.details_address_tv)
    TextView detailsAddressTv;  //地址信息
    @BindView(R.id.details_user_rl)
    RelativeLayout detailsUserRl;//用户信息栏

    @BindView(R.id.details_pic_iv)
    ImageView details_pic_iv;//商品图片
    @BindView(R.id.details_name_tv)
    TextView details_name_tv;//商品名称
    @BindView(R.id.details_type_tv)
    TextView details_type_tv;//类型
    @BindView(R.id.details_standard_tv)
    TextView details_standard_tv;//规格
    @BindView(R.id.details_rmb_tv)
    TextView detailsRmbTv;//原价符号
    @BindView(R.id.details_purchase_price_tv)
    TextView detailsPurchasePriceTv;//原价
    @BindView(R.id.details_price_tv)
    TextView detailsPriceTv;//现价
    @BindView(R.id.details_num_tv)
    TextView detailsNumTv;//数量
    @BindView(R.id.details_out_tv)
    TextView detailsOutTv;//退款/退货
    @BindView(R.id.details_total_tv)
    TextView detailsTotalTv;//合计价格
    @BindView(R.id.details_recommender_tv)
    TextView detailsRecommenderTv;//推荐人号码
    @BindView(R.id.details_order_tv)
    TextView detailsOrderTv;//订单编号
    @BindView(R.id.details_time_tv)
    TextView detailsTimeTv;//时间
    @BindView(R.id.details_discount_tv)
    TextView detailsDiscountTv; //优惠信息
    @BindView(R.id.details_total_price_tv)
    TextView details_total_price_tv;//总价
    @BindView(R.id.details_h_tv)
    TextView details_h_tv;//剩余小时
    @BindView(R.id.details_m_tv)
    TextView details_m_tv;//剩余分钟
    @BindView(R.id.details_payment_tv)
    TextView detailsPaymentTv;

    Indent indent;

    @Override
    protected int getContentView() {
        return R.layout.activity_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        indent = (Indent) getIntent().getSerializableExtra("indent");
        toolbar_left_iv.setVisibility(View.VISIBLE);
        if (indent.getState()==1) {
            tv_toolbar_title.setText(getResources().getString(R.string.details_title2));
         detailsOutTv.setVisibility(View.GONE);
        } else {
            tv_toolbar_title.setText(getResources().getString(R.string.details_title));
        }
    }


    @OnClick({R.id.details_logistics_rl, R.id.details_user_rl, R.id.details_payment_tv, R.id.toolbar_left_iv, R.id.details_out_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.details_logistics_rl:
                break;
            case R.id.details_user_rl:
                break;
            case R.id.details_payment_tv:
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
            case R.id.details_out_tv:
                startActivity(new Intent(this, AfterSalesActivity.class));
                break;
        }
    }
}
