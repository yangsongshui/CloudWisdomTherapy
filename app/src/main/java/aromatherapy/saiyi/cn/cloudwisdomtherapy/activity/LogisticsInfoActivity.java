package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class LogisticsInfoActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.logistic_info_firm_et)
    EditText logisticInfoFirmEt;
    @BindView(R.id.logistic_info_numbers_et)
    EditText logisticInfoNumbersEt;

    Map<String, String> mMap;
    Toastor toastor;
    String logistics;

    @Override
    protected int getContentView() {
        return R.layout.activity_logistics_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toastor = new Toastor(this);
        mMap = new HashMap<>();
        tv_toolbar_title.setText(getResources().getString(R.string.returnInfo_examine2));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        logistics = getIntent().getStringExtra("logistics");
    }


    @OnClick({R.id.logistics_button_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logistics_button_tv:

                deleteAddress();
                break;

        }
    }

    private void deleteAddress() {
        mMap.clear();

        mMap.put("orderNo", logistics);
        if (logisticInfoFirmEt.getText().length() > 0 && logisticInfoNumbersEt.getText().length() > 0) {
            mMap.put("logisticsCompany", logisticInfoFirmEt.getText().toString());
            mMap.put("logisticsNo", logisticInfoNumbersEt.getText().toString());
             NetworkRequests.getInstance().initViw(this).GetRequests( Constant.INSERTLOGISTICSRETURNMSG, mMap, new JsonDataReturnListener() {
                @Override
                public void jsonListener(JSONObject jsonObject) {
                    Log.e("jsonListener", jsonObject.toString());
                    toastor.showSingletonToast(jsonObject.optString("resMessage"));
                    if (jsonObject.optInt("resCode") == 0) {
                        toastor.showSingletonToast("添加成功");
                        Intent intent = new Intent();
                        intent.putExtra("logisticsCompany", logisticInfoFirmEt.getText().toString());
                        intent.putExtra("logisticsNo", logisticInfoNumbersEt.getText().toString());
                        setResult(2, intent);
                        finish();
                    }
                }
            });
        } else {
            toastor.showSingletonToast("物流信息不能为空");
        }
    }
}
