package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.nipuream.NRecyclerView.view.NRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.MallImageAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class SearchMallActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.mall_search_tv)
    EditText friendSearchTv;
    @BindView(R.id.mall_friends_rv)
    NRecyclerView searchFriendsRv;
    @BindView(R.id.mall_no_ll)
    LinearLayout searchNoLl;

    List<Mall> mList;
    MallImageAdapter adapter;
    Map<String, String> map;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_mall;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        map = new HashMap<>();
        toastor = new Toastor(this);
        tvToolbarTitle.setText(getResources().getString(R.string.compile_cancel2));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchFriendsRv.setLayoutManager(layoutManager);
        //禁止上拉加载
        searchFriendsRv.setPullLoadEnable(false);
        //禁止下拉刷新
        searchFriendsRv.setPullRefreshEnable(false);
        adapter = new MallImageAdapter(mList, this);
        adapter.setOnItemClickListener(this);
        searchFriendsRv.setAdapter(adapter);

        searchFriendsRv.setTotalPages(5);
    }


    private void getItem(JSONArray jsonArray) {
        mList.clear();
        if (jsonArray.length() > 0) {
            searchNoLl.setVisibility(View.GONE);
            searchFriendsRv.setVisibility(View.VISIBLE);
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("----", jsonArray.optJSONObject(i).toString() + "");
                Mall mall = new Mall();
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                mall.setName(jsonObject.optString("commodityName"));
                mall.setPrice(jsonObject.optString("discountPrice"));
                mall.setID(jsonObject.optString("id"));
                if (jsonObject.optString("type").equals("0")) {
                    mall.setType("美白牙齿");
                } else if (jsonObject.optString("type").equals("1")) {
                    mall.setType("妇儿保健");
                } else if (jsonObject.optString("type").equals("2")) {
                    mall.setType("疼痛健康");
                } else if (jsonObject.optString("type").equals("3")) {
                    mall.setType("养生家居");
                }

                mall.setPurchase_price(jsonObject.optString("originalPrice"));
                mall.setPicture(jsonObject.optString("commodityPic"));
                mall.setStandard(jsonObject.optString("specifications"));
                mall.setProductionFactory(jsonObject.optString("productionFactory"));
                mall.setDescribe(jsonObject.optString("discountMsg"));
                mall.setPicList(Arrays.asList(jsonObject.optString("remark").split(",")));
                mList.add(mall);
            }
            adapter.setItems(mList);

        } else {
            searchNoLl.setVisibility(View.VISIBLE);
            searchFriendsRv.setVisibility(View.GONE);
        }
    }

    private void getMall(String msg) {
        map.clear();
        map.put("value", msg + "");
        NetworkRequests.getInstance().initViw(this).GetRequests(Constant.FINDVAGUE, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    getItem(jsonObject.optJSONObject("resBody").optJSONObject("lists").optJSONArray("commoditys"));
                } else {
                    toastor.showSingletonToast(jsonObject.optString("resMessage"));
                }
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }

    @Override
    public void onItemClick(View holder, int position) {
        startActivity(new Intent(this, MallActivity.class).putExtra("mall", mList.get(position)));
    }


    @OnClick(R.id.mall_cancel_tv)
    public void onClick() {
        if (friendSearchTv.getText().toString().trim().length() > 0)
            getMall(friendSearchTv.getText().toString().trim());
    }
}
