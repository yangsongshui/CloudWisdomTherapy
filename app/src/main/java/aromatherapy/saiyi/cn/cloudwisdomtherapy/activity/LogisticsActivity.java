package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.LogiaticsAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Logistics;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;

public class LogisticsActivity extends BaseActivity {


    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.recyclerMagicView)
    RecyclerView recyclerMagicView;
    @BindView(R.id.logistic_info_firm_tv)
    TextView logistic_info_firm_tv;
    @BindView(R.id.logistic_info_numbers_tv)
    TextView logistic_info_numbers_tv;
    List<Logistics> mList;
    LogiaticsAdapter adapter;
    String logistics;

    Map<String, String> mMap;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_logistics;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toastor = new Toastor(this);
        mMap = new HashMap<>();
        logistics = getIntent().getStringExtra("logistics");
        tv_toolbar_title.setText(getResources().getString(R.string.logistic_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initList();
        getwuliu();
    }

    private void initList() {
        mList = new ArrayList<>();

        adapter = new LogiaticsAdapter(this, mList);
        recyclerMagicView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMagicView.setLayoutManager(layoutManager);
        recyclerMagicView.setAdapter(adapter);
    }

    public void getwuliu() {

        String url = "http://ali-deliver.showapi.com/showapi_expInfo?com=auto&nu=" + logistics;
        NetworkRequests.getInstance().initViw(this).makeHTTPrequest(url, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                JSONObject jsonCode = jsonObject.optJSONObject("showapi_res_body");
                int code = jsonCode.optInt("ret_code");
                logistic_info_numbers_tv.setText(jsonCode.optString("mailNo"));
                logistic_info_firm_tv.setText(jsonCode.optString("expTextName"));
                if (code == 0) {
                    getLogistics(jsonCode.optJSONArray("data"));
                } else {
                    toastor.showSingletonToast("物流信息查询失败");
                }
            }
        });
    }

    private void getLogistics(JSONArray jsonArray) {
        mList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            Logistics logistics = new Logistics();
            logistics.setData(jsonArray.optJSONObject(i).optString("time"));
            logistics.setContext(jsonArray.optJSONObject(i).optString("context"));
            mList.add(logistics);
        }
        adapter.setItems(mList);
    }
}
