package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.AftermarketAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Address;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.NestFullListView;
import butterknife.BindView;

public class AftermarketActivity extends BaseActivity implements NestFullListView.OnItemClickListener, OnItemClickListener {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.cstFullShowListView)
    NestFullListView aftermarketListRv;

    AftermarketAdapter adapter;
    List<Indent> mList;
    Map<String, String> map;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_aftermarket;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        map = new HashMap<>();
        toastor = new Toastor(this);
        tvToolbarTitle.setText(getResources().getString(R.string.aftermarket_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new AftermarketAdapter(R.layout.aftermarket_item, mList, this);
        aftermarketListRv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        aftermarketListRv.setOnItemClickListener(this);
    }

    private void getIndent() {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("orderStatu", "8");
        map.put("type", "1");
         NetworkRequests.getInstance().initViw(this).GetRequests(Constant.FINDALLORDER, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    aftermarketListRv.setVisibility(View.VISIBLE);
                    getItem(jsonObject.optJSONObject("resBody").optJSONArray("list"));
                } else {
                    mList.clear();
                    aftermarketListRv.setVisibility(View.GONE);
                    aftermarketListRv.updateUI();
                }
                Log.e("AftermarketActivity", jsonObject.toString());

            }
        });
    }

    private void getItem(JSONArray jsonArray) {
        mList.clear();
        if (jsonArray.length() > 0)
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                JSONObject adressDetail = jsonObject.optJSONObject("adressDetail");
                JSONArray commodityDetail = jsonObject.optJSONArray("commodityDetail");
                JSONObject returnMsg = jsonObject.optJSONObject("returnMsg");
                Log.e("returnMsg", returnMsg.toString());
                List<Mall> malls = new ArrayList<>();

                Indent indent = new Indent();
                for (int r = 0; r < commodityDetail.length(); r++) {
                    Mall mall = new Mall();
                    JSONObject commodity = commodityDetail.optJSONObject(r);
                    JSONObject jsonMall = commodity.optJSONObject("commodityDetail");
                    if (jsonMall.optString("type").equals("0")) {
                        mall.setType("美白牙齿");
                    } else if (jsonMall.optString("type").equals("1")) {
                        mall.setType("妇儿保健");
                    } else if (jsonMall.optString("type").equals("2")) {
                        mall.setType("疼痛健康");
                    } else if (jsonMall.optString("type").equals("3")) {
                        mall.setType("养生家居");
                    }
                    mall.setName(jsonMall.optString("commodityName"));
                    mall.setPrice(jsonMall.optString("discountPrice"));
                    mall.setPurchase_price(jsonMall.optString("originalPrice"));
                    mall.setPicture(jsonMall.optString("commodityPic"));
                    mall.setStandard(jsonMall.optString("specifications"));
                    mall.setProductionFactory(jsonMall.optString("productionFactory"));
                    mall.setDescribe(jsonMall.optString("discountMsg"));


                    mall.setNum(commodity.optString("commodityNum"));
                    malls.add(mall);
                }

                //获取地址信息
                Address address = new Address();
                address.setName(adressDetail.optString("consignee"));
                address.setPhone(adressDetail.optString("consigneePhone"));
                address.setShi(adressDetail.optString("shi"));
                address.setSheng(adressDetail.optString("sheng"));
                address.setAddress(adressDetail.optString("consigneeAddress"));
                address.setMail(adressDetail.optString("mail"));

                indent.setRecommender(jsonObject.optString("recommender"));
                indent.setState(jsonObject.optInt("orderStatus"));
                indent.setTotal(jsonObject.optString("orderPrice"));
                indent.setOrderNo(jsonObject.optString("orderNo"));
                indent.setDate(jsonObject.optString("date"));
                indent.setLogisticsCompany(jsonObject.optString("logisticsCompany"));
                indent.setLogisticsNo(jsonObject.optString("logisticsNo"));

                indent.setReimburse(returnMsg.optInt("returnMoney") + "");
                indent.setReturnReson(returnMsg.optString("returnReson"));
                indent.setRelogisticsCompany(returnMsg.optString("logisticsCompany"));
                indent.setRelogisticsNo(returnMsg.optString("logisticsNo"));
                if (returnMsg.optString("returnType").equals("0"))
                    indent.setType("退货退款");
                else
                    indent.setType("退款");

                indent.setMalls(malls);
                indent.setAddress(address);
                mList.add(indent);
            }

        aftermarketListRv.updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIndent();
    }

    @Override
    public void onItemClick(NestFullListView parent, View view, int position) {
        startActivity(new Intent(this, ReturnInfoActivity.class).putExtra("indent", mList.get(position)));
    }

    @Override
    public void onItemClick(View holder, int position) {
        startActivity(new Intent(this, ReturnInfoActivity.class).putExtra("indent", mList.get(position)));
    }
}
