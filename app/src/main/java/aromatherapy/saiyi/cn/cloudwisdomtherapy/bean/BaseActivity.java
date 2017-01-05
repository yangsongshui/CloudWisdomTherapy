package aromatherapy.saiyi.cn.cloudwisdomtherapy.bean;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import org.xutils.x;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import cn.jpush.android.api.JPushInterface;


public abstract class BaseActivity extends Activity {
    //添加到活动管理集合中
    {
        MyApplication.newInstance().addActyToList(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentView());
        JPushInterface.init(getApplicationContext());
        x.view().inject(this);
        //用于显示当前位于哪个活动
        Log.d("BaseActivity", getClass().getSimpleName());
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.newInstance().removeActyFromList(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
    //注入布局
    protected abstract int getContentView();

    //初始化
    protected abstract void init();
}
