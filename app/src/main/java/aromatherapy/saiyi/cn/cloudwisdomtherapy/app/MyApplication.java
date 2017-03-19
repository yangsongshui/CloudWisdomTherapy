package aromatherapy.saiyi.cn.cloudwisdomtherapy.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.cache.DiskLruBasedCache;
import com.android.volley.cache.SimpleImageLoader;
import com.android.volley.toolbox.Volley;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.controller.EaseUI;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.User;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合

    private User user;
    private RequestQueue mQueue;

    {
        PlatformConfig.setWeixin("wx815938a2f5f56950", "eb8b6aaf7817ac50e35e53d2c935a1dd");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        Config.DEBUG = true;
    }

    /**
     * 获取单例
     *
     * @return MyApplication
     */
    public static MyApplication newInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mQueue = Volley.newRequestQueue(this);
        DiskLruBasedCache.ImageCacheParams cacheParams = new DiskLruBasedCache.ImageCacheParams(getApplicationContext(), "CacheDirectory");
        cacheParams.setMemCacheSizePercent(0.5f);
        mImageLoader = new SimpleImageLoader(getApplicationContext(), cacheParams);
        EaseUI.getInstance().init(instance, null);
        UMShareAPI.get(this);
        EMClient.getInstance().setDebugMode(true);


    }


    public RequestQueue getmQueue() {
        return mQueue;
    }

    /**
     * 把活动添加到活动管理集合
     *
     * @param activity
     */
    public void addActyToList(Activity activity) {
        if (!activitiesList.contains(activity))
            activitiesList.add(activity);
    }

    /**
     * 把活动从活动管理集合移除
     *
     * @param activity
     */
    public void removeActyFromList(Activity activity) {
        if (activitiesList.contains(activity))
            activitiesList.remove(activity);
    }

    /**
     * 程序退出
     */
    public void clearAllActies() {
        for (Activity acty : activitiesList) {
            if (acty != null)
                acty.finish();
        }
        /*try {
            System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
    }


    public void setUser(User user) {
        this.user = user;
        //获取SharedPreferences对象
        SharedPreferences sharedPre = this.getSharedPreferences("config", this.MODE_PRIVATE);
        //获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        //设置参数
        editor.putString("username", user.getPhone());
        editor.putString("password", user.getPsw());

        //提交
        editor.commit();

    }

    public User getUser() {
        //获取SharedPreferences对象
        SharedPreferences sharedPre = this.getSharedPreferences("config", this.MODE_PRIVATE);
        String username = sharedPre.getString("username", "");
        String password = sharedPre.getString("password", "");
        Log.e("------", username + " " + password);
        if (username.equals("") || password.equals(""))
            return null;
        user.setPhone(username);
        user.setPsw(password);
        return user;
    }

    public void outLogin() {
        user = null;
        SharedPreferences sharedPre = this.getSharedPreferences("config", this.MODE_PRIVATE);
        //获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        //设置参数
        editor.putString("username", "");
        editor.putString("password", "");
        //提交
        editor.commit();
    }

    private SimpleImageLoader mImageLoader;

    public SimpleImageLoader getmImageLoader() {
        return mImageLoader;
    }

}
