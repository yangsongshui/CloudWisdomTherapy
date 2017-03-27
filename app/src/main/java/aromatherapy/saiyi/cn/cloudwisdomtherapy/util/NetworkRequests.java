package aromatherapy.saiyi.cn.cloudwisdomtherapy.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;

import org.json.JSONObject;

import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;

/**
 * Created by yangsong on 2017/3/16.
 */

public class NetworkRequests {

    private static ProgressDialog progressDialog;

    public static void GetRequests(final Context context, final String url, final Map<String, String> map, final JsonDataReturnListener jsonListener) {
        initViw(context);
        if (!progressDialog.isShowing())
            progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue mQueue = MyApplication.newInstance().getmQueue();
                mQueue.add(new NormalPostRequest(url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        jsonListener.jsonListener(jsonObject);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new Toastor(context).showSingletonToast("服务器连接失败");
                        progressDialog.dismiss();
                    }
                }, map));
            }
        }).start();


    }

    public static void initViw(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("加载中...");
        progressDialog.setTitle("");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

}
