package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;

public class AddressActivity extends BaseActivity {
    @BindView(R.id.address_rv)
    RecyclerView address_rv;

    @Override
    protected int getContentView() {
        return R.layout.activity_address;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
