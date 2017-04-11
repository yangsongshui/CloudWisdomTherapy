package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.nipuream.NRecyclerView.view.NRecyclerView;
import com.hr.nipuream.NRecyclerView.view.base.BaseLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.AftermarketAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Aftermarket;
import butterknife.BindView;

public class AftermarketActivity extends BaseActivity implements BaseLayout.RefreshAndLoadingListener, OnItemClickListener {

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.aftermarket_recycler)
    NRecyclerView aftermarketListRv;

    AftermarketAdapter adapter;
    List<Aftermarket> mList;
    @Override
    protected int getContentView() {
        return R.layout.activity_aftermarket;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList=new ArrayList<>();
        tvToolbarTitle.setText(getResources().getString(R.string.aftermarket_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initRecyclerView();
    }
    private void initRecyclerView(){
        //mList.add(new Aftermarket("大力丸", "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg", "保健品", "3g*20粒", "12.40", "18.80", "2", 5, "24.80", "24.80"));
        //mList.add(new Aftermarket("无上神丹", "http://img.my.csdn.net/uploads/201407/26/1406383219_5806.jpg", "药品", "1g*20粒", "10.00", "12.80", "1", 6, "10.00","10.00"));
       // mList.add(new Aftermarket("无上神水", "http://img.my.csdn.net/uploads/201407/26/1406383242_3127.jpg", "药品", "0.5g*20粒", "5.00", "7.80", "3", 7, "15.00", "15.00"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        aftermarketListRv.setLayoutManager(layoutManager);
        aftermarketListRv.setOnRefreshAndLoadingListener(this);
        //禁止上拉加载
        aftermarketListRv.setPullLoadEnable(false);
        //禁止下拉刷新
        aftermarketListRv.setPullRefreshEnable(false);
        // 设置底部提示
         //ViewGroup bottomView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.bottom_layout, (ViewGroup) this.findViewById(android.R.id.content), false);

        //aftermarketListRv.setBottomView(bottomView);
        adapter = new AftermarketAdapter(mList, this);
        adapter.setOnItemClickListener(this);
        aftermarketListRv.setAdapter(adapter);
        aftermarketListRv.setTotalPages(5);
    }

    @Override
    public void refresh() {

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
                if (mList.size() > 5) {
                    //没有更多数据
                    aftermarketListRv.pullNoMoreEvent();
                } else {

                    //关闭加载圆形进度条
                    aftermarketListRv.endLoadingMore();
                }

            }
        }.execute();
    }

    @Override
    public void onItemClick(View holder, int position) {
        startActivity(new Intent(this,ReturnInfoActivity.class));
    }
}
