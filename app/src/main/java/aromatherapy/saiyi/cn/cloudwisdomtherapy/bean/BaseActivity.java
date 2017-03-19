package aromatherapy.saiyi.cn.cloudwisdomtherapy.bean;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import butterknife.ButterKnife;



public abstract class BaseActivity extends AppCompatActivity {
    //添加到活动管理集合中
    {
        MyApplication.newInstance().addActyToList(this);
    }
    protected BaseFragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getContentView());
        translucentStatusBar();
        ButterKnife.bind(this);
        //用于显示当前位于哪个活动
        Log.d("BaseActivity", getClass().getSimpleName());
        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.newInstance().removeActyFromList(this);
    }



    //注入布局
    protected abstract int getContentView();

    //初始化
    protected abstract void init(Bundle savedInstanceState);
    
    private void translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }
}
