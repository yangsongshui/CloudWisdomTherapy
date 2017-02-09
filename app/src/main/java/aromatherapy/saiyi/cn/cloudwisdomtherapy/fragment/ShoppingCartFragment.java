package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter.ShoppingCartAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Commodity;
import butterknife.BindView;
import butterknife.OnClick;

public class ShoppingCartFragment extends BaseFragment {
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tv_toolbar_right;
    @BindView(R.id.shopping_cart_null_ll)
    LinearLayout shoppingCartNullLl;
    @BindView(R.id.shopping_cart_all_cb)
    CheckBox shoppingCartAllCb;
    @BindView(R.id.shopping_cart_price_tv)
    TextView shoppingCartPriceTv;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.shopping_cart_goods_rv)
    RecyclerView shoppingCartGoodsRv;
    private List<Commodity> mList;
    private ShoppingCartAdapter adapter;
    private boolean complete = false;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        tvToolbarTitle.setText(getResources().getString(R.string.tab_4));
        tv_toolbar_right.setVisibility(View.VISIBLE);
        tv_toolbar_right.setText(getResources().getString(R.string.shopping_cart_edit));
        shoppingCartAllCb.setText(getResources().getString(R.string.shopping_cart_select) + "(3)");
        mList = new ArrayList<>();

        shoppingCartAllCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });

        initLisr();
    }

    @Override
    protected int getContentView() {

        return R.layout.fragment_shopping_cart;


    }


    @OnClick({R.id.shopping_cart_go_tv, R.id.shopping_cart_null_ll, R.id.shopping_cart_settlement_tv, R.id.tv_toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shopping_cart_go_tv:
                //去逛逛按钮
                break;
            case R.id.shopping_cart_settlement_tv:
                //结算按钮
                break;
            case R.id.tv_toolbar_right:
                //编辑按钮

                if (complete){
                    tv_toolbar_right.setText(getResources().getString(R.string.shopping_cart_complete));
                    adapter.setConceal(complete);
                    complete=false;
                }else {
                    tv_toolbar_right.setText(getResources().getString(R.string.shopping_cart_edit));
                    adapter.setConceal(complete);
                    complete=true;
                }

                break;
        }
    }

    private void initLisr() {
        mList.add(new Commodity("大力丸", "保健品", "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg"));
        mList.add(new Commodity("无上神丹", "药品", "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg"));
        mList.add(new Commodity("无上神水", "药品", "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg"));

        adapter = new ShoppingCartAdapter(getActivity(), mList);
        shoppingCartGoodsRv.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shoppingCartGoodsRv.setLayoutManager(layoutManager);
        shoppingCartGoodsRv.setAdapter(adapter);
    }
}
