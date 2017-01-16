package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.nipuream.NRecyclerView.view.NRecyclerView;
import com.hr.nipuream.NRecyclerView.view.base.BaseLayout;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.ForumAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MyViewPager;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends BaseFragment implements BaseLayout.RefreshAndLoadingListener {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.forum_nr_rv)
    NRecyclerView forumragment_lv;

    /*列表数据*/
    private ForumAdapter adapter;
    private List<String> currentDatas = new ArrayList<String>();
    private List<String> datas = new ArrayList<String>();
    private int currentPage = 1;
    private int totalPages = 6;
    /*广告栏数据*/
    // 滚动栏viewpager
    private MyViewPager vpager;
    private ImageView[] imgvsOfVpager = new ImageView[4];
    private View[] pointvsOfVpager = new View[4];
    // viewpager适配器
    private PagerAdapter vpagerAdapter;
    // 图片资源id数组
    private int[] imgvsResId = {R.drawable.zhifubao_icon, R.drawable.weixin_icon,
            R.drawable.zhifubao_icon, R.drawable.weixin_icon};
    // 点view的id数组
    private int[] pointvsId = {R.id.recommendFrag_vpager_point1ForVpager,
            R.id.recommendFrag_vpager_point2ForVpager,
            R.id.recommendFrag_vpager_point3ForVpager,
            R.id.recommendFrag_vpager_point4ForVpager};
    private Handler handler = new Handler(); // 用于执行viewpager自动滚动效果
    private Runnable tempRun; // viewpager自动滚动任务
    private int currentPageOfVpager; // 正在展示的viewpager页数
    private int imgvsNum;
    private boolean isResetTimer = true; // 手动改变viewpager显示页时，要重置自动滚动计时
    private ViewGroup adVentureView;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        tvToolbarTitle.setText(getResources().getString(R.string.information));
        initRV();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_information;
    }


    /*初始化新闻列表*/
    private void initRV() {
        forumragment_lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).marginResId(R.dimen.margin_left).build(), 2);
        forumragment_lv.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        forumragment_lv.setLayoutManager(layoutManager);
        forumragment_lv.setOnRefreshAndLoadingListener(this);
        //设置广告
        adVentureView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.forumragment_adventure_layout, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        initVpager(adVentureView);

        forumragment_lv.setAdtureView(adVentureView);
        // 设置底部提示
        ViewGroup bottomView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.bottom_layout, (ViewGroup) getActivity().findViewById(android.R.id.content), false);

        forumragment_lv.setBottomView(bottomView);
        //设置数据
        datas = Arrays.asList(getResources().getStringArray(R.array.data));

        addItems();
        adapter = new ForumAdapter(currentDatas);
        adapter.setmListener(new ForumAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.e("onItemClick", postion + "");
            }
        });
        forumragment_lv.setAdapter(adapter);

        forumragment_lv.setTotalPages(5);

    }


    @Override
    public void refresh() {
        Log.e("-------refresh", "doInBackground");
        //下拉刷新
        currentPage = 1;
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {

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
                Log.e("-------refresh", "onPostExecute");
                if (integer == 1) {

                    forumragment_lv.removeErrorView();
                    forumragment_lv.setPullLoadEnable(currentPage >= totalPages ? false : true);

                    forumragment_lv.resetEntryView();
                    // addItems();
                    adapter.setItems(currentDatas);
                    forumragment_lv.endRefresh();

                }
            }
        }.execute();
        /*实时更新vpager中image图片*/
      /*  ImageView iv = (ImageView) vpager.findViewWithTag(imgvsResId[0]);
        imgvsResId[0]=R.drawable.weixin_icon;

        iv.setImageResource( imgvsResId[0]);*/
    }

    @Override
    public void load() {
        //下拉加载
        currentPage++;
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
                if (currentPage >= totalPages) {
                    /*没有更多数据*/
                    forumragment_lv.pullNoMoreEvent();
                } else {

                    addItems();
                    adapter.setItems(currentDatas);
                    //关闭加载圆形进度条
                    forumragment_lv.endLoadingMore();
                }

            }
        }.execute();
    }

    private void addItems() {

        List<String> strs = new ArrayList<>();

        for (int i = (currentPage - 1) * 15; i < currentPage * 15; i++) {
            strs.add(datas.get(i));
        }

        if (forumragment_lv.isRefreshing())
            currentDatas = strs;
        else
            currentDatas.addAll(strs);

    }

    /**
     * 初始化viewpager
     *
     * @param
     */
    private void initVpager(View layout) {
        vpager = (MyViewPager) layout.findViewById(R.id.recommendFrag_myVpager);
        vpager.setDisallowParentInterceptTouchEvent(true);
        imgvsNum = imgvsOfVpager.length;
        for (int i = 0; i < imgvsNum; i++) {
            // 图片
            imgvsOfVpager[i] = new ImageView(getActivity());
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            imgvsOfVpager[i].setScaleType(ImageView.ScaleType.FIT_XY);
            imgvsOfVpager[i].setImageResource(imgvsResId[i]);
            // 点
            pointvsOfVpager[i] = layout.findViewById(pointvsId[i]);
            pointvsOfVpager[i].setTag(i);
            // pointvsOfVpager[i].setOnClickListener(onPointClickListener);
        }
        pointvsOfVpager[currentPageOfVpager % imgvsNum].setSelected(true);
        // 适配器
        vpagerAdapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(imgvsOfVpager[position % imgvsNum]);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ImageView v = imgvsOfVpager[position % imgvsNum];
                //设置Tag用于实时更新图片
                v.setTag(imgvsResId[position % imgvsNum]);
                ViewGroup parent = (ViewGroup) v.getParent();
                //Log.i("ViewPaperAdapter", parent.toString());
                if (parent != null) {
                    parent.removeAllViews();
                }

                container.addView(imgvsOfVpager[position % imgvsNum]);
                return imgvsOfVpager[position % imgvsNum];
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }


        };
        vpager.setAdapter(vpagerAdapter);
        currentPageOfVpager = imgvsNum * 100;
        vpager.setCurrentItem(currentPageOfVpager);
        // viewpager的滚动事件
        vpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                pointvsOfVpager[currentPageOfVpager % imgvsNum]
                        .setSelected(false);
                currentPageOfVpager = arg0;
                pointvsOfVpager[currentPageOfVpager % imgvsNum]
                        .setSelected(true);
                // 判断是否重置滚动计时
                if (isResetTimer) {
                    handler.removeCallbacks(tempRun);
                    setVpagerAutoScroll();
                }
                isResetTimer = true;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        // 滚动条自动滚动
        setVpagerAutoScroll();
    }

    /**
     * viewpager自动循环滚动
     */
    private void setVpagerAutoScroll() {
        tempRun = new Runnable() {
            @Override
            public void run() {
                isResetTimer = false;
                vpager.setCurrentItem((currentPageOfVpager + 1)
                        % Integer.MAX_VALUE);

                setVpagerAutoScroll();
                Log.e("-----", "自动滚动");
            }
        };
        handler.postDelayed(tempRun, 5000);
    }

}
