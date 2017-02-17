package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;

public class CompileActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_white_title)
    TextView tv_toolbar_white_title;
    @BindView(R.id.toolbar_left_white_iv)
    ImageView toolbar_left_white_iv;

    @Override
    protected int getContentView() {
        return R.layout.activity_compile;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
