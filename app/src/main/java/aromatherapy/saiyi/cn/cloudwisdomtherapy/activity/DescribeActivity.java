package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.DescribeAdapater;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import butterknife.BindView;
import butterknife.OnClick;

public class DescribeActivity extends BaseActivity {
    @BindView(R.id.describe_lv)
    ListView describe_lv;

    @BindView(R.id.toolbar_left_white_iv)
    ImageView toolbarLeftWhiteIv;
    @BindView(R.id.tv_toolbar_white_title)
    TextView tvToolbarWhiteTitle;

    DescribeAdapater adapater;
    Mall mall;

    @Override
    protected int getContentView() {
        return R.layout.activity_describe;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvToolbarWhiteTitle.setText(getResources().getString(R.string.mall_describe2));
        toolbarLeftWhiteIv.setVisibility(View.VISIBLE);
        mall = (Mall) getIntent().getSerializableExtra("mall");
        adapater = new DescribeAdapater(this, mall.getPicList());
        describe_lv.setAdapter(adapater);
    }

    @OnClick(R.id.toolbar_left_white_iv)
    public void onClick() {
        finish();
    }
}
