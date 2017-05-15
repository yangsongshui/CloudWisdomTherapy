package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.ConfirmActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.MainActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.ShoppingCartAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemCheckListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Commodity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class ShoppingCartFragment extends BaseFragment implements OnItemCheckListener, View.OnClickListener {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tv_toolbar_right;
    @BindView(R.id.shopping_cart_null_ll)
    LinearLayout shoppingCartNullLl;
    @BindView(R.id.shopping_cart_all_cb)
    CheckBox shoppingCartAllCb;
    @BindView(R.id.shopping_cart_price_tv)
    TextView shoppingCartPriceTv;//选中总价
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.shopping_cart_goods_rv)
    RecyclerView shoppingCartGoodsRv;
    @BindView(R.id.shopping_cart_settlement_tv)
    TextView shopping_cart_settlement_tv;
    @BindView(R.id.shopping_cart_mall_cb)
    CheckBox shopping_cart_mall_cb;
    @BindView(R.id.shopping_cart_all_rl)
    RelativeLayout shopping_cart_all_rl;
    private List<Commodity> mList;
    private ShoppingCartAdapter adapter;
    private boolean complete = true;
    Map mMap;
    Toastor toastor;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mMap = new HashMap();
        toastor = new Toastor(getActivity());
        tvToolbarTitle.setText(getResources().getString(R.string.tab_4));
        tv_toolbar_right.setVisibility(View.VISIBLE);
        tv_toolbar_right.setText(getResources().getString(R.string.shopping_cart_edit));
        mList = new ArrayList<>();
        shoppingCartAllCb.setOnClickListener(this);
        shopping_cart_mall_cb.setOnClickListener(this);
        shoppingCartAllCb.setText(getResources().getString(R.string.shopping_cart_select));
        initList();
    }

    @Override
    protected int getContentView() {

        return R.layout.fragment_shopping_cart;


    }


    @OnClick({R.id.shopping_cart_go_tv, R.id.shopping_cart_null_ll, R.id.shopping_cart_settlement_tv, R.id.tv_toolbar_right})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.shopping_cart_go_tv:
                //去逛逛按钮
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setFragment(1);
                break;
            case R.id.shopping_cart_settlement_tv:
                //结算按钮
                if (complete) {
                    completeCart();

                } else {
                    deleteCart();

                }

                break;
            case R.id.tv_toolbar_right:
                //编辑按钮

                if (complete) {
                    tv_toolbar_right.setText(getResources().getString(R.string.shopping_cart_complete));
                    shopping_cart_settlement_tv.setText(getResources().getString(R.string.shopping_cart_delete));
                    adapter.setConceal(complete);
                    complete = false;

                } else {
                    tv_toolbar_right.setText(getResources().getString(R.string.shopping_cart_edit));
                    shopping_cart_settlement_tv.setText(getResources().getString(R.string.shopping_cart_settlement));
                    adapter.setConceal(complete);
                    complete = true;
                    update();
                }

                break;
        }
    }

    private void initList() {
        adapter = new ShoppingCartAdapter(getActivity(), mList);
        adapter.setOnItemCheckListener(this);
        shoppingCartGoodsRv.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shoppingCartGoodsRv.setLayoutManager(layoutManager);
        shoppingCartGoodsRv.setAdapter(adapter);
    }

    private void traverse(boolean isChecked) {
        double price = 0.00;
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setChoice(isChecked);
            if (isChecked) {
                int num = Integer.parseInt(mList.get(i).getNum());
                price += Double.parseDouble(mList.get(i).getPrice()) * num;
            }
        }
        adapter.setItems(mList);
        shoppingCartPriceTv.setText(price + "");

    }

    @Override
    public void onitemCheck(View view, boolean isCheck, int position) {

        mList.get(position).setChoice(isCheck);
        Log.e("onitemCheck", position + "  " + isCheck);
        isChecked();
        getPrice();

    }

    @Override
    public void onNumCheck(View view, int num, int position) {
        mList.get(position).setNum(num + "");
    }

    boolean isAll = false;

    private void isChecked() {
        double price = 0.00;
        for (int i = 0; i < mList.size(); i++) {
            Log.e("isChecked", mList.get(i).isChoice());

            if (mList.get(i).isChoice()) {
                isChecked = false;
                isAll = true;
            } else {
                isAll = false;
                isChecked = true;
                break;
            }
            Log.e("price", price + "=price");
        }

        Log.e("isChecked", isAll);
        if (isAll) {
            shopping_cart_mall_cb.setChecked(true);
            shoppingCartAllCb.setChecked(true);
        } else {
            Log.e("isChecked", isAll);
            shopping_cart_mall_cb.setChecked(false);
            shoppingCartAllCb.setChecked(false);
        }
    }

    private void getPrice() {
        double price = 0.00;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isChoice()) {
                int num = Integer.parseInt(mList.get(i).getNum());
                price += Double.parseDouble(mList.get(i).getPrice()) * num;
            }
        }
        shoppingCartPriceTv.setText(price + "");

    }

    boolean isChecked = true;

    @Override
    public void onClick(View v) {
        traverse(isChecked);
        Log.e("onClick", isChecked);
        shopping_cart_mall_cb.setChecked(isChecked);
        shoppingCartAllCb.setChecked(isChecked);

        isChecked = !isChecked;
    }

    private void deleteCart() {
        mMap.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < mList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("commodityNo", mList.get(i).getID());
                jsonObject.put("phoneNumber", phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }

        mMap.put("list", jsonArray.toString().trim());
        Log.e("JSONObject", jsonArray.toString().trim());
       NetworkRequests.getInstance().initViw(getActivity()).GetRequests(Constant.DELETESHOPPINGCAR, mMap, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("jsonListener", jsonObject.toString());
                if (jsonObject.optInt("resCode") == 0) {
                    getCart();
                }
            }
        });
    }

    private void update() {
        mMap.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isChoice()) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("commodityNo", mList.get(i).getID());
                    jsonObject.put("phoneNumber", phone);
                    jsonObject.put("num", mList.get(i).getNum());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
            }
        }

        mMap.put("shoppingCarList", jsonArray.toString().trim());
        Log.e("JSONObject", jsonArray.toString().trim());
       NetworkRequests.getInstance().initViw(getActivity()).GetRequests( Constant.UPDATESHOPPINGCAR, mMap, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("jsonListener", jsonObject.toString());
                if (jsonObject.optInt("resCode") == 0) {
                    toastor.showSingletonToast("修改成功");
                }
            }
        });
    }

    private void getCart() {
        mMap.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        mMap.put("phoneNumber", phone);
       NetworkRequests.getInstance().initViw(getActivity()).GetRequests( Constant.FINDSHOPPINGCAR, mMap, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                getItem(jsonObject.optJSONObject("resBody").optJSONArray("lists"));
                Log.e("jsonListener", jsonObject.toString());
            }
        });
    }

    private void getItem(JSONArray jsonArray) {
        mList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            Commodity commodity = new Mall();
            commodity.setID(jsonObject.optString("commodityNo"));
            commodity.setName(jsonObject.optString("commodityName"));
            commodity.setPicture(jsonObject.optString("commodityPic"));
            commodity.setPurchase_price(jsonObject.optString("originalPrice"));
            commodity.setPrice(jsonObject.optString("discountPrice"));
            commodity.setNum(jsonObject.optString("num"));
            commodity.setStandard(jsonObject.optString("specifications"));
            commodity.setChoice(false);
            if (jsonObject.optString("type").equals("0")) {
                commodity.setType("美白牙齿");
            } else if (jsonObject.optString("type").equals("1")) {
                commodity.setType("妇儿保健");
            } else if (jsonObject.optString("type").equals("2")) {
                commodity.setType("疼痛健康");
            } else if (jsonObject.optString("type").equals("3")) {
                commodity.setType("养生家居");
            }
            mList.add(commodity);
        }
        if (mList.size() > 0) {
            adapter.setItems(mList);
            shopping_cart_all_rl.setVisibility(View.VISIBLE);
            shoppingCartNullLl.setVisibility(View.GONE);
            tv_toolbar_right.setVisibility(View.VISIBLE);
        } else {
            shopping_cart_all_rl.setVisibility(View.GONE);
            tv_toolbar_right.setVisibility(View.GONE);
            shoppingCartNullLl.setVisibility(View.VISIBLE);
        }
        isChecked();
    }

    private void completeCart() {
        ArrayList<Mall> malls = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isChoice()) {
                Mall mall = (Mall) mList.get(i);
                mall.setNum(mList.get(i).getNum() + "");
                int num = Integer.parseInt(mList.get(i).getNum());
                double price = Double.parseDouble(mall.getPrice());
                mall.setTotalPrice((price * num));
                malls.add(mall);
            }
        }
        startActivity(new Intent(getActivity(), ConfirmActivity.class).putExtra("list", malls));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("onHiddenChanged", "hidden:" + hidden);
        if (!hidden) {
            NetworkRequests.getInstance().isShow=false;
            NetworkRequests.getInstance().isShow=false;
            getCart();

            tv_toolbar_right.setText(getResources().getString(R.string.shopping_cart_edit));
            shopping_cart_settlement_tv.setText(getResources().getString(R.string.shopping_cart_settlement));
            adapter.setConceal(false);
            complete = true;

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        NetworkRequests.getInstance().isShow=false;
        getCart();
    }
}
