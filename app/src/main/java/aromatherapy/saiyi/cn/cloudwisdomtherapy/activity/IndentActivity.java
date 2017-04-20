package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.IndentAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnViewClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Address;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.NestFullListView;
import butterknife.BindView;
import butterknife.OnClick;

public class IndentActivity extends BaseActivity implements OnViewClickListener, OnItemClickListener, NestFullListView.OnItemClickListener {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.indent_cart_go_ll)
    LinearLayout indent_cart_go_ll;
    @BindView(R.id.cstFullShowListView)
    NestFullListView nestFullListView;
    IndentAdapter adapter;
    List<Indent> mList;
    Map<String, String> map;
    Toastor toastor;
    int type = -1;

    @Override
    protected int getContentView() {
        return R.layout.activity_indent;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTab();
        map = new HashMap<>();
        toastor = new Toastor(this);
        mList = new ArrayList<>();
        tvToolbarTitle.setText(getResources().getString(R.string.me_order));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initeListView();
    }

    private void initTab() {

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.indent_all)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.indent_obligation)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.indent_committed)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.indent_collect)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.indent_buy)));
        type = getIntent().getIntExtra("type", -1);
        if (type == -1 || type == 8) {
            tabLayout.getTabAt(0).select();
        } else {
            tabLayout.getTabAt((type + 1)).select();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                Log.i("选中了", tab.getPosition() + "");
                switch (tab.getPosition()) {
                    case 0:
                        getIndent("8");
                        type = 8;
                        break;
                    case 1:
                        getIndent("0");
                        type = 0;
                        break;
                    case 2:
                        getIndent("1");
                        type = 1;
                        break;
                    case 3:
                        getIndent("2");
                        type = 2;
                        break;
                    case 4:
                        getIndent("3");
                        type = 3;
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //上一次tab的逻辑
                Log.i("上一次选中", tab.getPosition() + "");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //上一次tab的逻辑
                Log.i("上一次选中", tab.getPosition() + "");
            }
        });

    }

    private void initeListView() {
        adapter = new IndentAdapter(R.layout.indent_item, mList);
        adapter.setListener(this);
        nestFullListView.setAdapter(adapter);
        nestFullListView.setOnItemClickListener(this);
        adapter.setOnItemClickListener(this);


    }

    @OnClick(R.id.indent_cart_go_tv)
    public void onClick() {
        finish();
    }


    private void getIndent(String type) {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("orderStatu", type);
        map.put("type", "0");
         NetworkRequests.getInstance().initViw(this).GetRequests(Constant.FINDALLORDER, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    indent_cart_go_ll.setVisibility(View.GONE);
                    nestFullListView.setVisibility(View.VISIBLE);
                    getItem(jsonObject.optJSONObject("resBody").optJSONArray("list"));
                } else {
                    mList.clear();
                    indent_cart_go_ll.setVisibility(View.VISIBLE);
                    nestFullListView.setVisibility(View.GONE);
                    nestFullListView.updateUI();
                }
                Log.e("IndentActivity", jsonObject.toString());

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
                indent.setLogisticsCompany(jsonObject.optString("logist icsCompany"));
                indent.setLogisticsNo(jsonObject.optString("logisticsNo"));
                Log.e("getItem", indent.getState() + "");
                indent.setMalls(malls);
                indent.setAddress(address);
                mList.add(indent);
            }

        nestFullListView.updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIndent(type + "");
    }


    @Override
    public void OnViewClick(View view, int position, int type) {
        if (type == 1) {
            //确认收货
            showSexDialog(position);
        } else if (type == 2) {
            //去付款
            startActivity(new Intent(IndentActivity.this, PaymentActivity.class).putExtra("orderNo", mList.get(position).getOrderNo()));

        } else if (type == 3) {
            //查询物流
            startActivity(new Intent(IndentActivity.this, LogisticsActivity.class).putExtra("logistics", mList.get(position).getLogisticsNo()));
        }
    }

    private void showSexDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndentActivity.this);
        builder.setTitle("提示");
        builder.setMessage("是否确认收货");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                affirm(position);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onItemClick(View holder, int position) {
        Log.e("onClick", position);
        startActivity(new Intent(this, DetailsActivity.class).putExtra("indent", mList.get(position)));
    }

    @Override
    public void onItemClick(NestFullListView parent, View view, int position) {
        startActivity(new Intent(this, DetailsActivity.class).putExtra("indent", mList.get(position)));
    }

    private void affirm(int position) {
        map.clear();
        map.put("orderNo", mList.get(position).getOrderNo());

        map.put("orderStatus", "3");
         NetworkRequests.getInstance().initViw(this).GetRequests(Constant.UPDATEORDERSTATU, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    getIndent(type + "");
                }
                toastor.showSingletonToast(jsonObject.optString("resMessage"));

            }
        });
    }
}
