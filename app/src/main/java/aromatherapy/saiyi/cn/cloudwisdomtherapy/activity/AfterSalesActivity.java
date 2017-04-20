package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import butterknife.BindView;
import butterknife.OnClick;

public class AfterSalesActivity extends BaseActivity {

    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;


    Indent indent;
    @Override
    protected int getContentView() {
        return R.layout.activity_after_sales;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tv_toolbar_title.setText(getResources().getString(R.string.after_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        indent= (Indent) getIntent().getSerializableExtra("indent");
    }


    @OnClick({R.id.after_return_goods_rl, R.id.after_refund_rl, R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.after_return_goods_rl:
                startActivity(new Intent(this, ReturnGoodsActivity.class).putExtra("type",0).putExtra("indent",indent));
                finish();
                break;
            case R.id.after_refund_rl:
                startActivity(new Intent(this, ReturnGoodsActivity.class).putExtra("type",1).putExtra("indent",indent));
                finish();
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
        }
    }
}
