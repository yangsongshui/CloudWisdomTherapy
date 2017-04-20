package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.NestFullListViewAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Address;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.NestFullListView;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.NestFullViewHolder;
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
    @BindView(R.id.details_rv)
    LinearLayout details_rv;

    @BindView(R.id.cstFullShowListView)
    NestFullListView nestFullListView;
    Indent indent;

    @Override
    protected int getContentView() {
        return R.layout.activity_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        indent = (Indent) getIntent().getSerializableExtra("indent");
        toolbar_left_iv.setVisibility(View.VISIBLE);
        if (indent.getState() == 0) {
            tv_toolbar_title.setText(getResources().getString(R.string.details_title2));
            detailsOutTv.setVisibility(View.GONE);
            detailsLogisticsRl.setVisibility(View.GONE);
            details_total_price_tv.setText(indent.getTotal());
        } else if (indent.getState() == 1) {
            tv_toolbar_title.setText(getResources().getString(R.string.details_title));
            detailsLogisticsRl.setVisibility(View.GONE);
            details_rv.setVisibility(View.GONE);
        } else {
            tv_toolbar_title.setText(getResources().getString(R.string.details_title));
            details_rv.setVisibility(View.GONE);

        }
        if (indent.getState() == 3) {
            detailsOutTv.setVisibility(View.GONE);
        }
        initdata();
        initView();
    }


    @OnClick({R.id.details_logistics_rl, R.id.details_user_rl, R.id.details_payment_tv, R.id.toolbar_left_iv, R.id.details_out_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.details_logistics_rl:
                startActivity(new Intent(this, LogisticsActivity.class).putExtra("logistics", indent.getLogisticsNo()));
                break;
            case R.id.details_user_rl:
                break;
            case R.id.details_payment_tv:
                startActivity(new Intent(this, PaymentActivity.class).putExtra("orderNo", indent.getOrderNo()));
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
            case R.id.details_out_tv:
                startActivity(new Intent(this, AfterSalesActivity.class).putExtra("indent", indent));
                finish();
                break;
        }
    }

    private void initView() {
        Address address = indent.getAddress();
        details_username_tv.setText(address.getName());
        detailsPhoneTv.setText(address.getPhone());
        detailsOrderTv.setText(indent.getOrderNo());
        detailsTimeTv.setText(indent.getDate());
        detailsTotalTv.setText(indent.getTotal());
        detailsNumberTv.setText(indent.getLogisticsNo());
        detailsLogisticsTv.setText(indent.getLogisticsCompany());

        if (indent.getRecommender() != null && indent.getRecommender().length() == 11) {
            detailsRecommenderTv.setText(indent.getRecommender());
            detailsDiscountTv.setText("用户推荐九折优惠！");
        } else {
            detailsRecommenderTv.setText("");
            detailsDiscountTv.setText("");
        }
        String city = address.getSheng();
        if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
            detailsAddressTv.setText(address.getShi() + address.getQu() + address.getAddress());
        } else {
            detailsAddressTv.setText(city + address.getShi() + address.getQu() + address.getAddress());
        }
    }

    private void initdata() {
        nestFullListView.setAdapter(new NestFullListViewAdapter<Mall>(R.layout.indent_mall_item, indent.getMalls()) {
            @Override
            public void onBind(int pos, Mall mall, NestFullViewHolder holder) {
                ImageView indent_item_pic_iv = holder.getView(R.id.indent_item_pic_iv);
                MyApplication.newInstance().getmImageLoader().load(mall.getPicture()).into(indent_item_pic_iv);
                TextView intent_rmb_tv = holder.getView(R.id.intent_rmb_tv);
                TextView indent_item_purchase_price_tv = holder.getView(R.id.indent_item_purchase_price_tv);
                holder.getView(R.id.indent_item_num_tv);
                indent_item_purchase_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                intent_rmb_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                indent_item_purchase_price_tv.setText(mall.getPurchase_price());
                holder.setText(R.id.indent_item_name_tv, mall.getName());
                holder.setText(R.id.indent_item_price_tv, mall.getPrice());
                holder.setText(R.id.indent_item_type_tv, mall.getType());
                holder.setText(R.id.indent_item_standard_tv, mall.getStandard());
            }
        });
    }
}
