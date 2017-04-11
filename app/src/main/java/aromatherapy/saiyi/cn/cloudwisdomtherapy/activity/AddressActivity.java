package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.AddressAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnCheckedListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnViewClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Address;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity implements OnViewClickListener, OnCheckedListener, OnItemClickListener {
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.tv_toolbar_right)
    TextView tv_toolbar_right;
    @BindView(R.id.address_rv)
    RecyclerView address_rv;

    AddressAdapter adapter;
    List<Address> mList;

    Map<String, String> mMap;
    Toastor toastor;
    boolean isAddress = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_address;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        isAddress = getIntent().getBooleanExtra("address", false);
        mMap = new HashMap<>();
        toastor = new Toastor(this);
        initToolbar();
        initRecyclerView();

    }

    private void initToolbar() {
        tv_toolbar_title.setText(getResources().getString(R.string.address_title));
        tv_toolbar_right.setText(getResources().getString(R.string.address_add));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        tv_toolbar_right.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        mList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        address_rv.setLayoutManager(layoutManager);
        adapter = new AddressAdapter(mList, this);
        adapter.setOnViewClickListener(this);
        adapter.setOnCheckedListener(this);
        if (isAddress)
            adapter.setOnItemClickListener(this);
        address_rv.setAdapter(adapter);

    }

    @Override
    public void OnViewClick(View view, int position, int type) {
        if (type == 1) {
            //删除收货地址
            deleteAddress(mList.get(position).getID());
        } else if (type == 0) {
            //编辑收货地址
            startActivity(new Intent(this, AddLocationActivity.class).putExtra("address", mList.get(position)));
        }
    }


    private void setDefault(int position) {
        for (int i = 0; i < mList.size(); i++) {
            if (!mList.get(i).isDefa() || position == i) {

            } else {
                mList.get(i).setDefa(false);
                mList.get(position).setDefa(true);
                specialUpdate();
            }
        }


    }

    private void specialUpdate() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                adapter.setmList(mList);
            }
        };
        handler.post(r);
    }

    @OnClick({R.id.toolbar_left_iv, R.id.tv_toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_iv:
                finish();
                break;
            case R.id.tv_toolbar_right:
                startActivity(new Intent(this, AddLocationActivity.class));
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        addCart();
    }

    @Override
    public void onViewChecked(View buttonView, int position) {
        setDefault(position);
        setDefaul(mList.get(position).getID());
    }

    private void addCart() {
        mMap.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();

        mMap.put("phoneNumber", phone);
        NetworkRequests.GetRequests(this, Constant.FINDADDRESS, mMap, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("jsonListener", jsonObject.toString());
                if (jsonObject.optInt("resCode") == 0)
                    getItem(jsonObject.optJSONObject("resBody").optJSONArray("lists"));
            }
        });

    }

    private void setDefaul(String id) {
        mMap.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();

        mMap.put("phoneNumber", phone);
        mMap.put("id", id);
        NetworkRequests.GetRequests(this, Constant.UPDATEADDRESSDEFAULT, mMap, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("jsonListener", jsonObject.toString());

            }
        });
    }

    boolean isDefaul = false;

    private void getItem(JSONArray jsonArray) {
        mList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            Address address = new Address();
            address.setAddress(jsonObject.optString("consigneeAddress"));
            address.setID(jsonObject.optString("id"));
            address.setSheng(jsonObject.optString("sheng"));
            address.setShi(jsonObject.optString("shi"));
            address.setQu(jsonObject.optString("qu"));
            address.setPhone(jsonObject.optString("consigneePhone"));
            address.setName(jsonObject.optString("consignee"));
            address.setMail(jsonObject.optString("mail"));
            if (jsonObject.optString("defaultAddress").equals("0")) {
                isDefaul = true;
                address.setDefa(true);
            } else {
                address.setDefa(false);
            }

            mList.add(address);

        }
        if (!isDefaul && mList.size() > 0)
            mList.get(0).setDefa(true);
        adapter.setmList(mList);
    }

    @Override
    public void onItemClick(View holder, int position) {
        Intent intent = new Intent();
        Address address = mList.get(position);
        intent.putExtra("return", address);
        setResult(2, intent);
        finish();
    }

    private void deleteAddress(final String id) {
        mMap.clear();

        mMap.put("id", id);
        NetworkRequests.GetRequests(this, Constant.DELETEADDRESS, mMap, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("jsonListener", jsonObject.toString());
                toastor.showSingletonToast(jsonObject.optString("resMessage"));
                if (jsonObject.optInt("resCode") == 0) {
                    addCart();
                }

            }
        });
    }
}
