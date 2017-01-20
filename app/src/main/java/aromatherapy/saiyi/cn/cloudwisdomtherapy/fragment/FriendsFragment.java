package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;


    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText("消息"));
        mTabLayout.addTab(mTabLayout.newTab().setText("好友"));
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friends;
    }

}
