package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.nipuream.NRecyclerView.view.NRecyclerView;
import com.hr.nipuream.NRecyclerView.view.base.BaseLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.IndentAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import butterknife.BindView;

public class IndentActivity extends BaseActivity implements BaseLayout.RefreshAndLoadingListener {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerMagicView)
    NRecyclerView recyclerMagicView;
    IndentAdapter adapter;
    List<Indent> mList;

    @Override
    protected int getContentView() {
        return R.layout.activity_indent;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTab();
        mList = new ArrayList<>();
        initRecyclerView();
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
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                Log.i("选中了", tab.getPosition() + "");
                switch (tab.getPosition()) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    default:
                        break;
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

    private void initRecyclerView() {
        mList.add(new Indent("大力丸", "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg", "保健品", "3g*20粒", "12.40", "18.8", "2", 1, "24.80"));
        mList.add(new Indent("无上神丹", "http://img.my.csdn.net/uploads/201407/26/1406383219_5806.jpg", "药品", "1g*20粒", "10.00", "12.8", "1", 2, "10.00"));
        mList.add(new Indent("无上神水", "http://img.my.csdn.net/uploads/201407/26/1406383242_3127.jpg", "药品", "0.5g*20粒", "5.00", "7.80", "3", 3, "15.00"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMagicView.setLayoutManager(layoutManager);
        recyclerMagicView.setOnRefreshAndLoadingListener(this);
        //禁止上拉加载
        //recyclerMagicView.setPullLoadEnable(false);
        //禁止下拉刷新
        recyclerMagicView.setPullRefreshEnable(false);
        // 设置底部提示
        ViewGroup bottomView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.bottom_layout, (ViewGroup) this.findViewById(android.R.id.content), false);

        recyclerMagicView.setBottomView(bottomView);
        adapter = new IndentAdapter(mList, this);
        recyclerMagicView.setAdapter(adapter);
        recyclerMagicView.setTotalPages(5);
    }

    @Override
    public void refresh() {
        //上拉刷新
    }

    @Override
    public void load() {
        //下来加载
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
               /* if (mList.size() > 5) {
                    *//*没有更多数据*//*
                    recyclerMagicView.pullNoMoreEvent();
                } else {

                    //关闭加载圆形进度条
                    recyclerMagicView.endLoadingMore();
                }*/

            }
        }.execute();
    }
}
