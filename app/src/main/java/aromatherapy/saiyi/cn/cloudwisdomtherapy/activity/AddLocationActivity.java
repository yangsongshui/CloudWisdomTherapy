package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
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

    @Override
    protected int getContentView() {
        return R.layout.activity_add_location;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tv_toolbar_title.setText(getResources().getString(R.string.add_title));
        tv_toolbar_right.setText(getResources().getString(R.string.add_right));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        tv_toolbar_right.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.add_city_ll, R.id.toolbar_left_iv, R.id.tv_toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_city_ll:
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
            case R.id.tv_toolbar_right:
                break;
        }
    }
}
