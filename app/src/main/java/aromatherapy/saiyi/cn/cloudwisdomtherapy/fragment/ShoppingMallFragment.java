package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.MallImageAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingMallFragment extends BaseFragment implements BaseLayout.RefreshAndLoadingListener {
    @BindView(R.id.recyclerMagicView)
    NRecyclerView recyclerMagicView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    MallImageAdapter adapter;
    List<String> mList;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {

        mList = new ArrayList<>();
        mList.add("akjsdhaj");
        mList.add("435346");
        mList.add("hdgdfg");
        mList.add("asdafdgg");
        mList.add("sdgdfhrtexf");
        initRecyclerView();

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText("全部"));
        mTabLayout.addTab(mTabLayout.newTab().setText("类型1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("类型2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("类型3"));
        mTabLayout.addTab(mTabLayout.newTab().setText("类型4"));
        mTabLayout.addTab(mTabLayout.newTab().setText("类型5"));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                Log.e("选中了", tab.getPosition() + "");
                switch (tab.getPosition()) {
                    case 0:
                        mList.clear();
                        mList.add("akjsdhaj");
                        mList.add("435346");
                        mList.add("hdgdfg");
                        mList.add("asdafdgg");
                        mList.add("sdgdfhrtexf");
                        adapter.setItems(mList);
                        break;
                    case 1:
                        mList.clear();
                        mList.add("akjsdhaj");
                        mList.add("435346");
                        adapter.setItems(mList);
                        break;
                    case 2:
                        mList.clear();

                        mList.add("hdgdfg");
                        mList.add("asdafdgg");
                        mList.add("sdgdfhrtexf");
                        adapter.setItems(mList);
                        break;
                    case 3:
                        mList.clear();
                        mList.add("akjsdhaj");
                        mList.add("ak124sfjsdhaj");
                        mList.add("fsdfdsfsd");
                        mList.add("fsdfd23123sfsd");

                        adapter.setItems(mList);
                        break;
                    case 4:
                        mList.clear();
                        mList.add("akjsdhaj");
                        mList.add("435346");
                        mList.add("hdgdfg");

                        adapter.setItems(mList);
                        break;
                    case 5:
                        mList.clear();
                        mList.add("asdafdgg");
                        mList.add("sdgdfhrtexf");
                        adapter.setItems(mList);
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
                //再次选中tab的逻辑
                Log.e("再次选中", tab.getPosition() + "");
            }
        });
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
}
