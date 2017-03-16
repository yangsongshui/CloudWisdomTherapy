package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {


    @BindView(R.id.friend_search_tv)
    EditText friendSearchTv;
    @BindView(R.id.search_friends_rv)
    RecyclerView searchFriendsRv;
    @BindView(R.id.search_no_ll)
    LinearLayout searchNoLl;

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    @OnClick(R.id.search_cancel_tv)
    public void onClick() {
        finish();
    }
}
