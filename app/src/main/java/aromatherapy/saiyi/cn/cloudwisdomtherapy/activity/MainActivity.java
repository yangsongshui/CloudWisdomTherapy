package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getResources().getString(R.string.tab_1), R.drawable.ic_bottom_navigation);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getResources().getString(R.string.tab_2), R.drawable.ic_bottom_music);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getResources().getString(R.string.tab_3), R.drawable.ic_bottom_car);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getResources().getString(R.string.tab_4), R.drawable.ic_bottom_setting);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(getResources().getString(R.string.tab_5), R.drawable.ic_bottom_toys);
        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        // 默认背景颜色
        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.bg_bottom_navigation));
        // 切换时颜色的转变
        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.accent_bottom_navigation));
        bottomNavigation.setInactiveColor(ContextCompat.getColor(this, R.color.inactive_bottom_navigation));
        // 强制着色
        bottomNavigation.setForceTint(true);
        // 强制展示标题
        bottomNavigation.setForceTitlesDisplay(true);
        // 使用圆圈效果
        bottomNavigation.setColored(false);
        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);
    }
}
