package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
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

    MallSharePopuoWindow mallSharePopuoWindow;
    MallGoodsPopupWindow mallGoodsPopupWindow;
    Mall mall;
    boolean isJoin = false;
    Map<String, String> mMap;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_mall;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mMap = new HashMap<>();
        mall = (Mall) getIntent().getSerializableExtra("mall");
        toastor = new Toastor(this);
        mallSharePopuoWindow = new MallSharePopuoWindow(this, this);
        mallGoodsPopupWindow = new MallGoodsPopupWindow(this, this);
        initView();

    }

    @OnClick({R.id.mall_service_tv, R.id.mall_share_tv, R.id.mall_join_tv, R.id.mall_purchase_tv, R.id.mall_back_iv})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.mall_service_tv:
                break;
            case R.id.mall_share_tv:
                //分享
                mallSharePopuoWindow.showAtLocation(MallActivity.this.findViewById(R.id.activity_mall), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.mall_join_tv:
                //加入购物车
                isJoin = false;
                mallGoodsPopupWindow.showAtLocation(MallActivity.this.findViewById(R.id.activity_mall), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.mall_purchase_tv:
                //立即购买
                isJoin = true;
                mallGoodsPopupWindow.showAtLocation(MallActivity.this.findViewById(R.id.activity_mall), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.mall_back_iv:
                finish();
                break;
        }
    }

    private void initView() {
        MyApplication.newInstance().getmImageLoader().get(mall.getPicture(), mallGoodsIv);
        mallNameTv.setText(mall.getName());

        mallFactoryTv.setText(mall.getProductionFactory());
        mallMoneyTv.setText(mall.getPrice());
        mallOriginalTv.setText(mall.getPurchase_price());
        mallIntroduceTv.setText(mall.getDescribe());
        MyApplication.newInstance().getmImageLoader().get(mall.getPicture(), mallGoodsPopupWindow.getPop_pic_iv());
        mallGoodsPopupWindow.getPop_standard_tv().setText(mall.getStandard());
        mallGoodsPopupWindow.getPop_rmb_tv().setText(mall.getPrice());
        mallGoodsPopupWindow.getPop_type_tv().setText(mall.getGoogsType());
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
                mallGoodsPopupWindow.dismiss();
                if (isJoin) {
                    mall.setNum(mallGoodsPopupWindow.getNum() + "");
                    startActivity(new Intent(this, ConfirmActivity.class).putExtra("mall", mall));
                } else
                    addCart();
                break;
        }
    }

    private void addCart() {
        mMap.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        mMap.put("phoneNumber", phone);
        mMap.put("commodityNo", mall.getID());
        mMap.put("num", mallGoodsPopupWindow.getNum() + "");
        mMap.put("price", (Double.parseDouble(mall.getPrice()) * mallGoodsPopupWindow.getNum()) + "");
        NetworkRequests.GetRequests(this, Constant.ADDSHOPPINGCAR, mMap, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("jsonListener", jsonObject.toString());
            }
        });
    }
}
