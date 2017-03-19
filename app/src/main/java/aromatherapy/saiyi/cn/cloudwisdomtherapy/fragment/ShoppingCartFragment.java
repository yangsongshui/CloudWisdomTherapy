package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;

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
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.ShoppingCartAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemCheckListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Commodity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
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
    TextView shoppingCartPriceTv;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.shopping_cart_goods_rv)
    RecyclerView shoppingCartGoodsRv;
    @BindView(R.id.shopping_cart_settlement_tv)
    TextView shopping_cart_settlement_tv;
    @BindView(R.id.shopping_cart_mall_cb)
    CheckBox shopping_cart_mall_cb;
    private List<Commodity> mList;
    private ShoppingCartAdapter adapter;
    private boolean complete = false;
    Map mMap;


    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mMap = new HashMap();
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
                break;
            case R.id.shopping_cart_settlement_tv:
                //结算按钮
                if (complete) {

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
                }

                break;
        }
    }

    private void initList() {
        mList.add(new Commodity("大力丸", "保健品", "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg", true));
        mList.add(new Commodity("无上神丹", "药品", "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg", false));
        mList.add(new Commodity("无上神水", "药品", "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg", true));

        adapter = new ShoppingCartAdapter(getActivity(), mList);
        adapter.setOnItemCheckListener(this);
        shoppingCartGoodsRv.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shoppingCartGoodsRv.setLayoutManager(layoutManager);
        shoppingCartGoodsRv.setAdapter(adapter);
    }


    private void traverse(boolean isChecked) {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setChoice(isChecked);
        }
        adapter.setItems(mList);


    }

    @Override
    public void onitemCheck(View view, boolean isCheck, int position) {

        mList.get(position).setChoice(isCheck);
        Log.e("onClick", isChecked);
        isChecked();


    }

    @Override
    public void onNumCheck(View view, int num, int position) {
        mList.get(position).setNum(num + "");
    }

    private void isChecked() {
        for (int i = 0; i < mList.size(); i++) {
            if (!mList.get(i).isChoice()) {
                shopping_cart_mall_cb.setChecked(false);
                shoppingCartAllCb.setChecked(false);
                isChecked = true;
                return;
            } else {
                isChecked = false;
                if (i == (mList.size() - 1)) {
                    shopping_cart_mall_cb.setChecked(true);
                    shoppingCartAllCb.setChecked(true);
                }
            }

        }
    }

    boolean isChecked = false;

    @Override
    public void onClick(View v) {
        traverse(isChecked);
        Log.e("onClick", isChecked);
        shopping_cart_mall_cb.setChecked(isChecked);
        shoppingCartAllCb.setChecked(isChecked);
        isChecked = !isChecked;
    }

    private void deleteCart() {
        String phone = MyApplication.newInstance().getUser().getPhone();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isChoice()) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("commodityNo", mList.get(i).getID());
                    jsonObject.put("phoneNumber", phone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
            }
        }

        mMap.put("list", jsonArray.toString().trim());
        Log.e("JSONObject", jsonArray.toString().trim());
        NetworkRequests.GetRequests(getActivity(), Constant.DELETESHOPPINGCAR, mMap, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("jsonListener", jsonObject.toString());
            }
        });
    }

    private void completeCart() {
        String phone = MyApplication.newInstance().getUser().getPhone();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isChoice()) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("commodityID", mList.get(i).getID());
                    jsonObject.put("num", mList.get(i).getNum());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
            }
        }

        mMap.put("list", jsonArray.toString().trim());
        mMap.put("orderer", phone);
        Log.e("JSONObject", jsonArray.toString().trim());
        NetworkRequests.GetRequests(getActivity(), Constant.INSERTORDER, mMap, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("jsonListener", jsonObject.toString());
            }
        });
    }

}
