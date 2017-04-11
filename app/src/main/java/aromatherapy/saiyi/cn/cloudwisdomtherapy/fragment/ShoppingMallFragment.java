package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hr.nipuream.NRecyclerView.view.NRecyclerView;
import com.hr.nipuream.NRecyclerView.view.base.BaseLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.MallActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.MallImageAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingMallFragment extends BaseFragment implements BaseLayout.RefreshAndLoadingListener, OnItemClickListener {
    @BindView(R.id.recyclerMagicView)
    NRecyclerView recyclerMagicView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    MallImageAdapter adapter;
    List<Mall> mList;
    Toastor toastor;
    Map<String, String> map;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        toastor = new Toastor(getActivity());
        map = new HashMap<>();
        mList = new ArrayList<>();
        initRecyclerView();

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText("全部"));
        mTabLayout.addTab(mTabLayout.newTab().setText("美白牙齿"));
        mTabLayout.addTab(mTabLayout.newTab().setText("妇儿保健"));
        mTabLayout.addTab(mTabLayout.newTab().setText("疼痛健康"));
        mTabLayout.addTab(mTabLayout.newTab().setText("养生家居"));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                Log.e("选中了", tab.getPosition() + "");
                switch (tab.getPosition()) {
                    case 0:
                        mList.clear();
                        getMall("100");
                        break;
                    case 1:
                        mList.clear();
                        getMall("0");
                        break;
                    case 2:
                        mList.clear();
                        getMall("1");
                        break;
                    case 3:
                        mList.clear();

                        getMall("2");
                        break;
                    case 4:
                        mList.clear();
                        getMall("3");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //上一次tab的逻辑
                Log.e("上一次选中", tab.getPosition() + "");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //上一次tab的逻辑
                Log.e("上一次选中", tab.getPosition() + "");
            }
        });
        getMall("100");
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_shopping_mall;
    }


    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMagicView.setLayoutManager(layoutManager);
        recyclerMagicView.setOnRefreshAndLoadingListener(this);
        //禁止上拉加载
        //recyclerMagicView.setPullLoadEnable(false);
        //禁止下拉刷新
        recyclerMagicView.setPullRefreshEnable(false);
        // 设置底部提示
        ViewGroup bottomView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.bottom_layout, (ViewGroup) getActivity().findViewById(android.R.id.content), false);

        recyclerMagicView.setBottomView(bottomView);
        adapter = new MallImageAdapter(mList, getActivity());
        adapter.setOnItemClickListener(this);
        recyclerMagicView.setAdapter(adapter);

        recyclerMagicView.setTotalPages(5);

    }

    @Override
    public void refresh() {

    }

    @Override
    public void load() {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                Log.e("-------load", "doInBackground");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                Log.e("-------load", "onPostExecute");
                if (mList.size() > 5) {
                    /*没有更多数据*/
                    recyclerMagicView.pullNoMoreEvent();
                } else {

                    //关闭加载圆形进度条
                    recyclerMagicView.endLoadingMore();
                }

            }
        }.execute();

    }

    @Override
    public void onItemClick(View holder, int position) {
        startActivity(new Intent(getActivity(), MallActivity.class).putExtra("mall", mList.get(position)));
    }

    private void getMall(String type) {
        map.put("type", type);
        NetworkRequests.GetRequests(getActivity(), Constant.FINDCOMMODITYS, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("ShoppingMallFragment", jsonObject.toString());
                if (jsonObject.optInt("resCode") == 0) {
                    getItem(jsonObject.optJSONObject("resBody").optJSONArray("lists"));
                }

            }
        });
    }

    private void getItem(JSONArray jsonArray) {
        mList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
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
            mList.add(mall);
        }
        adapter.setItems(mList);
    }

}
