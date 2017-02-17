package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyInformationActivity extends BaseActivity {

    @BindView(R.id.information_name_tv)
    TextView informationNameTv;
    @BindView(R.id.information_sex_tv)
    ImageView informationSexTv;
    @BindView(R.id.information_age_tc)
    TextView informationAgeTc;
    @BindView(R.id.information_phone_tv)
    TextView informationPhoneTv;
    @BindView(R.id.information_address_tv)
    TextView informationAddressTv;
    @BindView(R.id.information_hospital_tv)
    TextView informationHospitalTv;
    @BindView(R.id.information_hospital_ll)
    LinearLayout informationHospitalLl;
    @BindView(R.id.information_family_tv)
    TextView informationFamilyTv;
    @BindView(R.id.information_family_ll)
    LinearLayout informationFamilyLl;
    @BindView(R.id.information_birthday_tv)
    TextView informationBirthdayTv;
    @BindView(R.id.information_height_tv)
    TextView informationHeightTv;
    @BindView(R.id.information_weight_tv)
    TextView informationWeightTv;
    @BindView(R.id.information_compile_tv)
    TextView informationCompileTv;
    @BindView(R.id.activity_my_information)
    LinearLayout activityMyInformation;
    @BindView(R.id.information_pic_iv)
    CircleImageView informationPicIv;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_information;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    @OnClick({R.id.information_back_iv, R.id.information_compile_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.information_back_iv:
                finish();

                break;
            case R.id.information_compile_tv:
                startActivity(new Intent(this, CompileActivity.class));
                break;
        }
    }
}
