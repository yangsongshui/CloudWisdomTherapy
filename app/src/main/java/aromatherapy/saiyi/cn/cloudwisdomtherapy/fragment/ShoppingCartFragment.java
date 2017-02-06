package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import butterknife.BindView;

public class ShoppingCartFragment extends BaseFragment {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tv_toolbar_right;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        tvToolbarTitle.setText(getResources().getString(R.string.tab_4));
        tv_toolbar_right.setVisibility(View.VISIBLE);
        tv_toolbar_right.setText(getResources().getString(R.string.shopping_cart_add));
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_shopping_cart;
    }
}
