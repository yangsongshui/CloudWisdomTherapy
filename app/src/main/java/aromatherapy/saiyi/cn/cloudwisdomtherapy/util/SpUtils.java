package aromatherapy.saiyi.cn.cloudwisdomtherapy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * @author laoqin
 *         首选项的工具类
 *         boolean String
 */
public final class SpUtils {

    private final static String name = "config";
    private final static int mode = Context.MODE_PRIVATE;


    //存值
    public final static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public final static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public final static void putString(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    //获取值
    public final static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        return sp.getBoolean(key, defValue);
    }

    public final static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        return sp.getString(key, defValue);
    }

    public final static int getint(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        return sp.getInt(key, defValue);
    }


    // 退出登录时要调用
    public static void clean() {
        try {
            SharedPreferences preferences = context.getSharedPreferences(name, mode);
            if (null != preferences) {
                preferences.edit().clear().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
