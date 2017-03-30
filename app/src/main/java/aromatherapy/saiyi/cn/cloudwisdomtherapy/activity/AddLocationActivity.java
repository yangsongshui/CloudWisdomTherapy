package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Address;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.GetCity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class AddLocationActivity extends BaseActivity {
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.tv_toolbar_right)
    TextView tv_toolbar_right;

    @BindView(R.id.add_name_et)
    EditText addNameEt;
    @BindView(R.id.add_phone_et)
    EditText addPhoneEt;
    @BindView(R.id.add_postcode_et)
    EditText addPostcodeEt;
    @BindView(R.id.add_city_tv)
    TextView addCityTv;
    @BindView(R.id.add_location_et)
    EditText addLocationEt;
    GetCity getCity;
    private OptionsPickerView optionsPickerView;//地区选择

    Map<String, String> mMap;
    Toastor toastor;
    Address address;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_location;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        address = (Address) getIntent().getSerializableExtra("address");
        mMap = new HashMap<>();
        toastor = new Toastor(this);
        getCity = new GetCity(this);
        tv_toolbar_title.setText(getResources().getString(R.string.add_title));
        tv_toolbar_right.setText(getResources().getString(R.string.add_right));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        tv_toolbar_right.setVisibility(View.VISIBLE);
        if (address != null) {
            addNameEt.setText(address.getName());
            addPhoneEt.setText(address.getPhone());
            addPostcodeEt.setText(address.getMail());
            addLocationEt.setText(address.getAddress());
            String city = address.getSheng();
            if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                addCityTv.setText(address.getShi() + address.getQu());
            } else {
                addCityTv.setText(address.getSheng() + address.getShi() + address.getQu());
            }

        }
        initView();
    }


    @OnClick({R.id.add_city_ll, R.id.toolbar_left_iv, R.id.tv_toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_city_ll:
                optionsPickerView.show();
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
            case R.id.tv_toolbar_right:
                AddCity();
                break;
        }
    }

    String sheng, shi, qu;

    private void initView() {
        optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx;
                //返回的分别是三个级别的选中位置
                String city = getCity.getOptions1Items().get(options1).getPickerViewText();
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    tx = city + " " + getCity.getOptions3Items().get(options1).get(options2).get(options3).getPickerViewText();
                    sheng = city;
                    shi = city;
                    qu = getCity.getOptions3Items().get(options1).get(options2).get(options3).getPickerViewText();
                } else {
                    tx = getCity.getOptions1Items().get(options1).getPickerViewText() +
                            getCity.getOptions2Items().get(options1).get(options2) +
                            getCity.getOptions3Items().get(options1).get(options2).get(options3).getPickerViewText();
                    sheng = getCity.getOptions1Items().get(options1).getPickerViewText();
                    shi = getCity.getOptions2Items().get(options1).get(options2);
                    qu = getCity.getOptions3Items().get(options1).get(options2).get(options3).getPickerViewText();
                }

                addCityTv.setText(tx);
            }
        }).build();
        optionsPickerView.setPicker(getCity.getOptions1Items(), getCity.getOptions2Items(), getCity.getOptions3Items());//三级选择器
    }

    private void AddCity() {
        String url;
        mMap.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        if (address != null) {
            url = Constant.UPDATEADDRESS;
            sheng = address.getSheng();
            shi = address.getShi();
            qu = address.getQu();
            mMap.put("id", address.getID());
        } else {
            url = Constant.ADDADDRESS;

        }

        if (addNameEt.getText().toString().trim().length() > 0 && addPhoneEt.getText().toString().trim().length() == 11
                && addLocationEt.getText().toString().trim().length() > 0 && sheng != null && shi != null && qu != null) {
            mMap.put("phoneNumber", phone);
            mMap.put("consignee", addNameEt.getText().toString().trim());
            mMap.put("consigneePhone", addPhoneEt.getText().toString().trim());
            mMap.put("consigneeAddress", addLocationEt.getText().toString().trim());
            mMap.put("defaultAddress", "1");
            mMap.put("sheng", sheng);
            mMap.put("shi", shi);
            mMap.put("qu", qu);
            mMap.put("mail", addPostcodeEt.getText().toString().trim());


            NetworkRequests.GetRequests(this, url, mMap, new JsonDataReturnListener() {
                @Override
                public void jsonListener(JSONObject jsonObject) {
                    Log.e("jsonListener", jsonObject.toString());
                    toastor.showSingletonToast(jsonObject.optString("resMessage"));
                    if (jsonObject.optInt("resCode") == 0)
                        finish();
                }
            });
        } else {
            toastor.showSingletonToast("部分参数填写不正确,请检查");
        }


    }
}
