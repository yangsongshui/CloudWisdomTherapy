package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MallGoodsPopupWindow;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MallSharePopuoWindow;
import butterknife.BindView;
import butterknife.OnClick;

public class MallActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.mall_goods_iv)
    ImageView mallGoodsIv;
    @BindView(R.id.mall_name_tv)
    TextView mallNameTv;
    @BindView(R.id.mall_sold_tv)
    TextView mallSoldTv;
    @BindView(R.id.mall_inventory_tc)
    TextView mallInventoryTc;
    @BindView(R.id.mall_money_tv)
    TextView mallMoneyTv;
    @BindView(R.id.mall_original_tv)
    TextView mallOriginalTv;
    @BindView(R.id.mall_factory_tv)
    TextView mallFactoryTv;
    @BindView(R.id.mall_introduce_tv)
    TextView mallIntroduceTv;
    @BindView(R.id.mall_describe_tv)
    TextView mallDescribeTv;
    MallSharePopuoWindow mallSharePopuoWindow;
    MallGoodsPopupWindow mallGoodsPopupWindow;

    @Override
    protected int getContentView() {
        return R.layout.activity_mall;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mallSharePopuoWindow = new MallSharePopuoWindow(this, this);
        mallGoodsPopupWindow = new MallGoodsPopupWindow(this, this);
    }

    @OnClick({R.id.mall_service_tv, R.id.mall_share_tv, R.id.mall_join_tv, R.id.mall_purchase_tv, R.id.mall_back_iv})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.mall_service_tv:
                break;
            case R.id.mall_share_tv:
                //显示窗口
                mallSharePopuoWindow.showAtLocation(MallActivity.this.findViewById(R.id.activity_mall), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.mall_join_tv:
                //显示窗口
                mallGoodsPopupWindow.showAtLocation(MallActivity.this.findViewById(R.id.activity_mall), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.mall_purchase_tv:
                //显示窗口
                mallGoodsPopupWindow.showAtLocation(MallActivity.this.findViewById(R.id.activity_mall), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.mall_back_iv:
                finish();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mall_QQ_tv:
                break;
            case R.id.mall_weixin_iv:
                break;
            case R.id.mall_friend_tv:
                break;
            case R.id.pop_confirm_tv:
                startActivity(new Intent(this,ConfirmActivity.class));
                break;
        }
    }
}
