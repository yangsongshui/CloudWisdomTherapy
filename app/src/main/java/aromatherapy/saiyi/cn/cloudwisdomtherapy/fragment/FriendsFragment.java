package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends BaseFragment {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;


    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        tvToolbarTitle.setText(getResources().getString(R.string.tab_3));
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friends;
    }

}
