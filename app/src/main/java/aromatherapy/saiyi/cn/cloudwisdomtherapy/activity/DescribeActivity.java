package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.DescribeAdapater;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class DescribeActivity extends BaseActivity {
    @BindView(R.id.describe_lv)
    ListView describe_lv;

    @BindView(R.id.toolbar_left_white_iv)
    ImageView toolbarLeftWhiteIv;
    @BindView(R.id.tv_toolbar_white_title)
    TextView tvToolbarWhiteTitle;


    List<String> mList;
    DescribeAdapater adapater;

    @Override
    protected int getContentView() {
        return R.layout.activity_describe;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvToolbarWhiteTitle.setText(getResources().getString(R.string.mall_describe2));
        toolbarLeftWhiteIv.setVisibility(View.VISIBLE);
        mList = new ArrayList<>();
        mList.add("http://119.23.72.141:8080/SystemIMG/zixun/81b26fe73a5f4705b73619cf3ba79f04.jpg");
        mList.add("http://119.23.72.141:8080/SystemIMG/zixun/81b26fe73a5f4705b73619cf3ba79f04.jpg");
        mList.add("http://119.23.72.141:8080/SystemIMG/zixun/81b26fe73a5f4705b73619cf3ba79f04.jpg");
        mList.add("http://119.23.72.141:8080/SystemIMG/zixun/81b26fe73a5f4705b73619cf3ba79f04.jpg");
        adapater = new DescribeAdapater(this, mList);
        describe_lv.setAdapter(adapater);
    }

    @OnClick(R.id.toolbar_left_white_iv)
    public void onClick() {
        finish();
    }
}
