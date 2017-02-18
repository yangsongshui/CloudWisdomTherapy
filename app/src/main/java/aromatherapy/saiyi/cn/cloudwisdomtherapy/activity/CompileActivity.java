package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

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

public class CompileActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_white_title)
    TextView tv_toolbar_white_title;
    @BindView(R.id.toolbar_left_white_iv)
    ImageView toolbar_left_white_iv;
    @BindView(R.id.tv_toolbar_white_right)
    TextView tvToolbarWhiteRight;
    @BindView(R.id.compile_pic_iv)
    CircleImageView compilePicIv;
    @BindView(R.id.compile_name_tv)
    TextView compileNameTv;
    @BindView(R.id.compile_phone_tv)
    TextView compilePhoneTv;
    @BindView(R.id.compile_address_tv)
    TextView compileAddressTv;
    @BindView(R.id.compile_sex_tv)
    TextView compileSexTv;
    @BindView(R.id.compile_height_tv)
    TextView compileHeightTv;
    @BindView(R.id.compile_weight_tv)
    TextView compileWeightTv;
    @BindView(R.id.compile_hospital_tv)
    TextView compileHospitalTv;
    @BindView(R.id.compile_hospital_ll)
    LinearLayout compileHospitalLl;
    @BindView(R.id.compile_family_tv)
    TextView compileFamilyTv;
    @BindView(R.id.compile_family_ll)
    LinearLayout compileFamilyLl;

    @Override
    protected int getContentView() {
        return R.layout.activity_compile;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tv_toolbar_white_title.setText(getResources().getString(R.string.compile_title));
        toolbar_left_white_iv.setVisibility(View.VISIBLE);
        toolbar_left_white_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick({R.id.compile_pic_ll, R.id.compile_name_ll, R.id.compile_phone_ll, R.id.compile_address_ll, R.id.compile_sex_ll, R.id.compile_height_ll, R.id.compile_weight_ll, R.id.compile_hospital_ll, R.id.compile_family_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.compile_pic_ll:
                break;
            case R.id.compile_name_ll:
                break;
            case R.id.compile_phone_ll:
                break;
            case R.id.compile_address_ll:
                break;
            case R.id.compile_sex_ll:
                break;
            case R.id.compile_height_ll:
                break;
            case R.id.compile_weight_ll:
                break;
            case R.id.compile_hospital_ll:
                break;
            case R.id.compile_family_ll:
                break;
        }
    }
}
