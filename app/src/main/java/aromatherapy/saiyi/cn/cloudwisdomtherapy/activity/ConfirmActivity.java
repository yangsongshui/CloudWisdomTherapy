package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.ShoppingMallAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Address;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class ConfirmActivity extends BaseActivity {
    private static final int RESULT = 2;
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

    @BindView(R.id.confirm_mall_rv)
    RecyclerView confirm_mall_rv;
    @BindView(R.id.confirm_total_tv)
    TextView confirmTotalTv; //总价
    @BindView(R.id.confirm_tuijianren_et)
    EditText confirmTuijianrenEt;//推荐人号码
    @BindView(R.id.confirm_total_price_tv)
    TextView confirmTotalPriceTv;  //折后总价

    ShoppingMallAdapter adapter;
    List<Mall> mallList;
    Map<String, String> map;
    double prcice = 0.00;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_confirm;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mallList = new ArrayList<>();
        map = new HashMap<>();
        toastor = new Toastor(this);
        ArrayList<Mall> malls = (ArrayList<Mall>) getIntent().getSerializableExtra("list");
        mallList.addAll(malls);
        getPrcice();
        adapter = new ShoppingMallAdapter(this, mallList);
        tvToolbarTitle.setText("确认订单");
        toolbar_left_iv.setVisibility(View.VISIBLE);
        initList();
        confirmTotalTv.setText(prcice + "");


        confirmTuijianrenEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getPrcice();
                if (s.length() == 11) {
                    findUserRole(s.toString());
                }
            }
        });
        getAddress();
    }

    private void initList() {
        confirm_mall_rv.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        confirm_mall_rv.setLayoutManager(layoutManager);
        confirm_mall_rv.setAdapter(adapter);
    }

    @OnClick({R.id.confirm_user_rl, R.id.confirm_payment_tv, R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_user_rl:
                startActivityForResult(new Intent(this, AddressActivity.class).putExtra("address", true), RESULT);
                break;
            case R.id.confirm_payment_tv:
                addConfirm();
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
        }
    }

    Address address;

    private void addConfirm() {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < mallList.size(); i++) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("commodityID", mallList.get(i).getID());
                jsonObject.put("num", mallList.get(i).getNum());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);

        }

        map.put("orderer", phone);
        map.put("commodityList", jsonArray.toString().trim());
        map.put("consigneeID", address.getID());
        map.put("orderPrice", prcice + "");
        if (confirmTuijianrenEt.getText().toString().trim().length() == 11) {
            map.put("recommender", confirmTuijianrenEt.getText().toString().trim());
        }
        if (!confirmTuijianrenEt.getText().toString().trim().equals(phone)) {
            Log.e("JSONObject", jsonArray.toString().trim());
             NetworkRequests.getInstance().initViw(this).GetRequests(Constant.INSERTORDER, map, new JsonDataReturnListener() {
                @Override
                public void jsonListener(JSONObject jsonObject) {
                    if (jsonObject.optInt("resCode") == 0) {
                        String orderNo = jsonObject.optJSONObject("resBody").optString("orderNo");
                        startActivity(new Intent(ConfirmActivity.this, PaymentActivity.class).putExtra("orderNo", orderNo).putExtra("prcice", prcice));
                        finish();
                    }
                    Log.e("CompileActivity", jsonObject.toString());

                }
            });
        } else {
            toastor.showSingletonToast("推荐人不能为自己");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT) {
            address = (Address) data.getSerializableExtra("return");
            confirmUsernameTv.setText(address.getName());
            confirmPhoneTv.setText(address.getPhone());
            String city = address.getSheng();
            if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                confirmAddressTv.setText(address.getShi() + address.getQu() + address.getAddress());
            } else {
                confirmAddressTv.setText(city + address.getShi() + address.getQu() + address.getAddress());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void getPrcice() {
        prcice = 0;
        for (int i = 0; i < mallList.size(); i++) {
            prcice += mallList.get(i).getTotalPrice();
        }
        confirmTotalPriceTv.setText(prcice + "");

    }

    private void getAddress() {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("phoneNumber", phone);
         NetworkRequests.getInstance().initViw(this).GetRequests(Constant.FINDDEFAULTADD, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    JSONObject object = jsonObject.optJSONObject("resBody").optJSONObject("map");
                    address = new Address();
                    address.setAddress(object.optString("consigneeAddress"));
                    address.setID(object.optString("id"));
                    address.setSheng(object.optString("sheng"));
                    address.setShi(object.optString("shi"));
                    address.setQu(object.optString("qu"));
                    address.setPhone(object.optString("consigneePhone"));
                    address.setName(object.optString("consignee"));
                    confirmUsernameTv.setText(address.getName());
                    confirmPhoneTv.setText(address.getPhone());
                    String city = address.getSheng();
                    if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                        confirmAddressTv.setText(address.getShi() + address.getQu() + address.getAddress());
                    } else {
                        confirmAddressTv.setText(city + address.getShi() + address.getQu() + address.getAddress());
                    }
                }
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }

    private void findUserRole(final String phone) {
        map.clear();
        map.put("phoneNumber", phone);
        Log.e("findUserRole", !(phone.equals(MyApplication.newInstance().getUser().getPhone())));
         NetworkRequests.getInstance().initViw(this).GetRequests(Constant.FINDUSERROLE, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") != 0 || (phone.equals(MyApplication.newInstance().getUser().getPhone()))) {

                    toastor.showSingletonToast("推荐用户不存在或为自己,不能享受折扣优惠");
                    getPrcice();
                } else {

                    if (jsonObject.optJSONObject("resBody").optInt("lists") == 1)
                        prcice = prcice * 0.85;
                    else
                        prcice = prcice * 0.9;

                    confirmTotalPriceTv.setText(prcice + "");
                }
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }
}
