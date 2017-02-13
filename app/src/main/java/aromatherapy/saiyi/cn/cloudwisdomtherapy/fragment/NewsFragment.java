package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.hr.nipuream.NRecyclerView.view.NRecyclerView;
import com.hr.nipuream.NRecyclerView.view.base.BaseLayout;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.NewsAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.News;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment implements BaseLayout.RefreshAndLoadingListener {
    @BindView(R.id.recyclerMagicView)
    NRecyclerView recyclerMagicView;
    List<News> mList;
    NewsAdapter adapter;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mList.add(new News());
        mList.add(new News());
        mList.add(new News());
        mList.add(new News());
        initData();

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_news;
    }

    private void initData() {
        recyclerMagicView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).marginResId(R.dimen.margin_left).build(), 2);
        recyclerMagicView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMagicView.setLayoutManager(layoutManager);
        recyclerMagicView.setOnRefreshAndLoadingListener(this);
        //禁止上拉加载
        recyclerMagicView.setPullLoadEnable(false);

        adapter = new NewsAdapter(mList);
        recyclerMagicView.setAdapter(adapter);

        recyclerMagicView.setTotalPages(5);
    }

    @Override
    public void refresh() {
        //下拉加载
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
                recyclerMagicView.endRefresh();

            }
        }.execute();
    }

    @Override
    public void load() {

    }
}
