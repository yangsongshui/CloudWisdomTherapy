package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.IndentAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.NestFullListViewAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Address;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.NestFullListView;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.NestFullViewHolder;
import butterknife.BindView;
import butterknife.OnClick;

public class IndentActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    /* @BindView(R.id.indent_mall_lv)
     RecyclerView indent_mall_lv;*/
    @BindView(R.id.indent_cart_go_ll)
    LinearLayout indent_cart_go_ll;
    @BindView(R.id.cstFullShowListView)
    NestFullListView nestFullListView;
    IndentAdapter adapter;
    List<Indent> mList;
    Map<String, List<Indent>> mMap;
    Map<String, String> map;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_indent;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTab();
        map = new HashMap<>();
        toastor = new Toastor(this);
        mList = new ArrayList<>();
        mMap = new HashMap<>();
        List<Mall> malls = new ArrayList<>();
        malls.add(new Mall());
        malls.add(new Mall());
        malls.add(new Mall());
        mList.add(new Indent(0, "326", malls, new Address()));
        mList.add(new Indent(0, "312", malls, new Address()));
        mList.add(new Indent(0, "321", malls, new Address()));
        if (mList.size() > 0) {
            indent_cart_go_ll.setVisibility(View.GONE);
            nestFullListView.setVisibility(View.VISIBLE);
        } else {
            indent_cart_go_ll.setVisibility(View.VISIBLE);
            nestFullListView.setVisibility(View.GONE);
        }
        initeListView();
        tvToolbarTitle.setText(getResources().getString(R.string.me_order));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTab() {

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.indent_all)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.indent_obligation)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.indent_committed)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.indent_collect)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.indent_buy)));
        int type = getIntent().getIntExtra("type", -1);
        if (type == -1) {
            tabLayout.getTabAt(0).select();
        } else {
            tabLayout.getTabAt(type).select();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                Log.i("选中了", tab.getPosition() + "");
                switch (tab.getPosition()) {
                    case 0:
                        getIndent("8");
                        break;
                    case 1:
                        getIndent("0");
                        break;
                    case 2:
                        getIndent("1");
                        break;
                    case 3:
                        getIndent("2");
                        break;
                    case 4:
                        getIndent("3");
                        break;

                    default:
                        break;
                }
                if (mList.size() > 0) {
                    indent_cart_go_ll.setVisibility(View.GONE);
                    nestFullListView.setVisibility(View.VISIBLE);
                } else {
                    indent_cart_go_ll.setVisibility(View.VISIBLE);
                    nestFullListView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //上一次tab的逻辑
                Log.i("上一次选中", tab.getPosition() + "");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //上一次tab的逻辑
                Log.i("上一次选中", tab.getPosition() + "");
            }
        });

    }

    /* private void initRecyclerView() {

         LinearLayoutManager layoutManager = new LinearLayoutManager(this);
         layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
         indent_mall_lv.setLayoutManager(layoutManager);
         adapter = new IndentAdapter(mList, this);
         indent_mall_lv.setAdapter(adapter);


     }
 */
    private void initeListView() {
        nestFullListView.setAdapter(new NestFullListViewAdapter<Indent>(R.layout.indent_item, mList) {
            @Override
            public void onBind(int pos, Indent indent, NestFullViewHolder holder) {


                if (indent.getState() == 1) {
                    //待付款
                    holder.getView(R.id.indent_item_confirm_tv).setVisibility(View.GONE);
                    holder.getView(R.id.indent_item_payment_tv).setVisibility(View.VISIBLE);
                    holder.getView(R.id.indent_item_logistics_tv).setVisibility(View.GONE);
                } else if (indent.getState() == 3) {
                    //已发货

                    holder.getView(R.id.indent_item_confirm_tv).setVisibility(View.VISIBLE);
                    holder.getView(R.id.indent_item_payment_tv).setVisibility(View.GONE);
                    holder.getView(R.id.indent_item_logistics_tv).setVisibility(View.VISIBLE);
                } else if (indent.getState() == 4) {
                    //已确认

                    holder.getView(R.id.indent_item_confirm_tv).setVisibility(View.GONE);
                    holder.getView(R.id.indent_item_payment_tv).setVisibility(View.GONE);
                    holder.getView(R.id.indent_item_logistics_tv).setVisibility(View.VISIBLE);

                } else if (indent.getState() == 2) {
                    //待发货
                }

                ((NestFullListView) holder.getView(R.id.cstFullShowListView2)).setAdapter(new NestFullListViewAdapter<Mall>(R.layout.indent_mall_item, indent.getMalls()) {
                    @Override
                    public void onBind(int pos, Mall mall, NestFullViewHolder holder) {
                        ImageView indent_item_pic_iv = holder.getView(R.id.indent_item_pic_iv);
                        TextView intent_rmb_tv = holder.getView(R.id.intent_rmb_tv);
                        holder.getView(R.id.indent_item_name_tv);
                        holder.getView(R.id.indent_item_price_tv);
                        holder.getView(R.id.indent_item_type_tv);
                        holder.getView(R.id.indent_item_standard_tv);
                        TextView indent_item_purchase_price_tv = holder.getView(R.id.indent_item_purchase_price_tv);
                        holder.getView(R.id.indent_item_num_tv);
                        indent_item_purchase_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        intent_rmb_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                });
            }
        });
    }

    @OnClick(R.id.indent_cart_go_tv)
    public void onClick() {
        finish();
    }


    private void getIndent(String type) {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("orderStatu", type);
        NetworkRequests.GetRequests(this, Constant.FINDALLORDER, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    getItem(jsonObject.optJSONObject("resBody").optJSONArray("list"));
                }
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }

    private void getItem(JSONArray jsonArray) {
        mList.clear();
        if (jsonArray.length() > 0)
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                Indent indent = new Indent();


                mList.add(indent);
            }
        // adapter.setmList(mList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getIndent("8");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, DetailsActivity.class).putExtra("indent", mList.get(position)));

    }
}
