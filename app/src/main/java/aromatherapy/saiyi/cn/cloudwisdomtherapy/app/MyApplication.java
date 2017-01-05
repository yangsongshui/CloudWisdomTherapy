package aromatherapy.saiyi.cn.cloudwisdomtherapy.app;

import android.app.Activity;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合

    //private static User user = new User();

    private int step_number = 0;
    private RequestQueue mQueue;

    public int getStep_number() {
        return step_number;
    }

    public void setStep_number(int step_number) {
        this.step_number = step_number;
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static MyApplication newInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mQueue = Volley.newRequestQueue(this);
        // 请谨慎使用，以免用户看到消息过多卸载应用。
        x.Ext.init(this);
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

    }



    public RequestQueue getmQueue() {
        return mQueue;
    }

    /**
     * 把活动添加到活动管理集合
     *
     * @param acty
     */
    public void addActyToList(Activity acty) {
        if (!activitiesList.contains(acty))
            activitiesList.add(acty);
    }

    /**
     * 把活动从活动管理集合移除
     *
     * @param acty
     */
    public void removeActyFromList(Activity acty) {
        if (activitiesList.contains(acty))
            activitiesList.remove(acty);
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

/*
    public void setUser(User user) {
        this.user = user;
        //获取SharedPreferences对象
        SharedPreferences sharedPre = this.getSharedPreferences("config", this.MODE_PRIVATE);
        //获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        //设置参数
        editor.putString("username", user.getPhone());
        editor.putString("password", user.getPassWord());
        //极光推送用户ID设置
         JPushInterface.setAliasAndTags(this, "2474978944", null, this);
        editor.putString("UserID", user.getUserID());
        //提交
        editor.commit();

    }

    public User getUser() {
        //获取SharedPreferences对象
        SharedPreferences sharedPre = this.getSharedPreferences("config", this.MODE_PRIVATE);
        String username = sharedPre.getString("username", "");
        String password = sharedPre.getString("password", "");
        String UserID = sharedPre.getString("UserID", "");
        Log.e("------", username + " " + password);
        if (username.equals("") || password.equals(""))
            return null;
        user.setPhone(username);
        user.setPassWord(password);
        user.setUserID(UserID);

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
*/


}
