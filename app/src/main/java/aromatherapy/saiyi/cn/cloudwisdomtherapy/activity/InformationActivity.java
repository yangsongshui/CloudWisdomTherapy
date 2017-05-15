package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Information;
import butterknife.BindView;

public class InformationActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.information_title_tv)
    TextView informationTitleTv;
    @BindView(R.id.information_time_tv)
    TextView informationTimeTv;
    @BindView(R.id.information_msg_iv)
    ImageView informationMsgIv;
    @BindView(R.id.information_msg_tv)
    TextView informationMsgTv;
    Information information;

    @Override
    protected int getContentView() {
        return R.layout.activity_information;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        information = (Information) getIntent().getExtras().getSerializable("information");
        MyApplication.newInstance().getmImageLoader().load(information.getTopPic()).into(informationMsgIv);
        tvToolbarTitle.setText(getResources().getString(R.string.information));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        informationTitleTv.setText(information.getTitle());
        informationTimeTv.setText(information.getDate());
        informationMsgTv.setText(information.getContext());

    }

}
