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

public class SuggestActivity extends BaseActivity {


    @BindView(R.id.toolbar_left_white_iv)
    ImageView toolbarLeftWhiteIv;
    @BindView(R.id.tv_toolbar_white_title)
    TextView tvToolbarWhiteTitle;
    @BindView(R.id.suggest_input_et)
    EditText suggestInputEt;

    @Override
    protected int getContentView() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toolbarLeftWhiteIv.setVisibility(View.VISIBLE);
        tvToolbarWhiteTitle.setText(getResources().getString(R.string.install_suggest));
    }



    @OnClick({R.id.toolbar_left_white_iv, R.id.suggest_submit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_white_iv:
                finish();
                break;
            case R.id.suggest_submit_tv:
                    break;

        }
    }
}
