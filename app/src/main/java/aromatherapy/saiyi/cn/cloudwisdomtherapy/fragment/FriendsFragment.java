package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;


    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText("消息"));
        mTabLayout.addTab(mTabLayout.newTab().setText("好友"));
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friends;
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //选中了tab的逻辑
        Log.i("选中了", tab.getPosition() + "");
        switch (tab.getPosition()) {
            case 0:

                break;
            case 1:

                break;

            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //上一次tab的逻辑
        Log.i("上一次选中", tab.getPosition() + "");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
//上一次tab的逻辑
        Log.i("上一次选中", tab.getPosition() + "");
    }
}
