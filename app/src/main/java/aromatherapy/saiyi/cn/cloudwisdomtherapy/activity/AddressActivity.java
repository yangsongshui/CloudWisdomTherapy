package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.AddressAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnCheckedListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnViewClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Address;
import butterknife.BindView;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity implements OnViewClickListener, OnCheckedListener {
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.tv_toolbar_right)
    TextView tv_toolbar_right;
    @BindView(R.id.address_rv)
    RecyclerView address_rv;

    AddressAdapter adapter;
    List<Address> mList;

    @Override
    protected int getContentView() {
        return R.layout.activity_address;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        tv_toolbar_title.setText(getResources().getString(R.string.address_title));
        tv_toolbar_right.setText(getResources().getString(R.string.address_add));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        tv_toolbar_right.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        mList = new ArrayList<>();
        mList.add(new Address("张三", "32165498710", "不知道省市不知道街道不知道号", true));
        mList.add(new Address("张三", "32165498710", "不知道省市不知道街道不知道号", false));
        mList.add(new Address("张三", "32165498710", "不知道省市不知道街道不知道号", false));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        address_rv.setLayoutManager(layoutManager);
        adapter = new AddressAdapter(mList, this);
        adapter.setOnViewClickListener(this);
        adapter.setOnCheckedListener(this);
        address_rv.setAdapter(adapter);

    }

    @Override
    public void OnViewClick(View view, int position, int type) {
        if (type == 1) {

        } else if (type == 0) {

        }
    }


    private void setDefault(int position) {
        for (int i = 0; i < mList.size(); i++) {
            if (!mList.get(i).isDefa() || position == i) {

            } else {
                mList.get(i).setDefa(false);
                mList.get(position).setDefa(true);
                specialUpdate();
            }
        }


    }

    private void specialUpdate() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                adapter.setmList(mList);
            }
        };
        handler.post(r);
    }
    @OnClick({R.id.toolbar_left_iv, R.id.tv_toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_iv:
                finish();
                break;
            case R.id.tv_toolbar_right:

                break;
        }

    }

    @Override
    public void onViewChecked(View buttonView, int position) {
        setDefault(position);
    }
}
